package org.sc.backend.web.rest.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.sc.backend.domain.ScUser;
import org.sc.backend.repository.ScUserRepository;
import org.sc.backend.service.ScUserService;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.sc.backend.domain.ScUser}.
 */
@RestController
@RequestMapping("/api/admin")
public class ScUserResource {

    private final Logger log = LoggerFactory.getLogger(ScUserResource.class);

    private static final String ENTITY_NAME = "scUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScUserService scUserService;

    private final ScUserRepository scUserRepository;

    public ScUserResource(ScUserService scUserService, ScUserRepository scUserRepository) {
        this.scUserService = scUserService;
        this.scUserRepository = scUserRepository;
    }

    /**
     * {@code POST  /sc-users} : Create a new scUser.
     *
     * @param scUser the scUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scUser, or with status {@code 400 (Bad Request)} if the scUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sc-users")
    public ResponseEntity<ScUser> createScUser(@Valid @RequestBody ScUser scUser) throws URISyntaxException {
        log.debug("REST request to save ScUser : {}", scUser);
        if (scUser.getScUserId() != null) {
            throw new BadRequestAlertException("A new scUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScUser result = scUserService.save(scUser);
        return ResponseEntity
            .created(new URI("/api/sc-users/" + result.getScUserId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getScUserId()))
            .body(result);
    }

    /**
     * {@code PUT  /sc-users/:scUserId} : Updates an existing scUser.
     *
     * @param scUserId the id of the scUser to save.
     * @param scUser the scUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scUser,
     * or with status {@code 400 (Bad Request)} if the scUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sc-users/{scUserId}")
    public ResponseEntity<ScUser> updateScUser(
        @PathVariable(value = "scUserId", required = false) final String scUserId,
        @Valid @RequestBody ScUser scUser
    ) throws URISyntaxException {
        log.debug("REST request to update ScUser : {}, {}", scUserId, scUser);
        if (scUser.getScUserId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(scUserId, scUser.getScUserId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scUserRepository.existsById(scUserId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScUser result = scUserService.update(scUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scUser.getScUserId()))
            .body(result);
    }

    /**
     * {@code PATCH  /sc-users/:scUserId} : Partial updates given fields of an existing scUser, field will ignore if it is null
     *
     * @param scUserId the id of the scUser to save.
     * @param scUser the scUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scUser,
     * or with status {@code 400 (Bad Request)} if the scUser is not valid,
     * or with status {@code 404 (Not Found)} if the scUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the scUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sc-users/{scUserId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScUser> partialUpdateScUser(
        @PathVariable(value = "scUserId", required = false) final String scUserId,
        @NotNull @RequestBody ScUser scUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScUser partially : {}, {}", scUserId, scUser);
        if (scUser.getScUserId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(scUserId, scUser.getScUserId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scUserRepository.existsById(scUserId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScUser> result = scUserService.partialUpdate(scUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scUser.getScUserId())
        );
    }

    /**
     * {@code GET  /sc-users} : get all the scUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scUsers in body.
     */
    @GetMapping("/sc-users")
    public List<ScUser> getAllScUsers() {
        log.debug("REST request to get all ScUsers");
        return scUserService.findAll();
    }

    /**
     * {@code GET  /sc-users/:id} : get the "id" scUser.
     *
     * @param id the id of the scUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sc-users/{id}")
    public ResponseEntity<ScUser> getScUser(@PathVariable String id) {
        log.debug("REST request to get ScUser : {}", id);
        Optional<ScUser> scUser = scUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scUser);
    }

    /**
     * {@code DELETE  /sc-users/:id} : delete the "id" scUser.
     *
     * @param id the id of the scUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sc-users/{id}")
    public ResponseEntity<Void> deleteScUser(@PathVariable String id) {
        log.debug("REST request to delete ScUser : {}", id);
        scUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
