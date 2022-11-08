package org.sc.backend.service;

import org.sc.backend.domain.Preferences;
import org.sc.backend.domain.Preferences_;
import org.sc.backend.domain.ScUser_;
import org.sc.backend.repository.PreferencesRepository;
import org.sc.backend.service.criteria.PreferencesCriteria;
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
 * Service for executing complex queries for {@link Preferences} entities in the database.
 * The main input is a {@link PreferencesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Preferences} or a {@link Page} of {@link Preferences} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PreferencesQueryService extends QueryService<Preferences> {

    private final Logger log = LoggerFactory.getLogger(PreferencesQueryService.class);

    private final PreferencesRepository preferencesRepository;

    public PreferencesQueryService(PreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    /**
     * Return a {@link List} of {@link Preferences} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Preferences> findByCriteria(PreferencesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Preferences> specification = createSpecification(criteria);
        return preferencesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Preferences} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Preferences> findByCriteria(PreferencesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Preferences> specification = createSpecification(criteria);
        return preferencesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PreferencesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Preferences> specification = createSpecification(criteria);
        return preferencesRepository.count(specification);
    }

    /**
     * Function to convert {@link PreferencesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Preferences> createSpecification(PreferencesCriteria criteria) {
        Specification<Preferences> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getScUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getScUserId(), Preferences_.scUserId));
            }
            if (criteria.getInvestmentPurpose() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getInvestmentPurpose(), Preferences_.investmentPurpose));
            }
            if (criteria.getRiskTolerance() != null) {
                specification = specification.and(buildSpecification(criteria.getRiskTolerance(), Preferences_.riskTolerance));
            }
            if (criteria.getIncomeCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getIncomeCategory(), Preferences_.incomeCategory));
            }
            if (criteria.getInvestmentLength() != null) {
                specification = specification.and(buildSpecification(criteria.getInvestmentLength(), Preferences_.investmentLength));
            }
            if (criteria.getScUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getScUserId(),
                            root -> root.join(Preferences_.scUser, JoinType.LEFT).get(ScUser_.scUserId)
                        )
                    );
            }
        }
        return specification;
    }
}
