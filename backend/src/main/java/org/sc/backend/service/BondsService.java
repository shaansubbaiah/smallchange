package org.sc.backend.service;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.Bonds;

/**
 * Service Interface for managing {@link Bonds}.
 */
public interface BondsService {
    /**
     * Save a bonds.
     *
     * @param bonds the entity to save.
     * @return the persisted entity.
     */
    Bonds save(Bonds bonds);

    /**
     * Updates a bonds.
     *
     * @param bonds the entity to update.
     * @return the persisted entity.
     */
    Bonds update(Bonds bonds);

    /**
     * Partially updates a bonds.
     *
     * @param bonds the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Bonds> partialUpdate(Bonds bonds);

    /**
     * Get all the bonds.
     *
     * @return the list of entities.
     */
    List<Bonds> findAll();

    /**
     * Get the "id" bonds.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bonds> findOne(String id);

    /**
     * Delete the "id" bonds.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
