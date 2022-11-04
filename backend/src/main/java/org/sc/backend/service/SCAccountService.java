package org.sc.backend.service;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.SCAccount;

/**
 * Service Interface for managing {@link SCAccount}.
 */
public interface SCAccountService {
    /**
     * Save a sCAccount.
     *
     * @param sCAccount the entity to save.
     * @return the persisted entity.
     */
    SCAccount save(SCAccount sCAccount);

    /**
     * Updates a sCAccount.
     *
     * @param sCAccount the entity to update.
     * @return the persisted entity.
     */
    SCAccount update(SCAccount sCAccount);

    /**
     * Partially updates a sCAccount.
     *
     * @param sCAccount the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SCAccount> partialUpdate(SCAccount sCAccount);

    /**
     * Get all the sCAccounts.
     *
     * @return the list of entities.
     */
    List<SCAccount> findAll();

    /**
     * Get the "id" sCAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SCAccount> findOne(Long id);

    /**
     * Delete the "id" sCAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
