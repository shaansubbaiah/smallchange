package org.sc.backend.service;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.Positions;

/**
 * Service Interface for managing {@link Positions}.
 */
public interface PositionsService {
    /**
     * Save a positions.
     *
     * @param positions the entity to save.
     * @return the persisted entity.
     */
    Positions save(Positions positions);

    /**
     * Updates a positions.
     *
     * @param positions the entity to update.
     * @return the persisted entity.
     */
    Positions update(Positions positions);

    /**
     * Partially updates a positions.
     *
     * @param positions the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Positions> partialUpdate(Positions positions);

    /**
     * Get all the positions.
     *
     * @return the list of entities.
     */
    List<Positions> findAll();

    /**
     * Get the "id" positions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Positions> findOne(Long id);

    /**
     * Delete the "id" positions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
