package org.sc.backend.service;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.MutualFunds;

/**
 * Service Interface for managing {@link MutualFunds}.
 */
public interface MutualFundsService {
    /**
     * Save a mutualFunds.
     *
     * @param mutualFunds the entity to save.
     * @return the persisted entity.
     */
    MutualFunds save(MutualFunds mutualFunds);

    /**
     * Updates a mutualFunds.
     *
     * @param mutualFunds the entity to update.
     * @return the persisted entity.
     */
    MutualFunds update(MutualFunds mutualFunds);

    /**
     * Partially updates a mutualFunds.
     *
     * @param mutualFunds the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MutualFunds> partialUpdate(MutualFunds mutualFunds);

    /**
     * Get all the mutualFunds.
     *
     * @return the list of entities.
     */
    List<MutualFunds> findAll();

    /**
     * Get the "id" mutualFunds.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MutualFunds> findOne(String id);

    /**
     * Delete the "id" mutualFunds.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
