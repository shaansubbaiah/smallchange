package org.sc.backend.service;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.ScUser;

/**
 * Service Interface for managing {@link ScUser}.
 */
public interface ScUserService {
    /**
     * Save a scUser.
     *
     * @param scUser the entity to save.
     * @return the persisted entity.
     */
    ScUser save(ScUser scUser);

    /**
     * Updates a scUser.
     *
     * @param scUser the entity to update.
     * @return the persisted entity.
     */
    ScUser update(ScUser scUser);

    /**
     * Partially updates a scUser.
     *
     * @param scUser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ScUser> partialUpdate(ScUser scUser);

    /**
     * Get all the scUsers.
     *
     * @return the list of entities.
     */
    List<ScUser> findAll();

    /**
     * Get the "id" scUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScUser> findOne(String id);

    /**
     * Delete the "id" scUser.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
