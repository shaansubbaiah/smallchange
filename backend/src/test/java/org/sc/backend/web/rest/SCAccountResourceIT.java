package org.sc.backend.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sc.backend.IntegrationTest;
import org.sc.backend.domain.ScAccount;
import org.sc.backend.repository.ScAccountRepository;
import org.sc.backend.web.rest.admin.ScAccountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ScAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScAccountResourceIT {

    private static final String DEFAULT_SC_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SC_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACC_TYPE = "BBBBBBBBBB";

    private static final Float DEFAULT_ACC_BALANCE = 0F;
    private static final Float UPDATED_ACC_BALANCE = 1F;

    private static final String ENTITY_API_URL = "/api/sc-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{accNo}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScAccountRepository scAccountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScAccountMockMvc;

    private ScAccount scAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScAccount createEntity(EntityManager em) {
        ScAccount scAccount = new ScAccount()
            .scUserId(DEFAULT_SC_USER_ID)
            .bankName(DEFAULT_BANK_NAME)
            .accType(DEFAULT_ACC_TYPE)
            .accBalance(DEFAULT_ACC_BALANCE);
        return scAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScAccount createUpdatedEntity(EntityManager em) {
        ScAccount scAccount = new ScAccount()
            .scUserId(UPDATED_SC_USER_ID)
            .bankName(UPDATED_BANK_NAME)
            .accType(UPDATED_ACC_TYPE)
            .accBalance(UPDATED_ACC_BALANCE);
        return scAccount;
    }

    @BeforeEach
    public void initTest() {
        scAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createScAccount() throws Exception {
        int databaseSizeBeforeCreate = scAccountRepository.findAll().size();
        // Create the ScAccount
        restScAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scAccount)))
            .andExpect(status().isCreated());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeCreate + 1);
        ScAccount testScAccount = scAccountList.get(scAccountList.size() - 1);
        assertThat(testScAccount.getScUserId()).isEqualTo(DEFAULT_SC_USER_ID);
        assertThat(testScAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testScAccount.getAccType()).isEqualTo(DEFAULT_ACC_TYPE);
        assertThat(testScAccount.getAccBalance()).isEqualTo(DEFAULT_ACC_BALANCE);
    }

    @Test
    @Transactional
    void createScAccountWithExistingId() throws Exception {
        // Create the ScAccount with an existing ID
        scAccount.setAccNo(1L);

        int databaseSizeBeforeCreate = scAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scAccount)))
            .andExpect(status().isBadRequest());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkScUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = scAccountRepository.findAll().size();
        // set the field null
        scAccount.setScUserId(null);

        // Create the ScAccount, which fails.

        restScAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scAccount)))
            .andExpect(status().isBadRequest());

        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scAccountRepository.findAll().size();
        // set the field null
        scAccount.setBankName(null);

        // Create the ScAccount, which fails.

        restScAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scAccount)))
            .andExpect(status().isBadRequest());

        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = scAccountRepository.findAll().size();
        // set the field null
        scAccount.setAccType(null);

        // Create the ScAccount, which fails.

        restScAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scAccount)))
            .andExpect(status().isBadRequest());

        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllScAccounts() throws Exception {
        // Initialize the database
        scAccountRepository.saveAndFlush(scAccount);

        // Get all the scAccountList
        restScAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=accNo,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].accNo").value(hasItem(scAccount.getAccNo().intValue())))
            .andExpect(jsonPath("$.[*].scUserId").value(hasItem(DEFAULT_SC_USER_ID)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accType").value(hasItem(DEFAULT_ACC_TYPE)))
            .andExpect(jsonPath("$.[*].accBalance").value(hasItem(DEFAULT_ACC_BALANCE.doubleValue())));
    }

    @Test
    @Transactional
    void getScAccount() throws Exception {
        // Initialize the database
        scAccountRepository.saveAndFlush(scAccount);

        // Get the scAccount
        restScAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, scAccount.getAccNo()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.accNo").value(scAccount.getAccNo().intValue()))
            .andExpect(jsonPath("$.scUserId").value(DEFAULT_SC_USER_ID))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.accType").value(DEFAULT_ACC_TYPE))
            .andExpect(jsonPath("$.accBalance").value(DEFAULT_ACC_BALANCE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingScAccount() throws Exception {
        // Get the scAccount
        restScAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScAccount() throws Exception {
        // Initialize the database
        scAccountRepository.saveAndFlush(scAccount);

        int databaseSizeBeforeUpdate = scAccountRepository.findAll().size();

        // Update the scAccount
        ScAccount updatedScAccount = scAccountRepository.findById(scAccount.getAccNo()).get();
        // Disconnect from session so that the updates on updatedScAccount are not directly saved in db
        em.detach(updatedScAccount);
        updatedScAccount.scUserId(UPDATED_SC_USER_ID).bankName(UPDATED_BANK_NAME).accType(UPDATED_ACC_TYPE).accBalance(UPDATED_ACC_BALANCE);

        restScAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScAccount.getAccNo())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedScAccount))
            )
            .andExpect(status().isOk());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeUpdate);
        ScAccount testScAccount = scAccountList.get(scAccountList.size() - 1);
        assertThat(testScAccount.getScUserId()).isEqualTo(UPDATED_SC_USER_ID);
        assertThat(testScAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testScAccount.getAccType()).isEqualTo(UPDATED_ACC_TYPE);
        assertThat(testScAccount.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void putNonExistingScAccount() throws Exception {
        int databaseSizeBeforeUpdate = scAccountRepository.findAll().size();
        scAccount.setAccNo(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scAccount.getAccNo())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScAccount() throws Exception {
        int databaseSizeBeforeUpdate = scAccountRepository.findAll().size();
        scAccount.setAccNo(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScAccount() throws Exception {
        int databaseSizeBeforeUpdate = scAccountRepository.findAll().size();
        scAccount.setAccNo(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scAccount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScAccountWithPatch() throws Exception {
        // Initialize the database
        scAccountRepository.saveAndFlush(scAccount);

        int databaseSizeBeforeUpdate = scAccountRepository.findAll().size();

        // Update the scAccount using partial update
        ScAccount partialUpdatedScAccount = new ScAccount();
        partialUpdatedScAccount.setAccNo(scAccount.getAccNo());

        partialUpdatedScAccount.accType(UPDATED_ACC_TYPE).accBalance(UPDATED_ACC_BALANCE);

        restScAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScAccount.getAccNo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScAccount))
            )
            .andExpect(status().isOk());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeUpdate);
        ScAccount testScAccount = scAccountList.get(scAccountList.size() - 1);
        assertThat(testScAccount.getScUserId()).isEqualTo(DEFAULT_SC_USER_ID);
        assertThat(testScAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testScAccount.getAccType()).isEqualTo(UPDATED_ACC_TYPE);
        assertThat(testScAccount.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void fullUpdateScAccountWithPatch() throws Exception {
        // Initialize the database
        scAccountRepository.saveAndFlush(scAccount);

        int databaseSizeBeforeUpdate = scAccountRepository.findAll().size();

        // Update the scAccount using partial update
        ScAccount partialUpdatedScAccount = new ScAccount();
        partialUpdatedScAccount.setAccNo(scAccount.getAccNo());

        partialUpdatedScAccount
            .scUserId(UPDATED_SC_USER_ID)
            .bankName(UPDATED_BANK_NAME)
            .accType(UPDATED_ACC_TYPE)
            .accBalance(UPDATED_ACC_BALANCE);

        restScAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScAccount.getAccNo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScAccount))
            )
            .andExpect(status().isOk());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeUpdate);
        ScAccount testScAccount = scAccountList.get(scAccountList.size() - 1);
        assertThat(testScAccount.getScUserId()).isEqualTo(UPDATED_SC_USER_ID);
        assertThat(testScAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testScAccount.getAccType()).isEqualTo(UPDATED_ACC_TYPE);
        assertThat(testScAccount.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void patchNonExistingScAccount() throws Exception {
        int databaseSizeBeforeUpdate = scAccountRepository.findAll().size();
        scAccount.setAccNo(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scAccount.getAccNo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScAccount() throws Exception {
        int databaseSizeBeforeUpdate = scAccountRepository.findAll().size();
        scAccount.setAccNo(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScAccount() throws Exception {
        int databaseSizeBeforeUpdate = scAccountRepository.findAll().size();
        scAccount.setAccNo(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScAccountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(scAccount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScAccount in the database
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScAccount() throws Exception {
        // Initialize the database
        scAccountRepository.saveAndFlush(scAccount);

        int databaseSizeBeforeDelete = scAccountRepository.findAll().size();

        // Delete the scAccount
        restScAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, scAccount.getAccNo()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScAccount> scAccountList = scAccountRepository.findAll();
        assertThat(scAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
