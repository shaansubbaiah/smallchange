package org.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.Positions;
import org.sc.backend.repository.PositionsRepository;
import org.sc.backend.service.PositionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Positions}.
 */
@Service
@Transactional
public class PositionsServiceImpl implements PositionsService {

    private final Logger log = LoggerFactory.getLogger(PositionsServiceImpl.class);

    private final PositionsRepository positionsRepository;

    public PositionsServiceImpl(PositionsRepository positionsRepository) {
        this.positionsRepository = positionsRepository;
    }

    @Override
    public Positions save(Positions positions) {
        log.debug("Request to save Positions : {}", positions);
        return positionsRepository.save(positions);
    }

    @Override
    public Positions update(Positions positions) {
        log.debug("Request to update Positions : {}", positions);
        return positionsRepository.save(positions);
    }

    @Override
    public Optional<Positions> partialUpdate(Positions positions) {
        log.debug("Request to partially update Positions : {}", positions);

        return positionsRepository
            .findById(positions.getPositionId())
            .map(existingPositions -> {
                if (positions.getScUserId() != null) {
                    existingPositions.setScUserId(positions.getScUserId());
                }
                if (positions.getAssetCode() != null) {
                    existingPositions.setAssetCode(positions.getAssetCode());
                }
                if (positions.getAssetType() != null) {
                    existingPositions.setAssetType(positions.getAssetType());
                }
                if (positions.getBuyPrice() != null) {
                    existingPositions.setBuyPrice(positions.getBuyPrice());
                }
                if (positions.getQuantity() != null) {
                    existingPositions.setQuantity(positions.getQuantity());
                }

                return existingPositions;
            })
            .map(positionsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Positions> findAll() {
        log.debug("Request to get all Positions");
        return positionsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Positions> findOne(Long id) {
        log.debug("Request to get Positions : {}", id);
        return positionsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Positions : {}", id);
        positionsRepository.deleteById(id);
    }
}
