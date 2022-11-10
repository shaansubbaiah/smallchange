package org.sc.backend.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.sc.backend.domain.*; // for static metamodels
import org.sc.backend.domain.ScUser;
import org.sc.backend.repository.ScUserRepository;
import org.sc.backend.service.criteria.ScUserCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ScUser} entities in the database.
 * The main input is a {@link ScUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScUser} or a {@link Page} of {@link ScUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScUserQueryService extends QueryService<ScUser> {

    private final Logger log = LoggerFactory.getLogger(ScUserQueryService.class);

    private final ScUserRepository scUserRepository;

    public ScUserQueryService(ScUserRepository scUserRepository) {
        this.scUserRepository = scUserRepository;
    }

    /**
     * Return a {@link List} of {@link ScUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScUser> findByCriteria(ScUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ScUser> specification = createSpecification(criteria);
        return scUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ScUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScUser> findByCriteria(ScUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ScUser> specification = createSpecification(criteria);
        return scUserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ScUser> specification = createSpecification(criteria);
        return scUserRepository.count(specification);
    }

    /**
     * Function to convert {@link ScUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ScUser> createSpecification(ScUserCriteria criteria) {
        Specification<ScUser> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getScUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getScUserId(), ScUser_.scUserId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ScUser_.name));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ScUser_.email));
            }
            if (criteria.getPasswordHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPasswordHash(), ScUser_.passwordHash));
            }
            if (criteria.getTotalStocksInvestment() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalStocksInvestment(), ScUser_.totalStocksInvestment));
            }
            if (criteria.getTotalBondsInvestment() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalBondsInvestment(), ScUser_.totalBondsInvestment));
            }
            if (criteria.getTotalMfInvestment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMfInvestment(), ScUser_.totalMfInvestment));
            }
            if (criteria.getScUserRole() != null) {
                specification = specification.and(buildSpecification(criteria.getScUserRole(), ScUser_.scUserRole));
            }
            if (criteria.getScUserEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getScUserEnabled(), ScUser_.scUserEnabled));
            }
            if (criteria.getPreferencesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPreferencesId(),
                            root -> root.join(ScUser_.preferences, JoinType.LEFT).get(Preferences_.scUserId)
                        )
                    );
            }
            if (criteria.getPositionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPositionsId(),
                            root -> root.join(ScUser_.positions, JoinType.LEFT).get(Positions_.positionId)
                        )
                    );
            }
            if (criteria.getScAccountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getScAccountId(),
                            root -> root.join(ScUser_.scAccounts, JoinType.LEFT).get(ScAccount_.accNo)
                        )
                    );
            }
            if (criteria.getTradeHistoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTradeHistoryId(),
                            root -> root.join(ScUser_.tradeHistories, JoinType.LEFT).get(TradeHistory_.tradeId)
                        )
                    );
            }
        }
        return specification;
    }
}
