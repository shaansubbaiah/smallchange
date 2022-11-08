package org.sc.backend.service;

import org.sc.backend.domain.ScAccount;
import org.sc.backend.domain.ScAccount_;
import org.sc.backend.domain.ScUser_;
import org.sc.backend.repository.ScAccountRepository;
import org.sc.backend.service.criteria.ScAccountCriteria;
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
 * Service for executing complex queries for {@link ScAccount} entities in the database.
 * The main input is a {@link ScAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScAccount} or a {@link Page} of {@link ScAccount} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScAccountQueryService extends QueryService<ScAccount> {

    private final Logger log = LoggerFactory.getLogger(ScAccountQueryService.class);

    private final ScAccountRepository scAccountRepository;

    public ScAccountQueryService(ScAccountRepository scAccountRepository) {
        this.scAccountRepository = scAccountRepository;
    }

    /**
     * Return a {@link List} of {@link ScAccount} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScAccount> findByCriteria(ScAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ScAccount> specification = createSpecification(criteria);
        return scAccountRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ScAccount} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScAccount> findByCriteria(ScAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ScAccount> specification = createSpecification(criteria);
        return scAccountRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ScAccount> specification = createSpecification(criteria);
        return scAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link ScAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ScAccount> createSpecification(ScAccountCriteria criteria) {
        Specification<ScAccount> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getAccNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccNo(), ScAccount_.accNo));
            }
            if (criteria.getScUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getScUserId(), ScAccount_.scUserId));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), ScAccount_.bankName));
            }
            if (criteria.getAccType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccType(), ScAccount_.accType));
            }
            if (criteria.getAccBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccBalance(), ScAccount_.accBalance));
            }
            if (criteria.getScUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getScUserId(),
                            root -> root.join(ScAccount_.scUser, JoinType.LEFT).get(ScUser_.scUserId)
                        )
                    );
            }
        }
        return specification;
    }
}
