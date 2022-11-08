package org.sc.backend.repository;

import org.sc.backend.domain.Positions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Positions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PositionsRepository extends JpaRepository<Positions, Long>, JpaSpecificationExecutor<Positions> {}
