package org.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.ScUser;
import org.sc.backend.repository.ScUserRepository;
import org.sc.backend.service.ScUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ScUser}.
 */
@Service
@Transactional
public class ScUserServiceImpl implements ScUserService {

    private final Logger log = LoggerFactory.getLogger(ScUserServiceImpl.class);

    private final ScUserRepository scUserRepository;

    public ScUserServiceImpl(ScUserRepository scUserRepository) {
        this.scUserRepository = scUserRepository;
    }

    @Override
    public ScUser save(ScUser scUser) {
        log.debug("Request to save ScUser : {}", scUser);
        return scUserRepository.save(scUser);
    }

    @Override
    public ScUser update(ScUser scUser) {
        log.debug("Request to update ScUser : {}", scUser);
        scUser.setIsPersisted();
        return scUserRepository.save(scUser);
    }

    @Override
    public Optional<ScUser> partialUpdate(ScUser scUser) {
        log.debug("Request to partially update ScUser : {}", scUser);

        return scUserRepository
            .findById(scUser.getScUserId())
            .map(existingScUser -> {
                if (scUser.getName() != null) {
                    existingScUser.setName(scUser.getName());
                }
                if (scUser.getEmail() != null) {
                    existingScUser.setEmail(scUser.getEmail());
                }
                if (scUser.getPasswordHash() != null) {
                    existingScUser.setPasswordHash(scUser.getPasswordHash());
                }
                if (scUser.getTotalStocksInvestment() != null) {
                    existingScUser.setTotalStocksInvestment(scUser.getTotalStocksInvestment());
                }
                if (scUser.getTotalBondsInvestment() != null) {
                    existingScUser.setTotalBondsInvestment(scUser.getTotalBondsInvestment());
                }
                if (scUser.getTotalMfInvestment() != null) {
                    existingScUser.setTotalMfInvestment(scUser.getTotalMfInvestment());
                }
                if (scUser.getImage() != null) {
                    existingScUser.setImage(scUser.getImage());
                }
                if (scUser.getImageContentType() != null) {
                    existingScUser.setImageContentType(scUser.getImageContentType());
                }
                if (scUser.getScUserRole() != null) {
                    existingScUser.setScUserRole(scUser.getScUserRole());
                }
                if (scUser.getScUserEnabled() != null) {
                    existingScUser.setScUserEnabled(scUser.getScUserEnabled());
                }

                return existingScUser;
            })
            .map(scUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScUser> findAll() {
        log.debug("Request to get all ScUsers");
        return scUserRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScUser> findOne(String id) {
        log.debug("Request to get ScUser : {}", id);
        return scUserRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ScUser : {}", id);
        scUserRepository.deleteById(id);
    }
}
