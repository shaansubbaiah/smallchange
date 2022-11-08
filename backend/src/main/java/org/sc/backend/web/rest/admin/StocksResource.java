package org.sc.backend.web.rest.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.sc.backend.domain.Stocks;
import org.sc.backend.repository.StocksRepository;
import org.sc.backend.service.StocksQueryService;
import org.sc.backend.service.StocksService;
import org.sc.backend.service.criteria.StocksCriteria;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Stocks}.
 */
@RestController
@RequestMapping("/api/admin")
public class StocksResource {

    private final Logger log = LoggerFactory.getLogger(StocksResource.class);

    private static final String ENTITY_NAME = "stocks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StocksService stocksService;

    private final StocksRepository stocksRepository;

    private final StocksQueryService stocksQueryService;

    public StocksResource(StocksService stocksService, StocksRepository stocksRepository, StocksQueryService stocksQueryService) {
        this.stocksService = stocksService;
        this.stocksRepository = stocksRepository;
        this.stocksQueryService = stocksQueryService;
    }

    /**
     * {@code POST  /stocks} : Create a new stocks.
     *
     * @param stocks the stocks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stocks, or with status {@code 400 (Bad Request)} if the stocks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stocks")
    public ResponseEntity<Stocks> createStocks(@Valid @RequestBody Stocks stocks) throws URISyntaxException {
        log.debug("REST request to save Stocks : {}", stocks);
        if (stocks.getCode() != null) {
            throw new BadRequestAlertException("A new stocks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stocks result = stocksService.save(stocks);
        return ResponseEntity
            .created(new URI("/api/stocks/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getCode()))
            .body(result);
    }

    /**
     * {@code PUT  /stocks/:code} : Updates an existing stocks.
     *
     * @param code the id of the stocks to save.
     * @param stocks the stocks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stocks,
     * or with status {@code 400 (Bad Request)} if the stocks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stocks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stocks/{code}")
    public ResponseEntity<Stocks> updateStocks(
        @PathVariable(value = "code", required = false) final String code,
        @Valid @RequestBody Stocks stocks
    ) throws URISyntaxException {
        log.debug("REST request to update Stocks : {}, {}", code, stocks);
        if (stocks.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, stocks.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stocksRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Stocks result = stocksService.update(stocks);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stocks.getCode()))
            .body(result);
    }

    /**
     * {@code PATCH  /stocks/:code} : Partial updates given fields of an existing stocks, field will ignore if it is null
     *
     * @param code the id of the stocks to save.
     * @param stocks the stocks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stocks,
     * or with status {@code 400 (Bad Request)} if the stocks is not valid,
     * or with status {@code 404 (Not Found)} if the stocks is not found,
     * or with status {@code 500 (Internal Server Error)} if the stocks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stocks/{code}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Stocks> partialUpdateStocks(
        @PathVariable(value = "code", required = false) final String code,
        @NotNull @RequestBody Stocks stocks
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stocks partially : {}, {}", code, stocks);
        if (stocks.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, stocks.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stocksRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Stocks> result = stocksService.partialUpdate(stocks);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stocks.getCode())
        );
    }

    /**
     * {@code GET  /stocks} : get all the stocks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stocks in body.
     */
    @GetMapping("/stocks")
    public ResponseEntity<List<Stocks>> getAllStocks(StocksCriteria criteria) {
        log.debug("REST request to get Stocks by criteria: {}", criteria);
        List<Stocks> entityList = stocksQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /stocks/count} : count all the stocks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/stocks/count")
    public ResponseEntity<Long> countStocks(StocksCriteria criteria) {
        log.debug("REST request to count Stocks by criteria: {}", criteria);
        return ResponseEntity.ok().body(stocksQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stocks/:id} : get the "id" stocks.
     *
     * @param id the id of the stocks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stocks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stocks/{id}")
    public ResponseEntity<Stocks> getStocks(@PathVariable String id) {
        log.debug("REST request to get Stocks : {}", id);
        Optional<Stocks> stocks = stocksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stocks);
    }

    /**
     * {@code DELETE  /stocks/:id} : delete the "id" stocks.
     *
     * @param id the id of the stocks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<Void> deleteStocks(@PathVariable String id) {
        log.debug("REST request to delete Stocks : {}", id);
        stocksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
