package org.sc.backend.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.sc.backend.domain.*; // for static metamodels
import org.sc.backend.domain.Stocks;
import org.sc.backend.repository.StocksRepository;
import org.sc.backend.service.criteria.StocksCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Stocks} entities in the database.
 * The main input is a {@link StocksCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Stocks} or a {@link Page} of {@link Stocks} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StocksQueryService extends QueryService<Stocks> {

    private final Logger log = LoggerFactory.getLogger(StocksQueryService.class);

    private final StocksRepository stocksRepository;

    public StocksQueryService(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    /**
     * Return a {@link List} of {@link Stocks} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Stocks> findByCriteria(StocksCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Stocks> specification = createSpecification(criteria);
        return stocksRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Stocks} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Stocks> findByCriteria(StocksCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Stocks> specification = createSpecification(criteria);
        return stocksRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StocksCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Stocks> specification = createSpecification(criteria);
        return stocksRepository.count(specification);
    }

    /**
     * Function to convert {@link StocksCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Stocks> createSpecification(StocksCriteria criteria) {
        Specification<Stocks> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Stocks_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Stocks_.name));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Stocks_.quantity));
            }
            if (criteria.getCurrentPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentPrice(), Stocks_.currentPrice));
            }
            if (criteria.getStockType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStockType(), Stocks_.stockType));
            }
            if (criteria.getExchangeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExchangeName(), Stocks_.exchangeName));
            }
        }
        return specification;
    }
}
