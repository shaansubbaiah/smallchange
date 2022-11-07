package org.sc.backend.web.rest.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.sc.backend.domain.ScAccount;
import org.sc.backend.repository.ScAccountRepository;
import org.sc.backend.service.ScAccountService;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.sc.backend.domain.ScAccount}.
 */
@RestController
@RequestMapping("/api/admin")
public class ScAccountResource {

    private final Logger log = LoggerFactory.getLogger(ScAccountResource.class);

    private static final String ENTITY_NAME = "scAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScAccountService scAccountService;

    private final ScAccountRepository scAccountRepository;

    public ScAccountResource(ScAccountService scAccountService, ScAccountRepository scAccountRepository) {
        this.scAccountService = scAccountService;
        this.scAccountRepository = scAccountRepository;
    }

    /**
     * {@code POST  /sc-accounts} : Create a new scAccount.
     *
     * @param scAccount the scAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scAccount, or with status {@code 400 (Bad Request)} if the scAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sc-accounts")
    public ResponseEntity<ScAccount> createScAccount(@Valid @RequestBody ScAccount scAccount) throws URISyntaxException {
        log.debug("REST request to save ScAccount : {}", scAccount);
        if (scAccount.getAccNo() != null) {
            throw new BadRequestAlertException("A new scAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScAccount result = scAccountService.save(scAccount);
        return ResponseEntity
            .created(new URI("/api/sc-accounts/" + result.getAccNo()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getAccNo().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sc-accounts/:accNo} : Updates an existing scAccount.
     *
     * @param accNo the id of the scAccount to save.
     * @param scAccount the scAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scAccount,
     * or with status {@code 400 (Bad Request)} if the scAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sc-accounts/{accNo}")
    public ResponseEntity<ScAccount> updateScAccount(
        @PathVariable(value = "accNo", required = false) final Long accNo,
        @Valid @RequestBody ScAccount scAccount
    ) throws URISyntaxException {
        log.debug("REST request to update ScAccount : {}, {}", accNo, scAccount);
        if (scAccount.getAccNo() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(accNo, scAccount.getAccNo())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scAccountRepository.existsById(accNo)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScAccount result = scAccountService.update(scAccount);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scAccount.getAccNo().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sc-accounts/:accNo} : Partial updates given fields of an existing scAccount, field will ignore if it is null
     *
     * @param accNo the id of the scAccount to save.
     * @param scAccount the scAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scAccount,
     * or with status {@code 400 (Bad Request)} if the scAccount is not valid,
     * or with status {@code 404 (Not Found)} if the scAccount is not found,
     * or with status {@code 500 (Internal Server Error)} if the scAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sc-accounts/{accNo}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScAccount> partialUpdateScAccount(
        @PathVariable(value = "accNo", required = false) final Long accNo,
        @NotNull @RequestBody ScAccount scAccount
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScAccount partially : {}, {}", accNo, scAccount);
        if (scAccount.getAccNo() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(accNo, scAccount.getAccNo())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scAccountRepository.existsById(accNo)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScAccount> result = scAccountService.partialUpdate(scAccount);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scAccount.getAccNo().toString())
        );
    }

    /**
     * {@code GET  /sc-accounts} : get all the scAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scAccounts in body.
     */
    @GetMapping("/sc-accounts")
    public List<ScAccount> getAllScAccounts() {
        log.debug("REST request to get all ScAccounts");
        return scAccountService.findAll();
    }

    /**
     * {@code GET  /sc-accounts/:id} : get the "id" scAccount.
     *
     * @param id the id of the scAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sc-accounts/{id}")
    public ResponseEntity<ScAccount> getScAccount(@PathVariable Long id) {
        log.debug("REST request to get ScAccount : {}", id);
        Optional<ScAccount> scAccount = scAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scAccount);
    }

    /**
     * {@code DELETE  /sc-accounts/:id} : delete the "id" scAccount.
     *
     * @param id the id of the scAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sc-accounts/{id}")
    public ResponseEntity<Void> deleteScAccount(@PathVariable Long id) {
        log.debug("REST request to delete ScAccount : {}", id);
        scAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
