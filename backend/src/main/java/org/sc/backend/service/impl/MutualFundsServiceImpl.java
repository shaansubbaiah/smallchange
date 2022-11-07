package org.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.MutualFunds;
import org.sc.backend.repository.MutualFundsRepository;
import org.sc.backend.service.MutualFundsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MutualFunds}.
 */
@Service
@Transactional
public class MutualFundsServiceImpl implements MutualFundsService {

    private final Logger log = LoggerFactory.getLogger(MutualFundsServiceImpl.class);

    private final MutualFundsRepository mutualFundsRepository;

    public MutualFundsServiceImpl(MutualFundsRepository mutualFundsRepository) {
        this.mutualFundsRepository = mutualFundsRepository;
    }

    @Override
    public MutualFunds save(MutualFunds mutualFunds) {
        log.debug("Request to save MutualFunds : {}", mutualFunds);
        return mutualFundsRepository.save(mutualFunds);
    }

    @Override
    public MutualFunds update(MutualFunds mutualFunds) {
        log.debug("Request to update MutualFunds : {}", mutualFunds);
        mutualFunds.setIsPersisted();
        return mutualFundsRepository.save(mutualFunds);
    }

    @Override
    public Optional<MutualFunds> partialUpdate(MutualFunds mutualFunds) {
        log.debug("Request to partially update MutualFunds : {}", mutualFunds);

        return mutualFundsRepository
            .findById(mutualFunds.getCode())
            .map(existingMutualFunds -> {
                if (mutualFunds.getName() != null) {
                    existingMutualFunds.setName(mutualFunds.getName());
                }
                if (mutualFunds.getQuantity() != null) {
                    existingMutualFunds.setQuantity(mutualFunds.getQuantity());
                }
                if (mutualFunds.getCurrentPrice() != null) {
                    existingMutualFunds.setCurrentPrice(mutualFunds.getCurrentPrice());
                }
                if (mutualFunds.getInterestRate() != null) {
                    existingMutualFunds.setInterestRate(mutualFunds.getInterestRate());
                }
                if (mutualFunds.getMfType() != null) {
                    existingMutualFunds.setMfType(mutualFunds.getMfType());
                }

                return existingMutualFunds;
            })
            .map(mutualFundsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MutualFunds> findAll() {
        log.debug("Request to get all MutualFunds");
        return mutualFundsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MutualFunds> findOne(String id) {
        log.debug("Request to get MutualFunds : {}", id);
        return mutualFundsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MutualFunds : {}", id);
        mutualFundsRepository.deleteById(id);
    }
}
