package org.sc.backend.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.sc.backend.domain.SCAccount;
import org.sc.backend.repository.SCAccountRepository;
import org.sc.backend.service.SCAccountService;
import org.sc.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.sc.backend.domain.SCAccount}.
 */
@RestController
@RequestMapping("/api")
public class SCAccountResource {

    private final Logger log = LoggerFactory.getLogger(SCAccountResource.class);

    private static final String ENTITY_NAME = "sCAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SCAccountService sCAccountService;

    private final SCAccountRepository sCAccountRepository;

    public SCAccountResource(SCAccountService sCAccountService, SCAccountRepository sCAccountRepository) {
        this.sCAccountService = sCAccountService;
        this.sCAccountRepository = sCAccountRepository;
    }

    /**
     * {@code POST  /sc-accounts} : Create a new sCAccount.
     *
     * @param sCAccount the sCAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sCAccount, or with status {@code 400 (Bad Request)} if the sCAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sc-accounts")
    public ResponseEntity<SCAccount> createSCAccount(@Valid @RequestBody SCAccount sCAccount) throws URISyntaxException {
        log.debug("REST request to save SCAccount : {}", sCAccount);
        if (sCAccount.getAccNo() != null) {
            throw new BadRequestAlertException("A new sCAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SCAccount result = sCAccountService.save(sCAccount);
        return ResponseEntity
            .created(new URI("/api/sc-accounts/" + result.getAccNo()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getAccNo().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sc-accounts/:accNo} : Updates an existing sCAccount.
     *
     * @param accNo the id of the sCAccount to save.
     * @param sCAccount the sCAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sCAccount,
     * or with status {@code 400 (Bad Request)} if the sCAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sCAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sc-accounts/{accNo}")
    public ResponseEntity<SCAccount> updateSCAccount(
        @PathVariable(value = "accNo", required = false) final Long accNo,
        @Valid @RequestBody SCAccount sCAccount
    ) throws URISyntaxException {
        log.debug("REST request to update SCAccount : {}, {}", accNo, sCAccount);
        if (sCAccount.getAccNo() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(accNo, sCAccount.getAccNo())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sCAccountRepository.existsById(accNo)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SCAccount result = sCAccountService.update(sCAccount);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sCAccount.getAccNo().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sc-accounts/:accNo} : Partial updates given fields of an existing sCAccount, field will ignore if it is null
     *
     * @param accNo the id of the sCAccount to save.
     * @param sCAccount the sCAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sCAccount,
     * or with status {@code 400 (Bad Request)} if the sCAccount is not valid,
     * or with status {@code 404 (Not Found)} if the sCAccount is not found,
     * or with status {@code 500 (Internal Server Error)} if the sCAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sc-accounts/{accNo}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SCAccount> partialUpdateSCAccount(
        @PathVariable(value = "accNo", required = false) final Long accNo,
        @NotNull @RequestBody SCAccount sCAccount
    ) throws URISyntaxException {
        log.debug("REST request to partial update SCAccount partially : {}, {}", accNo, sCAccount);
        if (sCAccount.getAccNo() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(accNo, sCAccount.getAccNo())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sCAccountRepository.existsById(accNo)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SCAccount> result = sCAccountService.partialUpdate(sCAccount);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sCAccount.getAccNo().toString())
        );
    }

    /**
     * {@code GET  /sc-accounts} : get all the sCAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sCAccounts in body.
     */
    @GetMapping("/sc-accounts")
    public List<SCAccount> getAllSCAccounts() {
        log.debug("REST request to get all SCAccounts");
        return sCAccountService.findAll();
    }

    /**
     * {@code GET  /sc-accounts/:id} : get the "id" sCAccount.
     *
     * @param id the id of the sCAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sCAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sc-accounts/{id}")
    public ResponseEntity<SCAccount> getSCAccount(@PathVariable Long id) {
        log.debug("REST request to get SCAccount : {}", id);
        Optional<SCAccount> sCAccount = sCAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sCAccount);
    }

    /**
     * {@code DELETE  /sc-accounts/:id} : delete the "id" sCAccount.
     *
     * @param id the id of the sCAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sc-accounts/{id}")
    public ResponseEntity<Void> deleteSCAccount(@PathVariable Long id) {
        log.debug("REST request to delete SCAccount : {}", id);
        sCAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
