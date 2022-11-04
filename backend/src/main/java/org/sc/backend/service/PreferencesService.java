package org.sc.backend.service;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.Preferences;

/**
 * Service Interface for managing {@link Preferences}.
 */
public interface PreferencesService {
    /**
     * Save a preferences.
     *
     * @param preferences the entity to save.
     * @return the persisted entity.
     */
    Preferences save(Preferences preferences);

    /**
     * Updates a preferences.
     *
     * @param preferences the entity to update.
     * @return the persisted entity.
     */
    Preferences update(Preferences preferences);

    /**
     * Partially updates a preferences.
     *
     * @param preferences the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Preferences> partialUpdate(Preferences preferences);

    /**
     * Get all the preferences.
     *
     * @return the list of entities.
     */
    List<Preferences> findAll();
    /**
     * Get all the Preferences where SCUser is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Preferences> findAllWhereSCUserIsNull();

    /**
     * Get the "id" preferences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Preferences> findOne(String id);

    /**
     * Delete the "id" preferences.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
