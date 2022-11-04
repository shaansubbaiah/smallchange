package org.sc.backend.repository;

import org.sc.backend.domain.SCAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SCAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SCAccountRepository extends JpaRepository<SCAccount, Long> {}
