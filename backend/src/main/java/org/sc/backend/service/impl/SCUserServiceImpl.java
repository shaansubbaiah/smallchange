package org.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.SCUser;
import org.sc.backend.repository.SCUserRepository;
import org.sc.backend.service.SCUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SCUser}.
 */
@Service
@Transactional
public class SCUserServiceImpl implements SCUserService {

    private final Logger log = LoggerFactory.getLogger(SCUserServiceImpl.class);

    private final SCUserRepository sCUserRepository;

    public SCUserServiceImpl(SCUserRepository sCUserRepository) {
        this.sCUserRepository = sCUserRepository;
    }

    @Override
    public SCUser save(SCUser sCUser) {
        log.debug("Request to save SCUser : {}", sCUser);
        return sCUserRepository.save(sCUser);
    }

    @Override
    public SCUser update(SCUser sCUser) {
        log.debug("Request to update SCUser : {}", sCUser);
        sCUser.setIsPersisted();
        return sCUserRepository.save(sCUser);
    }

    @Override
    public Optional<SCUser> partialUpdate(SCUser sCUser) {
        log.debug("Request to partially update SCUser : {}", sCUser);

        return sCUserRepository
            .findById(sCUser.getScUserId())
            .map(existingSCUser -> {
                if (sCUser.getName() != null) {
                    existingSCUser.setName(sCUser.getName());
                }
                if (sCUser.getEmail() != null) {
                    existingSCUser.setEmail(sCUser.getEmail());
                }
                if (sCUser.getPasswordHash() != null) {
                    existingSCUser.setPasswordHash(sCUser.getPasswordHash());
                }
                if (sCUser.getImage() != null) {
                    existingSCUser.setImage(sCUser.getImage());
                }
                if (sCUser.getImageContentType() != null) {
                    existingSCUser.setImageContentType(sCUser.getImageContentType());
                }

                return existingSCUser;
            })
            .map(sCUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SCUser> findAll() {
        log.debug("Request to get all SCUsers");
        return sCUserRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SCUser> findOne(String id) {
        log.debug("Request to get SCUser : {}", id);
        return sCUserRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete SCUser : {}", id);
        sCUserRepository.deleteById(id);
    }
}
