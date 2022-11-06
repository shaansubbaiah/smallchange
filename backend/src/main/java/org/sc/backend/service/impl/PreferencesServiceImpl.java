package org.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.sc.backend.domain.Preferences;
import org.sc.backend.repository.PreferencesRepository;
import org.sc.backend.service.PreferencesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Preferences}.
 */
@Service
@Transactional
public class PreferencesServiceImpl implements PreferencesService {

    private final Logger log = LoggerFactory.getLogger(PreferencesServiceImpl.class);

    private final PreferencesRepository preferencesRepository;

    public PreferencesServiceImpl(PreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    @Override
    public Preferences save(Preferences preferences) {
        log.debug("Request to save Preferences : {}", preferences);
        return preferencesRepository.save(preferences);
    }

    @Override
    public Preferences update(Preferences preferences) {
        log.debug("Request to update Preferences : {}", preferences);
        preferences.setIsPersisted();
        return preferencesRepository.save(preferences);
    }

    @Override
    public Optional<Preferences> partialUpdate(Preferences preferences) {
        log.debug("Request to partially update Preferences : {}", preferences);

        return preferencesRepository
            .findById(preferences.getScUserId())
            .map(existingPreferences -> {
                if (preferences.getInvestmentPurpose() != null) {
                    existingPreferences.setInvestmentPurpose(preferences.getInvestmentPurpose());
                }
                if (preferences.getRiskTolerance() != null) {
                    existingPreferences.setRiskTolerance(preferences.getRiskTolerance());
                }
                if (preferences.getIncomeCategory() != null) {
                    existingPreferences.setIncomeCategory(preferences.getIncomeCategory());
                }
                if (preferences.getInvestmentLength() != null) {
                    existingPreferences.setInvestmentLength(preferences.getInvestmentLength());
                }

                return existingPreferences;
            })
            .map(preferencesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Preferences> findAll() {
        log.debug("Request to get all Preferences");
        return preferencesRepository.findAll();
    }

    /**
     *  Get all the preferences where ScUser is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Preferences> findAllWhereScUserIsNull() {
        log.debug("Request to get all preferences where ScUser is null");
        return StreamSupport
            .stream(preferencesRepository.findAll().spliterator(), false)
            .filter(preferences -> preferences.getScUser() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Preferences> findOne(String id) {
        log.debug("Request to get Preferences : {}", id);
        return preferencesRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Preferences : {}", id);
        preferencesRepository.deleteById(id);
    }
}
