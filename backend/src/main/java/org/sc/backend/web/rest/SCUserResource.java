package org.sc.backend.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.sc.backend.domain.SCUser;
import org.sc.backend.repository.SCUserRepository;
import org.sc.backend.service.SCUserService;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.sc.backend.domain.SCUser}.
 */
@RestController
@RequestMapping("/api")
public class SCUserResource {

    private final Logger log = LoggerFactory.getLogger(SCUserResource.class);

    private static final String ENTITY_NAME = "sCUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SCUserService sCUserService;

    private final SCUserRepository sCUserRepository;

    public SCUserResource(SCUserService sCUserService, SCUserRepository sCUserRepository) {
        this.sCUserService = sCUserService;
        this.sCUserRepository = sCUserRepository;
    }

    /**
     * {@code POST  /sc-users} : Create a new sCUser.
     *
     * @param sCUser the sCUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sCUser, or with status {@code 400 (Bad Request)} if the sCUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sc-users")
    public ResponseEntity<SCUser> createSCUser(@Valid @RequestBody SCUser sCUser) throws URISyntaxException {
        log.debug("REST request to save SCUser : {}", sCUser);
        if (sCUser.getScUserId() != null) {
            throw new BadRequestAlertException("A new sCUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SCUser result = sCUserService.save(sCUser);
        return ResponseEntity
            .created(new URI("/api/sc-users/" + result.getScUserId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getScUserId()))
            .body(result);
    }

    /**
     * {@code PUT  /sc-users/:scUserId} : Updates an existing sCUser.
     *
     * @param scUserId the id of the sCUser to save.
     * @param sCUser the sCUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sCUser,
     * or with status {@code 400 (Bad Request)} if the sCUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sCUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sc-users/{scUserId}")
    public ResponseEntity<SCUser> updateSCUser(
        @PathVariable(value = "scUserId", required = false) final String scUserId,
        @Valid @RequestBody SCUser sCUser
    ) throws URISyntaxException {
        log.debug("REST request to update SCUser : {}, {}", scUserId, sCUser);
        if (sCUser.getScUserId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(scUserId, sCUser.getScUserId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sCUserRepository.existsById(scUserId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SCUser result = sCUserService.update(sCUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sCUser.getScUserId()))
            .body(result);
    }

    /**
     * {@code PATCH  /sc-users/:scUserId} : Partial updates given fields of an existing sCUser, field will ignore if it is null
     *
     * @param scUserId the id of the sCUser to save.
     * @param sCUser the sCUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sCUser,
     * or with status {@code 400 (Bad Request)} if the sCUser is not valid,
     * or with status {@code 404 (Not Found)} if the sCUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the sCUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sc-users/{scUserId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SCUser> partialUpdateSCUser(
        @PathVariable(value = "scUserId", required = false) final String scUserId,
        @NotNull @RequestBody SCUser sCUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update SCUser partially : {}, {}", scUserId, sCUser);
        if (sCUser.getScUserId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(scUserId, sCUser.getScUserId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sCUserRepository.existsById(scUserId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SCUser> result = sCUserService.partialUpdate(sCUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sCUser.getScUserId())
        );
    }

    /**
     * {@code GET  /sc-users} : get all the sCUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sCUsers in body.
     */
    @GetMapping("/sc-users")
    public List<SCUser> getAllSCUsers() {
        log.debug("REST request to get all SCUsers");
        return sCUserService.findAll();
    }

    /**
     * {@code GET  /sc-users/:id} : get the "id" sCUser.
     *
     * @param id the id of the sCUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sCUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sc-users/{id}")
    public ResponseEntity<SCUser> getSCUser(@PathVariable String id) {
        log.debug("REST request to get SCUser : {}", id);
        Optional<SCUser> sCUser = sCUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sCUser);
    }

    /**
     * {@code DELETE  /sc-users/:id} : delete the "id" sCUser.
     *
     * @param id the id of the sCUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sc-users/{id}")
    public ResponseEntity<Void> deleteSCUser(@PathVariable String id) {
        log.debug("REST request to delete SCUser : {}", id);
        sCUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
