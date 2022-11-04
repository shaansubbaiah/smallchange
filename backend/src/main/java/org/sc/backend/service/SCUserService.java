package org.sc.backend.service;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.SCUser;

/**
 * Service Interface for managing {@link SCUser}.
 */
public interface SCUserService {
    /**
     * Save a sCUser.
     *
     * @param sCUser the entity to save.
     * @return the persisted entity.
     */
    SCUser save(SCUser sCUser);

    /**
     * Updates a sCUser.
     *
     * @param sCUser the entity to update.
     * @return the persisted entity.
     */
    SCUser update(SCUser sCUser);

    /**
     * Partially updates a sCUser.
     *
     * @param sCUser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SCUser> partialUpdate(SCUser sCUser);

    /**
     * Get all the sCUsers.
     *
     * @return the list of entities.
     */
    List<SCUser> findAll();

    /**
     * Get the "id" sCUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SCUser> findOne(String id);

    /**
     * Delete the "id" sCUser.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
