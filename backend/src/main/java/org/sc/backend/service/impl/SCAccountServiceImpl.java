package org.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.SCAccount;
import org.sc.backend.repository.SCAccountRepository;
import org.sc.backend.service.SCAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SCAccount}.
 */
@Service
@Transactional
public class SCAccountServiceImpl implements SCAccountService {

    private final Logger log = LoggerFactory.getLogger(SCAccountServiceImpl.class);

    private final SCAccountRepository sCAccountRepository;

    public SCAccountServiceImpl(SCAccountRepository sCAccountRepository) {
        this.sCAccountRepository = sCAccountRepository;
    }

    @Override
    public SCAccount save(SCAccount sCAccount) {
        log.debug("Request to save SCAccount : {}", sCAccount);
        return sCAccountRepository.save(sCAccount);
    }

    @Override
    public SCAccount update(SCAccount sCAccount) {
        log.debug("Request to update SCAccount : {}", sCAccount);
        return sCAccountRepository.save(sCAccount);
    }

    @Override
    public Optional<SCAccount> partialUpdate(SCAccount sCAccount) {
        log.debug("Request to partially update SCAccount : {}", sCAccount);

        return sCAccountRepository
            .findById(sCAccount.getAccNo())
            .map(existingSCAccount -> {
                if (sCAccount.getScUserId() != null) {
                    existingSCAccount.setScUserId(sCAccount.getScUserId());
                }
                if (sCAccount.getBankName() != null) {
                    existingSCAccount.setBankName(sCAccount.getBankName());
                }
                if (sCAccount.getAccType() != null) {
                    existingSCAccount.setAccType(sCAccount.getAccType());
                }
                if (sCAccount.getAccBalance() != null) {
                    existingSCAccount.setAccBalance(sCAccount.getAccBalance());
                }

                return existingSCAccount;
            })
            .map(sCAccountRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SCAccount> findAll() {
        log.debug("Request to get all SCAccounts");
        return sCAccountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SCAccount> findOne(Long id) {
        log.debug("Request to get SCAccount : {}", id);
        return sCAccountRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SCAccount : {}", id);
        sCAccountRepository.deleteById(id);
    }
}
