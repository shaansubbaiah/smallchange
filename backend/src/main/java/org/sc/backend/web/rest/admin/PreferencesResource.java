package org.sc.backend.web.rest.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.sc.backend.domain.Preferences;
import org.sc.backend.repository.PreferencesRepository;
import org.sc.backend.service.PreferencesService;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.sc.backend.domain.Preferences}.
 */
@RestController
@RequestMapping("/api/admin")
public class PreferencesResource {

    private final Logger log = LoggerFactory.getLogger(PreferencesResource.class);

    private static final String ENTITY_NAME = "preferences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferencesService preferencesService;

    private final PreferencesRepository preferencesRepository;

    public PreferencesResource(PreferencesService preferencesService, PreferencesRepository preferencesRepository) {
        this.preferencesService = preferencesService;
        this.preferencesRepository = preferencesRepository;
    }

    /**
     * {@code POST  /preferences} : Create a new preferences.
     *
     * @param preferences the preferences to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferences, or with status {@code 400 (Bad Request)} if the preferences has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferences")
    public ResponseEntity<Preferences> createPreferences(@RequestBody Preferences preferences) throws URISyntaxException {
        log.debug("REST request to save Preferences : {}", preferences);
        if (preferences.getScUserId() != null) {
            throw new BadRequestAlertException("A new preferences cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Preferences result = preferencesService.save(preferences);
        return ResponseEntity
            .created(new URI("/api/preferences/" + result.getScUserId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getScUserId()))
            .body(result);
    }

    /**
     * {@code PUT  /preferences/:scUserId} : Updates an existing preferences.
     *
     * @param scUserId the id of the preferences to save.
     * @param preferences the preferences to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferences,
     * or with status {@code 400 (Bad Request)} if the preferences is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferences couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferences/{scUserId}")
    public ResponseEntity<Preferences> updatePreferences(
        @PathVariable(value = "scUserId", required = false) final String scUserId,
        @RequestBody Preferences preferences
    ) throws URISyntaxException {
        log.debug("REST request to update Preferences : {}, {}", scUserId, preferences);
        if (preferences.getScUserId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(scUserId, preferences.getScUserId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferencesRepository.existsById(scUserId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Preferences result = preferencesService.update(preferences);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, preferences.getScUserId()))
            .body(result);
    }

    /**
     * {@code PATCH  /preferences/:scUserId} : Partial updates given fields of an existing preferences, field will ignore if it is null
     *
     * @param scUserId the id of the preferences to save.
     * @param preferences the preferences to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferences,
     * or with status {@code 400 (Bad Request)} if the preferences is not valid,
     * or with status {@code 404 (Not Found)} if the preferences is not found,
     * or with status {@code 500 (Internal Server Error)} if the preferences couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferences/{scUserId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Preferences> partialUpdatePreferences(
        @PathVariable(value = "scUserId", required = false) final String scUserId,
        @RequestBody Preferences preferences
    ) throws URISyntaxException {
        log.debug("REST request to partial update Preferences partially : {}, {}", scUserId, preferences);
        if (preferences.getScUserId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(scUserId, preferences.getScUserId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferencesRepository.existsById(scUserId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Preferences> result = preferencesService.partialUpdate(preferences);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, preferences.getScUserId())
        );
    }

    /**
     * {@code GET  /preferences} : get all the preferences.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferences in body.
     */
    @GetMapping("/preferences")
    public List<Preferences> getAllPreferences(@RequestParam(required = false) String filter) {
        if ("scuser-is-null".equals(filter)) {
            log.debug("REST request to get all Preferencess where scUser is null");
            return preferencesService.findAllWhereScUserIsNull();
        }
        log.debug("REST request to get all Preferences");
        return preferencesService.findAll();
    }

    /**
     * {@code GET  /preferences/:id} : get the "id" preferences.
     *
     * @param id the id of the preferences to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferences, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferences/{id}")
    public ResponseEntity<Preferences> getPreferences(@PathVariable String id) {
        log.debug("REST request to get Preferences : {}", id);
        Optional<Preferences> preferences = preferencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferences);
    }

    /**
     * {@code DELETE  /preferences/:id} : delete the "id" preferences.
     *
     * @param id the id of the preferences to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferences/{id}")
    public ResponseEntity<Void> deletePreferences(@PathVariable String id) {
        log.debug("REST request to delete Preferences : {}", id);
        preferencesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
