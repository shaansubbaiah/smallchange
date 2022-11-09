package org.sc.backend.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.sc.backend.domain.*; // for static metamodels
import org.sc.backend.domain.TradeHistory;
import org.sc.backend.repository.TradeHistoryRepository;
import org.sc.backend.service.criteria.TradeHistoryCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TradeHistory} entities in the database.
 * The main input is a {@link TradeHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TradeHistory} or a {@link Page} of {@link TradeHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TradeHistoryQueryService extends QueryService<TradeHistory> {

    private final Logger log = LoggerFactory.getLogger(TradeHistoryQueryService.class);

    private final TradeHistoryRepository tradeHistoryRepository;

    public TradeHistoryQueryService(TradeHistoryRepository tradeHistoryRepository) {
        this.tradeHistoryRepository = tradeHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link TradeHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TradeHistory> findByCriteria(TradeHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TradeHistory> specification = createSpecification(criteria);
        return tradeHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TradeHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TradeHistory> findByCriteria(TradeHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TradeHistory> specification = createSpecification(criteria);
        return tradeHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TradeHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TradeHistory> specification = createSpecification(criteria);
        return tradeHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link TradeHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TradeHistory> createSpecification(TradeHistoryCriteria criteria) {
        Specification<TradeHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getTradeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTradeId(), TradeHistory_.tradeId));
            }
            if (criteria.getScUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getScUserId(), TradeHistory_.scUserId));
            }
            if (criteria.getAssetCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetCode(), TradeHistory_.assetCode));
            }
            if (criteria.getAssetType() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetType(), TradeHistory_.assetType));
            }
            if (criteria.getTradePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTradePrice(), TradeHistory_.tradePrice));
            }
            if (criteria.getTradeType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTradeType(), TradeHistory_.tradeType));
            }
            if (criteria.getTradeQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTradeQuantity(), TradeHistory_.tradeQuantity));
            }
            if (criteria.getTradeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTradeDate(), TradeHistory_.tradeDate));
            }
            if (criteria.getScUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getScUserId(),
                            root -> root.join(TradeHistory_.scUser, JoinType.LEFT).get(ScUser_.scUserId)
                        )
                    );
            }
        }
        return specification;
    }
}
