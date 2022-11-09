package org.sc.backend.repository;

import org.sc.backend.domain.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TradeHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long>, JpaSpecificationExecutor<TradeHistory> {}
