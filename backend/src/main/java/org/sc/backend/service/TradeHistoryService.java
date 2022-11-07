package org.sc.backend.service;

import java.util.List;
import java.util.Optional;
import org.sc.backend.domain.TradeHistory;

/**
 * Service Interface for managing {@link TradeHistory}.
 */
public interface TradeHistoryService {
    /**
     * Save a tradeHistory.
     *
     * @param tradeHistory the entity to save.
     * @return the persisted entity.
     */
    TradeHistory save(TradeHistory tradeHistory);

    /**
     * Updates a tradeHistory.
     *
     * @param tradeHistory the entity to update.
     * @return the persisted entity.
     */
    TradeHistory update(TradeHistory tradeHistory);

    /**
     * Partially updates a tradeHistory.
     *
     * @param tradeHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TradeHistory> partialUpdate(TradeHistory tradeHistory);

    /**
     * Get all the tradeHistories.
     *
     * @return the list of entities.
     */
    List<TradeHistory> findAll();

    /**
     * Get the "id" tradeHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TradeHistory> findOne(Long id);

    /**
     * Delete the "id" tradeHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
