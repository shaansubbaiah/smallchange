package org.sc.backend.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.sc.backend.domain.*; // for static metamodels
import org.sc.backend.domain.Bonds;
import org.sc.backend.repository.BondsRepository;
import org.sc.backend.service.criteria.BondsCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Bonds} entities in the database.
 * The main input is a {@link BondsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Bonds} or a {@link Page} of {@link Bonds} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BondsQueryService extends QueryService<Bonds> {

    private final Logger log = LoggerFactory.getLogger(BondsQueryService.class);

    private final BondsRepository bondsRepository;

    public BondsQueryService(BondsRepository bondsRepository) {
        this.bondsRepository = bondsRepository;
    }

    /**
     * Return a {@link List} of {@link Bonds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Bonds> findByCriteria(BondsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bonds> specification = createSpecification(criteria);
        return bondsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Bonds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Bonds> findByCriteria(BondsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bonds> specification = createSpecification(criteria);
        return bondsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BondsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bonds> specification = createSpecification(criteria);
        return bondsRepository.count(specification);
    }

    /**
     * Function to convert {@link BondsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bonds> createSpecification(BondsCriteria criteria) {
        Specification<Bonds> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Bonds_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Bonds_.name));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Bonds_.quantity));
            }
            if (criteria.getCurrentPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentPrice(), Bonds_.currentPrice));
            }
            if (criteria.getInterestRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterestRate(), Bonds_.interestRate));
            }
            if (criteria.getDurationMonths() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDurationMonths(), Bonds_.durationMonths));
            }
            if (criteria.getBondType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBondType(), Bonds_.bondType));
            }
            if (criteria.getExchangeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExchangeName(), Bonds_.exchangeName));
            }
        }
        return specification;
    }
}
