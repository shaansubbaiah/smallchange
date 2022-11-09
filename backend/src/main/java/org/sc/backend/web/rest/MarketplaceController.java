package org.sc.backend.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sc.backend.domain.Bonds;
import org.sc.backend.domain.MutualFunds;
import org.sc.backend.domain.ScUser;
import org.sc.backend.domain.Stocks;
import org.sc.backend.repository.BondsRepository;
import org.sc.backend.repository.MutualFundsRepository;
import org.sc.backend.repository.StocksRepository;
import org.sc.backend.security.jwt.JWTFilter;
import org.sc.backend.security.jwt.TokenProvider;
import org.sc.backend.service.*;
import org.sc.backend.service.criteria.BondsCriteria;
import org.sc.backend.service.criteria.MutualFundsCriteria;
import org.sc.backend.service.criteria.StocksCriteria;
import org.sc.backend.service.impl.ScUserServiceImpl;
import org.sc.backend.web.rest.admin.StocksResource;
import org.sc.backend.web.rest.vm.LoginVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/marketplace")
public class MarketplaceController {

    private final Logger log = LoggerFactory.getLogger(StocksResource.class);
    private final StocksService stocksService;
    private final StocksRepository stocksRepository;
    private final StocksQueryService stocksQueryService;

    private final BondsService bondsService;

    private final BondsRepository bondsRepository;

    private final BondsQueryService bondsQueryService;

    private final MutualFundsService mutualFundsService;

    private final MutualFundsRepository mutualFundsRepository;

    private final MutualFundsQueryService mutualFundsQueryService;
    public MarketplaceController(
        StocksService stocksService, StocksRepository stocksRepository, StocksQueryService stocksQueryService,
        BondsService bondsService, BondsRepository bondsRepository, BondsQueryService bondsQueryService,
        MutualFundsService mutualFundsService, MutualFundsRepository mutualFundsRepository, MutualFundsQueryService mutualFundsQueryService
    ) {
        this.stocksService = stocksService;
        this.stocksRepository = stocksRepository;
        this.stocksQueryService = stocksQueryService;

        this.bondsService = bondsService;
        this.bondsRepository = bondsRepository;
        this.bondsQueryService = bondsQueryService;

        this.mutualFundsService = mutualFundsService;
        this.mutualFundsRepository = mutualFundsRepository;
        this.mutualFundsQueryService = mutualFundsQueryService;
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
     * {@code GET  /bonds} : get all the bonds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonds in body.
     */
    @GetMapping("/bonds")
    public ResponseEntity<List<Bonds>> getAllBonds(BondsCriteria criteria) {
        log.debug("REST request to get Bonds by criteria: {}", criteria);
        List<Bonds> entityList = bondsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /bonds/:id} : get the "id" bonds.
     *
     * @param id the id of the bonds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bonds/{id}")
    public ResponseEntity<Bonds> getBonds(@PathVariable String id) {
        log.debug("REST request to get Bonds : {}", id);
        Optional<Bonds> bonds = bondsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonds);
    }

    /**
     * {@code GET  /mutual-funds} : get all the mutualFunds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mutualFunds in body.
     */
    @GetMapping("/mutual-funds")
    public ResponseEntity<List<MutualFunds>> getAllMutualFunds(MutualFundsCriteria criteria) {
        log.debug("REST request to get MutualFunds by criteria: {}", criteria);
        List<MutualFunds> entityList = mutualFundsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /mutual-funds/:id} : get the "id" mutualFunds.
     *
     * @param id the id of the mutualFunds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mutualFunds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mutual-funds/{id}")
    public ResponseEntity<MutualFunds> getMutualFunds(@PathVariable String id) {
        log.debug("REST request to get MutualFunds : {}", id);
        Optional<MutualFunds> mutualFunds = mutualFundsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mutualFunds);
    }
}
