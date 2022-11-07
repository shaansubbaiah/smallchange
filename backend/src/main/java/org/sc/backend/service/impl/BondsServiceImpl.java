package org.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.Bonds;
import org.sc.backend.repository.BondsRepository;
import org.sc.backend.service.BondsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bonds}.
 */
@Service
@Transactional
public class BondsServiceImpl implements BondsService {

    private final Logger log = LoggerFactory.getLogger(BondsServiceImpl.class);

    private final BondsRepository bondsRepository;

    public BondsServiceImpl(BondsRepository bondsRepository) {
        this.bondsRepository = bondsRepository;
    }

    @Override
    public Bonds save(Bonds bonds) {
        log.debug("Request to save Bonds : {}", bonds);
        return bondsRepository.save(bonds);
    }

    @Override
    public Bonds update(Bonds bonds) {
        log.debug("Request to update Bonds : {}", bonds);
        bonds.setIsPersisted();
        return bondsRepository.save(bonds);
    }

    @Override
    public Optional<Bonds> partialUpdate(Bonds bonds) {
        log.debug("Request to partially update Bonds : {}", bonds);

        return bondsRepository
            .findById(bonds.getCode())
            .map(existingBonds -> {
                if (bonds.getName() != null) {
                    existingBonds.setName(bonds.getName());
                }
                if (bonds.getQuantity() != null) {
                    existingBonds.setQuantity(bonds.getQuantity());
                }
                if (bonds.getCurrentPrice() != null) {
                    existingBonds.setCurrentPrice(bonds.getCurrentPrice());
                }
                if (bonds.getInterestRate() != null) {
                    existingBonds.setInterestRate(bonds.getInterestRate());
                }
                if (bonds.getDurationMonths() != null) {
                    existingBonds.setDurationMonths(bonds.getDurationMonths());
                }
                if (bonds.getBondType() != null) {
                    existingBonds.setBondType(bonds.getBondType());
                }
                if (bonds.getExchangeName() != null) {
                    existingBonds.setExchangeName(bonds.getExchangeName());
                }

                return existingBonds;
            })
            .map(bondsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bonds> findAll() {
        log.debug("Request to get all Bonds");
        return bondsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bonds> findOne(String id) {
        log.debug("Request to get Bonds : {}", id);
        return bondsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Bonds : {}", id);
        bondsRepository.deleteById(id);
    }
}
