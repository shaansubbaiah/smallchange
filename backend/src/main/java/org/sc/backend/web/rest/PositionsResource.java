package org.sc.backend.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.sc.backend.domain.Positions;
import org.sc.backend.repository.PositionsRepository;
import org.sc.backend.service.PositionsService;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.sc.backend.domain.Positions}.
 */
@RestController
@RequestMapping("/api")
public class PositionsResource {

    private final Logger log = LoggerFactory.getLogger(PositionsResource.class);

    private static final String ENTITY_NAME = "positions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PositionsService positionsService;

    private final PositionsRepository positionsRepository;

    public PositionsResource(PositionsService positionsService, PositionsRepository positionsRepository) {
        this.positionsService = positionsService;
        this.positionsRepository = positionsRepository;
    }

    /**
     * {@code POST  /positions} : Create a new positions.
     *
     * @param positions the positions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new positions, or with status {@code 400 (Bad Request)} if the positions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/positions")
    public ResponseEntity<Positions> createPositions(@Valid @RequestBody Positions positions) throws URISyntaxException {
        log.debug("REST request to save Positions : {}", positions);
        if (positions.getPositionId() != null) {
            throw new BadRequestAlertException("A new positions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Positions result = positionsService.save(positions);
        return ResponseEntity
            .created(new URI("/api/positions/" + result.getPositionId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getPositionId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /positions/:positionId} : Updates an existing positions.
     *
     * @param positionId the id of the positions to save.
     * @param positions the positions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated positions,
     * or with status {@code 400 (Bad Request)} if the positions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the positions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/positions/{positionId}")
    public ResponseEntity<Positions> updatePositions(
        @PathVariable(value = "positionId", required = false) final Long positionId,
        @Valid @RequestBody Positions positions
    ) throws URISyntaxException {
        log.debug("REST request to update Positions : {}, {}", positionId, positions);
        if (positions.getPositionId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(positionId, positions.getPositionId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!positionsRepository.existsById(positionId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Positions result = positionsService.update(positions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, positions.getPositionId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /positions/:positionId} : Partial updates given fields of an existing positions, field will ignore if it is null
     *
     * @param positionId the id of the positions to save.
     * @param positions the positions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated positions,
     * or with status {@code 400 (Bad Request)} if the positions is not valid,
     * or with status {@code 404 (Not Found)} if the positions is not found,
     * or with status {@code 500 (Internal Server Error)} if the positions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/positions/{positionId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Positions> partialUpdatePositions(
        @PathVariable(value = "positionId", required = false) final Long positionId,
        @NotNull @RequestBody Positions positions
    ) throws URISyntaxException {
        log.debug("REST request to partial update Positions partially : {}, {}", positionId, positions);
        if (positions.getPositionId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(positionId, positions.getPositionId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!positionsRepository.existsById(positionId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Positions> result = positionsService.partialUpdate(positions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, positions.getPositionId().toString())
        );
    }

    /**
     * {@code GET  /positions} : get all the positions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of positions in body.
     */
    @GetMapping("/positions")
    public List<Positions> getAllPositions() {
        log.debug("REST request to get all Positions");
        return positionsService.findAll();
    }

    /**
     * {@code GET  /positions/:id} : get the "id" positions.
     *
     * @param id the id of the positions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the positions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/positions/{id}")
    public ResponseEntity<Positions> getPositions(@PathVariable Long id) {
        log.debug("REST request to get Positions : {}", id);
        Optional<Positions> positions = positionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(positions);
    }

    /**
     * {@code DELETE  /positions/:id} : delete the "id" positions.
     *
     * @param id the id of the positions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/positions/{id}")
    public ResponseEntity<Void> deletePositions(@PathVariable Long id) {
        log.debug("REST request to delete Positions : {}", id);
        positionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
