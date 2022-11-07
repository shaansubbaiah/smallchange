package org.sc.backend.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sc.backend.IntegrationTest;
import org.sc.backend.domain.Preferences;
import org.sc.backend.domain.enumeration.IncomeCategory;
import org.sc.backend.domain.enumeration.InvestmentLength;
import org.sc.backend.domain.enumeration.RiskTolerance;
import org.sc.backend.repository.PreferencesRepository;
import org.sc.backend.web.rest.admin.PreferencesResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PreferencesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferencesResourceIT {

    private static final String DEFAULT_INVESTMENT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_INVESTMENT_PURPOSE = "BBBBBBBBBB";

    private static final RiskTolerance DEFAULT_RISK_TOLERANCE = RiskTolerance.CONSERVATIVE;
    private static final RiskTolerance UPDATED_RISK_TOLERANCE = RiskTolerance.BELOW_AVERAGE;

    private static final IncomeCategory DEFAULT_INCOME_CATEGORY = IncomeCategory.LOW;
    private static final IncomeCategory UPDATED_INCOME_CATEGORY = IncomeCategory.LOWER_MIDDLE;

    private static final InvestmentLength DEFAULT_INVESTMENT_LENGTH = InvestmentLength.SHORT;
    private static final InvestmentLength UPDATED_INVESTMENT_LENGTH = InvestmentLength.MEDIUM;

    private static final String ENTITY_API_URL = "/api/preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{scUserId}";

    @Autowired
    private PreferencesRepository preferencesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreferencesMockMvc;

    private Preferences preferences;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preferences createEntity(EntityManager em) {
        Preferences preferences = new Preferences()
            .investmentPurpose(DEFAULT_INVESTMENT_PURPOSE)
            .riskTolerance(DEFAULT_RISK_TOLERANCE)
            .incomeCategory(DEFAULT_INCOME_CATEGORY)
            .investmentLength(DEFAULT_INVESTMENT_LENGTH);
        return preferences;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preferences createUpdatedEntity(EntityManager em) {
        Preferences preferences = new Preferences()
            .investmentPurpose(UPDATED_INVESTMENT_PURPOSE)
            .riskTolerance(UPDATED_RISK_TOLERANCE)
            .incomeCategory(UPDATED_INCOME_CATEGORY)
            .investmentLength(UPDATED_INVESTMENT_LENGTH);
        return preferences;
    }

    @BeforeEach
    public void initTest() {
        preferences = createEntity(em);
    }

    @Test
    @Transactional
    void createPreferences() throws Exception {
        int databaseSizeBeforeCreate = preferencesRepository.findAll().size();
        // Create the Preferences
        restPreferencesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferences)))
            .andExpect(status().isCreated());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeCreate + 1);
        Preferences testPreferences = preferencesList.get(preferencesList.size() - 1);
        assertThat(testPreferences.getInvestmentPurpose()).isEqualTo(DEFAULT_INVESTMENT_PURPOSE);
        assertThat(testPreferences.getRiskTolerance()).isEqualTo(DEFAULT_RISK_TOLERANCE);
        assertThat(testPreferences.getIncomeCategory()).isEqualTo(DEFAULT_INCOME_CATEGORY);
        assertThat(testPreferences.getInvestmentLength()).isEqualTo(DEFAULT_INVESTMENT_LENGTH);
    }

    @Test
    @Transactional
    void createPreferencesWithExistingId() throws Exception {
        // Create the Preferences with an existing ID
        preferences.setScUserId("existing_id");

        int databaseSizeBeforeCreate = preferencesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferencesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferences)))
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPreferences() throws Exception {
        // Initialize the database
        preferences.setScUserId(UUID.randomUUID().toString());
        preferencesRepository.saveAndFlush(preferences);

        // Get all the preferencesList
        restPreferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=scUserId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].scUserId").value(hasItem(preferences.getScUserId())))
            .andExpect(jsonPath("$.[*].investmentPurpose").value(hasItem(DEFAULT_INVESTMENT_PURPOSE)))
            .andExpect(jsonPath("$.[*].riskTolerance").value(hasItem(DEFAULT_RISK_TOLERANCE.toString())))
            .andExpect(jsonPath("$.[*].incomeCategory").value(hasItem(DEFAULT_INCOME_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].investmentLength").value(hasItem(DEFAULT_INVESTMENT_LENGTH.toString())));
    }

    @Test
    @Transactional
    void getPreferences() throws Exception {
        // Initialize the database
        preferences.setScUserId(UUID.randomUUID().toString());
        preferencesRepository.saveAndFlush(preferences);

        // Get the preferences
        restPreferencesMockMvc
            .perform(get(ENTITY_API_URL_ID, preferences.getScUserId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.scUserId").value(preferences.getScUserId()))
            .andExpect(jsonPath("$.investmentPurpose").value(DEFAULT_INVESTMENT_PURPOSE))
            .andExpect(jsonPath("$.riskTolerance").value(DEFAULT_RISK_TOLERANCE.toString()))
            .andExpect(jsonPath("$.incomeCategory").value(DEFAULT_INCOME_CATEGORY.toString()))
            .andExpect(jsonPath("$.investmentLength").value(DEFAULT_INVESTMENT_LENGTH.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPreferences() throws Exception {
        // Get the preferences
        restPreferencesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPreferences() throws Exception {
        // Initialize the database
        preferences.setScUserId(UUID.randomUUID().toString());
        preferencesRepository.saveAndFlush(preferences);

        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();

        // Update the preferences
        Preferences updatedPreferences = preferencesRepository.findById(preferences.getScUserId()).get();
        // Disconnect from session so that the updates on updatedPreferences are not directly saved in db
        em.detach(updatedPreferences);
        updatedPreferences
            .investmentPurpose(UPDATED_INVESTMENT_PURPOSE)
            .riskTolerance(UPDATED_RISK_TOLERANCE)
            .incomeCategory(UPDATED_INCOME_CATEGORY)
            .investmentLength(UPDATED_INVESTMENT_LENGTH);

        restPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreferences.getScUserId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreferences))
            )
            .andExpect(status().isOk());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
        Preferences testPreferences = preferencesList.get(preferencesList.size() - 1);
        assertThat(testPreferences.getInvestmentPurpose()).isEqualTo(UPDATED_INVESTMENT_PURPOSE);
        assertThat(testPreferences.getRiskTolerance()).isEqualTo(UPDATED_RISK_TOLERANCE);
        assertThat(testPreferences.getIncomeCategory()).isEqualTo(UPDATED_INCOME_CATEGORY);
        assertThat(testPreferences.getInvestmentLength()).isEqualTo(UPDATED_INVESTMENT_LENGTH);
    }

    @Test
    @Transactional
    void putNonExistingPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setScUserId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferences.getScUserId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferences)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePreferencesWithPatch() throws Exception {
        // Initialize the database
        preferences.setScUserId(UUID.randomUUID().toString());
        preferencesRepository.saveAndFlush(preferences);

        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();

        // Update the preferences using partial update
        Preferences partialUpdatedPreferences = new Preferences();
        partialUpdatedPreferences.setScUserId(preferences.getScUserId());

        partialUpdatedPreferences.riskTolerance(UPDATED_RISK_TOLERANCE);

        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferences.getScUserId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferences))
            )
            .andExpect(status().isOk());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
        Preferences testPreferences = preferencesList.get(preferencesList.size() - 1);
        assertThat(testPreferences.getInvestmentPurpose()).isEqualTo(DEFAULT_INVESTMENT_PURPOSE);
        assertThat(testPreferences.getRiskTolerance()).isEqualTo(UPDATED_RISK_TOLERANCE);
        assertThat(testPreferences.getIncomeCategory()).isEqualTo(DEFAULT_INCOME_CATEGORY);
        assertThat(testPreferences.getInvestmentLength()).isEqualTo(DEFAULT_INVESTMENT_LENGTH);
    }

    @Test
    @Transactional
    void fullUpdatePreferencesWithPatch() throws Exception {
        // Initialize the database
        preferences.setScUserId(UUID.randomUUID().toString());
        preferencesRepository.saveAndFlush(preferences);

        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();

        // Update the preferences using partial update
        Preferences partialUpdatedPreferences = new Preferences();
        partialUpdatedPreferences.setScUserId(preferences.getScUserId());

        partialUpdatedPreferences
            .investmentPurpose(UPDATED_INVESTMENT_PURPOSE)
            .riskTolerance(UPDATED_RISK_TOLERANCE)
            .incomeCategory(UPDATED_INCOME_CATEGORY)
            .investmentLength(UPDATED_INVESTMENT_LENGTH);

        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferences.getScUserId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferences))
            )
            .andExpect(status().isOk());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
        Preferences testPreferences = preferencesList.get(preferencesList.size() - 1);
        assertThat(testPreferences.getInvestmentPurpose()).isEqualTo(UPDATED_INVESTMENT_PURPOSE);
        assertThat(testPreferences.getRiskTolerance()).isEqualTo(UPDATED_RISK_TOLERANCE);
        assertThat(testPreferences.getIncomeCategory()).isEqualTo(UPDATED_INCOME_CATEGORY);
        assertThat(testPreferences.getInvestmentLength()).isEqualTo(UPDATED_INVESTMENT_LENGTH);
    }

    @Test
    @Transactional
    void patchNonExistingPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setScUserId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferences.getScUserId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setScUserId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePreferences() throws Exception {
        // Initialize the database
        preferences.setScUserId(UUID.randomUUID().toString());
        preferencesRepository.saveAndFlush(preferences);

        int databaseSizeBeforeDelete = preferencesRepository.findAll().size();

        // Delete the preferences
        restPreferencesMockMvc
            .perform(delete(ENTITY_API_URL_ID, preferences.getScUserId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
