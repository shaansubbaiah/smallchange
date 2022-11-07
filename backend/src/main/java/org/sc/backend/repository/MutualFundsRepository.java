package org.sc.backend.repository;

import org.sc.backend.domain.MutualFunds;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MutualFunds entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MutualFundsRepository extends JpaRepository<MutualFunds, String>, JpaSpecificationExecutor<MutualFunds> {}
