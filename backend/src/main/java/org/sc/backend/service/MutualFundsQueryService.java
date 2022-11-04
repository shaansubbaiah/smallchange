package org.sc.backend.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.sc.backend.domain.*; // for static metamodels
import org.sc.backend.domain.MutualFunds;
import org.sc.backend.repository.MutualFundsRepository;
import org.sc.backend.service.criteria.MutualFundsCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MutualFunds} entities in the database.
 * The main input is a {@link MutualFundsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MutualFunds} or a {@link Page} of {@link MutualFunds} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MutualFundsQueryService extends QueryService<MutualFunds> {

    private final Logger log = LoggerFactory.getLogger(MutualFundsQueryService.class);

    private final MutualFundsRepository mutualFundsRepository;

    public MutualFundsQueryService(MutualFundsRepository mutualFundsRepository) {
        this.mutualFundsRepository = mutualFundsRepository;
    }

    /**
     * Return a {@link List} of {@link MutualFunds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MutualFunds> findByCriteria(MutualFundsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MutualFunds> specification = createSpecification(criteria);
        return mutualFundsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MutualFunds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MutualFunds> findByCriteria(MutualFundsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MutualFunds> specification = createSpecification(criteria);
        return mutualFundsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MutualFundsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MutualFunds> specification = createSpecification(criteria);
        return mutualFundsRepository.count(specification);
    }

    /**
     * Function to convert {@link MutualFundsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MutualFunds> createSpecification(MutualFundsCriteria criteria) {
        Specification<MutualFunds> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), MutualFunds_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MutualFunds_.name));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), MutualFunds_.quantity));
            }
            if (criteria.getCurrentPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentPrice(), MutualFunds_.currentPrice));
            }
            if (criteria.getInterestRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterestRate(), MutualFunds_.interestRate));
            }
            if (criteria.getMfType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMfType(), MutualFunds_.mfType));
            }
        }
        return specification;
    }
}
