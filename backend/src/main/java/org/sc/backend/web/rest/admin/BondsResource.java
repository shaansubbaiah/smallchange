package org.sc.backend.web.rest.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.sc.backend.domain.Bonds;
import org.sc.backend.repository.BondsRepository;
import org.sc.backend.service.BondsQueryService;
import org.sc.backend.service.BondsService;
import org.sc.backend.service.criteria.BondsCriteria;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.sc.backend.domain.Bonds}.
 */
@RestController
@RequestMapping("/api/admin")
public class BondsResource {

    private final Logger log = LoggerFactory.getLogger(BondsResource.class);

    private static final String ENTITY_NAME = "bonds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BondsService bondsService;

    private final BondsRepository bondsRepository;

    private final BondsQueryService bondsQueryService;

    public BondsResource(BondsService bondsService, BondsRepository bondsRepository, BondsQueryService bondsQueryService) {
        this.bondsService = bondsService;
        this.bondsRepository = bondsRepository;
        this.bondsQueryService = bondsQueryService;
    }

    /**
     * {@code POST  /bonds} : Create a new bonds.
     *
     * @param bonds the bonds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonds, or with status {@code 400 (Bad Request)} if the bonds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bonds")
    public ResponseEntity<Bonds> createBonds(@Valid @RequestBody Bonds bonds) throws URISyntaxException {
        log.debug("REST request to save Bonds : {}", bonds);
        if (bonds.getCode() != null) {
            throw new BadRequestAlertException("A new bonds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bonds result = bondsService.save(bonds);
        return ResponseEntity
            .created(new URI("/api/bonds/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getCode()))
            .body(result);
    }

    /**
     * {@code PUT  /bonds/:code} : Updates an existing bonds.
     *
     * @param code the id of the bonds to save.
     * @param bonds the bonds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonds,
     * or with status {@code 400 (Bad Request)} if the bonds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bonds/{code}")
    public ResponseEntity<Bonds> updateBonds(
        @PathVariable(value = "code", required = false) final String code,
        @Valid @RequestBody Bonds bonds
    ) throws URISyntaxException {
        log.debug("REST request to update Bonds : {}, {}", code, bonds);
        if (bonds.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, bonds.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bondsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bonds result = bondsService.update(bonds);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bonds.getCode()))
            .body(result);
    }

    /**
     * {@code PATCH  /bonds/:code} : Partial updates given fields of an existing bonds, field will ignore if it is null
     *
     * @param code the id of the bonds to save.
     * @param bonds the bonds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonds,
     * or with status {@code 400 (Bad Request)} if the bonds is not valid,
     * or with status {@code 404 (Not Found)} if the bonds is not found,
     * or with status {@code 500 (Internal Server Error)} if the bonds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bonds/{code}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bonds> partialUpdateBonds(
        @PathVariable(value = "code", required = false) final String code,
        @NotNull @RequestBody Bonds bonds
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bonds partially : {}, {}", code, bonds);
        if (bonds.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, bonds.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bondsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bonds> result = bondsService.partialUpdate(bonds);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bonds.getCode())
        );
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
     * {@code GET  /bonds/count} : count all the bonds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bonds/count")
    public ResponseEntity<Long> countBonds(BondsCriteria criteria) {
        log.debug("REST request to count Bonds by criteria: {}", criteria);
        return ResponseEntity.ok().body(bondsQueryService.countByCriteria(criteria));
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
     * {@code DELETE  /bonds/:id} : delete the "id" bonds.
     *
     * @param id the id of the bonds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bonds/{id}")
    public ResponseEntity<Void> deleteBonds(@PathVariable String id) {
        log.debug("REST request to delete Bonds : {}", id);
        bondsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
