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
import org.sc.backend.domain.SCAccount;
import org.sc.backend.repository.SCAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SCAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SCAccountResourceIT {

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
    private SCAccountRepository sCAccountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSCAccountMockMvc;

    private SCAccount sCAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SCAccount createEntity(EntityManager em) {
        SCAccount sCAccount = new SCAccount()
            .scUserId(DEFAULT_SC_USER_ID)
            .bankName(DEFAULT_BANK_NAME)
            .accType(DEFAULT_ACC_TYPE)
            .accBalance(DEFAULT_ACC_BALANCE);
        return sCAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SCAccount createUpdatedEntity(EntityManager em) {
        SCAccount sCAccount = new SCAccount()
            .scUserId(UPDATED_SC_USER_ID)
            .bankName(UPDATED_BANK_NAME)
            .accType(UPDATED_ACC_TYPE)
            .accBalance(UPDATED_ACC_BALANCE);
        return sCAccount;
    }

    @BeforeEach
    public void initTest() {
        sCAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createSCAccount() throws Exception {
        int databaseSizeBeforeCreate = sCAccountRepository.findAll().size();
        // Create the SCAccount
        restSCAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCAccount)))
            .andExpect(status().isCreated());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeCreate + 1);
        SCAccount testSCAccount = sCAccountList.get(sCAccountList.size() - 1);
        assertThat(testSCAccount.getScUserId()).isEqualTo(DEFAULT_SC_USER_ID);
        assertThat(testSCAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testSCAccount.getAccType()).isEqualTo(DEFAULT_ACC_TYPE);
        assertThat(testSCAccount.getAccBalance()).isEqualTo(DEFAULT_ACC_BALANCE);
    }

    @Test
    @Transactional
    void createSCAccountWithExistingId() throws Exception {
        // Create the SCAccount with an existing ID
        sCAccount.setAccNo(1L);

        int databaseSizeBeforeCreate = sCAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSCAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCAccount)))
            .andExpect(status().isBadRequest());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkScUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCAccountRepository.findAll().size();
        // set the field null
        sCAccount.setScUserId(null);

        // Create the SCAccount, which fails.

        restSCAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCAccount)))
            .andExpect(status().isBadRequest());

        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCAccountRepository.findAll().size();
        // set the field null
        sCAccount.setBankName(null);

        // Create the SCAccount, which fails.

        restSCAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCAccount)))
            .andExpect(status().isBadRequest());

        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCAccountRepository.findAll().size();
        // set the field null
        sCAccount.setAccType(null);

        // Create the SCAccount, which fails.

        restSCAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCAccount)))
            .andExpect(status().isBadRequest());

        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSCAccounts() throws Exception {
        // Initialize the database
        sCAccountRepository.saveAndFlush(sCAccount);

        // Get all the sCAccountList
        restSCAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=accNo,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].accNo").value(hasItem(sCAccount.getAccNo().intValue())))
            .andExpect(jsonPath("$.[*].scUserId").value(hasItem(DEFAULT_SC_USER_ID)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accType").value(hasItem(DEFAULT_ACC_TYPE)))
            .andExpect(jsonPath("$.[*].accBalance").value(hasItem(DEFAULT_ACC_BALANCE.doubleValue())));
    }

    @Test
    @Transactional
    void getSCAccount() throws Exception {
        // Initialize the database
        sCAccountRepository.saveAndFlush(sCAccount);

        // Get the sCAccount
        restSCAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, sCAccount.getAccNo()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.accNo").value(sCAccount.getAccNo().intValue()))
            .andExpect(jsonPath("$.scUserId").value(DEFAULT_SC_USER_ID))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.accType").value(DEFAULT_ACC_TYPE))
            .andExpect(jsonPath("$.accBalance").value(DEFAULT_ACC_BALANCE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingSCAccount() throws Exception {
        // Get the sCAccount
        restSCAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSCAccount() throws Exception {
        // Initialize the database
        sCAccountRepository.saveAndFlush(sCAccount);

        int databaseSizeBeforeUpdate = sCAccountRepository.findAll().size();

        // Update the sCAccount
        SCAccount updatedSCAccount = sCAccountRepository.findById(sCAccount.getAccNo()).get();
        // Disconnect from session so that the updates on updatedSCAccount are not directly saved in db
        em.detach(updatedSCAccount);
        updatedSCAccount.scUserId(UPDATED_SC_USER_ID).bankName(UPDATED_BANK_NAME).accType(UPDATED_ACC_TYPE).accBalance(UPDATED_ACC_BALANCE);

        restSCAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSCAccount.getAccNo())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSCAccount))
            )
            .andExpect(status().isOk());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeUpdate);
        SCAccount testSCAccount = sCAccountList.get(sCAccountList.size() - 1);
        assertThat(testSCAccount.getScUserId()).isEqualTo(UPDATED_SC_USER_ID);
        assertThat(testSCAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testSCAccount.getAccType()).isEqualTo(UPDATED_ACC_TYPE);
        assertThat(testSCAccount.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void putNonExistingSCAccount() throws Exception {
        int databaseSizeBeforeUpdate = sCAccountRepository.findAll().size();
        sCAccount.setAccNo(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSCAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sCAccount.getAccNo())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sCAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSCAccount() throws Exception {
        int databaseSizeBeforeUpdate = sCAccountRepository.findAll().size();
        sCAccount.setAccNo(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSCAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sCAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSCAccount() throws Exception {
        int databaseSizeBeforeUpdate = sCAccountRepository.findAll().size();
        sCAccount.setAccNo(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSCAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sCAccount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSCAccountWithPatch() throws Exception {
        // Initialize the database
        sCAccountRepository.saveAndFlush(sCAccount);

        int databaseSizeBeforeUpdate = sCAccountRepository.findAll().size();

        // Update the sCAccount using partial update
        SCAccount partialUpdatedSCAccount = new SCAccount();
        partialUpdatedSCAccount.setAccNo(sCAccount.getAccNo());

        partialUpdatedSCAccount.accType(UPDATED_ACC_TYPE).accBalance(UPDATED_ACC_BALANCE);

        restSCAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSCAccount.getAccNo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSCAccount))
            )
            .andExpect(status().isOk());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeUpdate);
        SCAccount testSCAccount = sCAccountList.get(sCAccountList.size() - 1);
        assertThat(testSCAccount.getScUserId()).isEqualTo(DEFAULT_SC_USER_ID);
        assertThat(testSCAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testSCAccount.getAccType()).isEqualTo(UPDATED_ACC_TYPE);
        assertThat(testSCAccount.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void fullUpdateSCAccountWithPatch() throws Exception {
        // Initialize the database
        sCAccountRepository.saveAndFlush(sCAccount);

        int databaseSizeBeforeUpdate = sCAccountRepository.findAll().size();

        // Update the sCAccount using partial update
        SCAccount partialUpdatedSCAccount = new SCAccount();
        partialUpdatedSCAccount.setAccNo(sCAccount.getAccNo());

        partialUpdatedSCAccount
            .scUserId(UPDATED_SC_USER_ID)
            .bankName(UPDATED_BANK_NAME)
            .accType(UPDATED_ACC_TYPE)
            .accBalance(UPDATED_ACC_BALANCE);

        restSCAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSCAccount.getAccNo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSCAccount))
            )
            .andExpect(status().isOk());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeUpdate);
        SCAccount testSCAccount = sCAccountList.get(sCAccountList.size() - 1);
        assertThat(testSCAccount.getScUserId()).isEqualTo(UPDATED_SC_USER_ID);
        assertThat(testSCAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testSCAccount.getAccType()).isEqualTo(UPDATED_ACC_TYPE);
        assertThat(testSCAccount.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void patchNonExistingSCAccount() throws Exception {
        int databaseSizeBeforeUpdate = sCAccountRepository.findAll().size();
        sCAccount.setAccNo(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSCAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sCAccount.getAccNo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sCAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSCAccount() throws Exception {
        int databaseSizeBeforeUpdate = sCAccountRepository.findAll().size();
        sCAccount.setAccNo(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSCAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sCAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSCAccount() throws Exception {
        int databaseSizeBeforeUpdate = sCAccountRepository.findAll().size();
        sCAccount.setAccNo(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSCAccountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sCAccount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SCAccount in the database
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSCAccount() throws Exception {
        // Initialize the database
        sCAccountRepository.saveAndFlush(sCAccount);

        int databaseSizeBeforeDelete = sCAccountRepository.findAll().size();

        // Delete the sCAccount
        restSCAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, sCAccount.getAccNo()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SCAccount> sCAccountList = sCAccountRepository.findAll();
        assertThat(sCAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
