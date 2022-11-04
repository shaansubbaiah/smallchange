package org.sc.backend.repository;

import org.sc.backend.domain.SCUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SCUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SCUserRepository extends JpaRepository<SCUser, String> {}
