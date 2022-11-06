package org.sc.backend.repository;

import org.sc.backend.domain.ScUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScUserRepository extends JpaRepository<ScUser, String> {}
