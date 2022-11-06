package org.sc.backend.service;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.ScAccount;

/**
 * Service Interface for managing {@link ScAccount}.
 */
public interface ScAccountService {
    /**
     * Save a scAccount.
     *
     * @param scAccount the entity to save.
     * @return the persisted entity.
     */
    ScAccount save(ScAccount scAccount);

    /**
     * Updates a scAccount.
     *
     * @param scAccount the entity to update.
     * @return the persisted entity.
     */
    ScAccount update(ScAccount scAccount);

    /**
     * Partially updates a scAccount.
     *
     * @param scAccount the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ScAccount> partialUpdate(ScAccount scAccount);

    /**
     * Get all the scAccounts.
     *
     * @return the list of entities.
     */
    List<ScAccount> findAll();

    /**
     * Get the "id" scAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScAccount> findOne(Long id);

    /**
     * Delete the "id" scAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
