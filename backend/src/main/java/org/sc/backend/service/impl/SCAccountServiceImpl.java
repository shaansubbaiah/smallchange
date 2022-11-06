package org.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.ScAccount;
import org.sc.backend.repository.ScAccountRepository;
import org.sc.backend.service.ScAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ScAccount}.
 */
@Service
@Transactional
public class ScAccountServiceImpl implements ScAccountService {

    private final Logger log = LoggerFactory.getLogger(ScAccountServiceImpl.class);

    private final ScAccountRepository scAccountRepository;

    public ScAccountServiceImpl(ScAccountRepository scAccountRepository) {
        this.scAccountRepository = scAccountRepository;
    }

    @Override
    public ScAccount save(ScAccount scAccount) {
        log.debug("Request to save ScAccount : {}", scAccount);
        return scAccountRepository.save(scAccount);
    }

    @Override
    public ScAccount update(ScAccount scAccount) {
        log.debug("Request to update ScAccount : {}", scAccount);
        return scAccountRepository.save(scAccount);
    }

    @Override
    public Optional<ScAccount> partialUpdate(ScAccount scAccount) {
        log.debug("Request to partially update ScAccount : {}", scAccount);

        return scAccountRepository
            .findById(scAccount.getAccNo())
            .map(existingScAccount -> {
                if (scAccount.getScUserId() != null) {
                    existingScAccount.setScUserId(scAccount.getScUserId());
                }
                if (scAccount.getBankName() != null) {
                    existingScAccount.setBankName(scAccount.getBankName());
                }
                if (scAccount.getAccType() != null) {
                    existingScAccount.setAccType(scAccount.getAccType());
                }
                if (scAccount.getAccBalance() != null) {
                    existingScAccount.setAccBalance(scAccount.getAccBalance());
                }

                return existingScAccount;
            })
            .map(scAccountRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScAccount> findAll() {
        log.debug("Request to get all ScAccounts");
        return scAccountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScAccount> findOne(Long id) {
        log.debug("Request to get ScAccount : {}", id);
        return scAccountRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScAccount : {}", id);
        scAccountRepository.deleteById(id);
    }
}
