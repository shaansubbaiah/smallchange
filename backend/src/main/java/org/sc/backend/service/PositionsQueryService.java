package org.sc.backend.service;

import org.sc.backend.domain.Positions;
import org.sc.backend.domain.Positions_;
import org.sc.backend.domain.ScUser_;
import org.sc.backend.repository.PositionsRepository;
import org.sc.backend.service.criteria.PositionsCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link Positions} entities in the database.
 * The main input is a {@link PositionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Positions} or a {@link Page} of {@link Positions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PositionsQueryService extends QueryService<Positions> {

    private final Logger log = LoggerFactory.getLogger(PositionsQueryService.class);

    private final PositionsRepository positionsRepository;

    public PositionsQueryService(PositionsRepository positionsRepository) {
        this.positionsRepository = positionsRepository;
    }

    /**
     * Return a {@link List} of {@link Positions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Positions> findByCriteria(PositionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Positions> specification = createSpecification(criteria);
        return positionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Positions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Positions> findByCriteria(PositionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Positions> specification = createSpecification(criteria);
        return positionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PositionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Positions> specification = createSpecification(criteria);
        return positionsRepository.count(specification);
    }

    /**
     * Function to convert {@link PositionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Positions> createSpecification(PositionsCriteria criteria) {
        Specification<Positions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null

            if (criteria.getPositionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPositionId(), Positions_.positionId));
            }
            if (criteria.getScUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getScUserId(), Positions_.scUserId));
            }
            if (criteria.getAssetCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetCode(), Positions_.assetCode));
            }
            if (criteria.getAssetType() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetType(), Positions_.assetType));
            }
            if (criteria.getBuyPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBuyPrice(), Positions_.buyPrice));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Positions_.quantity));
            }
            if (criteria.getScUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getScUserId(),
                            root -> root.join(Positions_.scUser, JoinType.LEFT).get(ScUser_.scUserId)
                        )
                    );
            }
        }
        return specification;
    }
}
