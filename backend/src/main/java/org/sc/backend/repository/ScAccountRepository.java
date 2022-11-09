package org.sc.backend.repository;

import org.sc.backend.domain.ScAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScAccountRepository extends JpaRepository<ScAccount, Long>, JpaSpecificationExecutor<ScAccount> {}
