package org.sc.backend.web.rest.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.sc.backend.domain.MutualFunds;
import org.sc.backend.repository.MutualFundsRepository;
import org.sc.backend.service.MutualFundsQueryService;
import org.sc.backend.service.MutualFundsService;
import org.sc.backend.service.criteria.MutualFundsCriteria;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link MutualFunds}.
 */
@RestController
@RequestMapping("/api/admin")
public class MutualFundsResource {

    private final Logger log = LoggerFactory.getLogger(MutualFundsResource.class);

    private static final String ENTITY_NAME = "mutualFunds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MutualFundsService mutualFundsService;

    private final MutualFundsRepository mutualFundsRepository;

    private final MutualFundsQueryService mutualFundsQueryService;

    public MutualFundsResource(
        MutualFundsService mutualFundsService,
        MutualFundsRepository mutualFundsRepository,
        MutualFundsQueryService mutualFundsQueryService
    ) {
        this.mutualFundsService = mutualFundsService;
        this.mutualFundsRepository = mutualFundsRepository;
        this.mutualFundsQueryService = mutualFundsQueryService;
    }

    /**
     * {@code POST  /mutual-funds} : Create a new mutualFunds.
     *
     * @param mutualFunds the mutualFunds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mutualFunds, or with status {@code 400 (Bad Request)} if the mutualFunds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mutual-funds")
    public ResponseEntity<MutualFunds> createMutualFunds(@Valid @RequestBody MutualFunds mutualFunds) throws URISyntaxException {
        log.debug("REST request to save MutualFunds : {}", mutualFunds);
        if (mutualFunds.getCode() != null) {
            throw new BadRequestAlertException("A new mutualFunds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MutualFunds result = mutualFundsService.save(mutualFunds);
        return ResponseEntity
            .created(new URI("/api/mutual-funds/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getCode()))
            .body(result);
    }

    /**
     * {@code PUT  /mutual-funds/:code} : Updates an existing mutualFunds.
     *
     * @param code the id of the mutualFunds to save.
     * @param mutualFunds the mutualFunds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mutualFunds,
     * or with status {@code 400 (Bad Request)} if the mutualFunds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mutualFunds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mutual-funds/{code}")
    public ResponseEntity<MutualFunds> updateMutualFunds(
        @PathVariable(value = "code", required = false) final String code,
        @Valid @RequestBody MutualFunds mutualFunds
    ) throws URISyntaxException {
        log.debug("REST request to update MutualFunds : {}, {}", code, mutualFunds);
        if (mutualFunds.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, mutualFunds.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mutualFundsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MutualFunds result = mutualFundsService.update(mutualFunds);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mutualFunds.getCode()))
            .body(result);
    }

    /**
     * {@code PATCH  /mutual-funds/:code} : Partial updates given fields of an existing mutualFunds, field will ignore if it is null
     *
     * @param code the id of the mutualFunds to save.
     * @param mutualFunds the mutualFunds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mutualFunds,
     * or with status {@code 400 (Bad Request)} if the mutualFunds is not valid,
     * or with status {@code 404 (Not Found)} if the mutualFunds is not found,
     * or with status {@code 500 (Internal Server Error)} if the mutualFunds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mutual-funds/{code}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MutualFunds> partialUpdateMutualFunds(
        @PathVariable(value = "code", required = false) final String code,
        @NotNull @RequestBody MutualFunds mutualFunds
    ) throws URISyntaxException {
        log.debug("REST request to partial update MutualFunds partially : {}, {}", code, mutualFunds);
        if (mutualFunds.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, mutualFunds.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mutualFundsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MutualFunds> result = mutualFundsService.partialUpdate(mutualFunds);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mutualFunds.getCode())
        );
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
     * {@code GET  /mutual-funds/count} : count all the mutualFunds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/mutual-funds/count")
    public ResponseEntity<Long> countMutualFunds(MutualFundsCriteria criteria) {
        log.debug("REST request to count MutualFunds by criteria: {}", criteria);
        return ResponseEntity.ok().body(mutualFundsQueryService.countByCriteria(criteria));
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

    /**
     * {@code DELETE  /mutual-funds/:id} : delete the "id" mutualFunds.
     *
     * @param id the id of the mutualFunds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mutual-funds/{id}")
    public ResponseEntity<Void> deleteMutualFunds(@PathVariable String id) {
        log.debug("REST request to delete MutualFunds : {}", id);
        mutualFundsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
