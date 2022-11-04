package org.sc.backend.repository;

import org.sc.backend.domain.Stocks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Stocks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StocksRepository extends JpaRepository<Stocks, String>, JpaSpecificationExecutor<Stocks> {}
