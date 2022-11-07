package org.sc.backend.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sc.backend.IntegrationTest;
import org.sc.backend.domain.TradeHistory;
import org.sc.backend.domain.enumeration.AssetType;
import org.sc.backend.repository.TradeHistoryRepository;
import org.sc.backend.web.rest.admin.TradeHistoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TradeHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TradeHistoryResourceIT {

    private static final String DEFAULT_SC_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SC_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_CODE = "BBBBBBBBBB";

    private static final AssetType DEFAULT_ASSET_TYPE = AssetType.STOCK;
    private static final AssetType UPDATED_ASSET_TYPE = AssetType.MUTUALFUND;

    private static final Float DEFAULT_TRADE_PRICE = 1F;
    private static final Float UPDATED_TRADE_PRICE = 2F;

    private static final String DEFAULT_TRADE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRADE_QUANTITY = 1;
    private static final Integer UPDATED_TRADE_QUANTITY = 2;

    private static final LocalDate DEFAULT_TRADE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRADE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/trade-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{tradeId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TradeHistoryRepository tradeHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTradeHistoryMockMvc;

    private TradeHistory tradeHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradeHistory createEntity(EntityManager em) {
        TradeHistory tradeHistory = new TradeHistory()
            .scUserId(DEFAULT_SC_USER_ID)
            .assetCode(DEFAULT_ASSET_CODE)
            .assetType(DEFAULT_ASSET_TYPE)
            .tradePrice(DEFAULT_TRADE_PRICE)
            .tradeType(DEFAULT_TRADE_TYPE)
            .tradeQuantity(DEFAULT_TRADE_QUANTITY)
            .tradeDate(DEFAULT_TRADE_DATE);
        return tradeHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradeHistory createUpdatedEntity(EntityManager em) {
        TradeHistory tradeHistory = new TradeHistory()
            .scUserId(UPDATED_SC_USER_ID)
            .assetCode(UPDATED_ASSET_CODE)
            .assetType(UPDATED_ASSET_TYPE)
            .tradePrice(UPDATED_TRADE_PRICE)
            .tradeType(UPDATED_TRADE_TYPE)
            .tradeQuantity(UPDATED_TRADE_QUANTITY)
            .tradeDate(UPDATED_TRADE_DATE);
        return tradeHistory;
    }

    @BeforeEach
    public void initTest() {
        tradeHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createTradeHistory() throws Exception {
        int databaseSizeBeforeCreate = tradeHistoryRepository.findAll().size();
        // Create the TradeHistory
        restTradeHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isCreated());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TradeHistory testTradeHistory = tradeHistoryList.get(tradeHistoryList.size() - 1);
        assertThat(testTradeHistory.getScUserId()).isEqualTo(DEFAULT_SC_USER_ID);
        assertThat(testTradeHistory.getAssetCode()).isEqualTo(DEFAULT_ASSET_CODE);
        assertThat(testTradeHistory.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
        assertThat(testTradeHistory.getTradePrice()).isEqualTo(DEFAULT_TRADE_PRICE);
        assertThat(testTradeHistory.getTradeType()).isEqualTo(DEFAULT_TRADE_TYPE);
        assertThat(testTradeHistory.getTradeQuantity()).isEqualTo(DEFAULT_TRADE_QUANTITY);
        assertThat(testTradeHistory.getTradeDate()).isEqualTo(DEFAULT_TRADE_DATE);
    }

    @Test
    @Transactional
    void createTradeHistoryWithExistingId() throws Exception {
        // Create the TradeHistory with an existing ID
        tradeHistory.setTradeId(1L);

        int databaseSizeBeforeCreate = tradeHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradeHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkScUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeHistoryRepository.findAll().size();
        // set the field null
        tradeHistory.setScUserId(null);

        // Create the TradeHistory, which fails.

        restTradeHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isBadRequest());

        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssetCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeHistoryRepository.findAll().size();
        // set the field null
        tradeHistory.setAssetCode(null);

        // Create the TradeHistory, which fails.

        restTradeHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isBadRequest());

        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssetTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeHistoryRepository.findAll().size();
        // set the field null
        tradeHistory.setAssetType(null);

        // Create the TradeHistory, which fails.

        restTradeHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isBadRequest());

        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTradePriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeHistoryRepository.findAll().size();
        // set the field null
        tradeHistory.setTradePrice(null);

        // Create the TradeHistory, which fails.

        restTradeHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isBadRequest());

        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTradeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeHistoryRepository.findAll().size();
        // set the field null
        tradeHistory.setTradeType(null);

        // Create the TradeHistory, which fails.

        restTradeHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isBadRequest());

        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTradeQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeHistoryRepository.findAll().size();
        // set the field null
        tradeHistory.setTradeQuantity(null);

        // Create the TradeHistory, which fails.

        restTradeHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isBadRequest());

        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTradeDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeHistoryRepository.findAll().size();
        // set the field null
        tradeHistory.setTradeDate(null);

        // Create the TradeHistory, which fails.

        restTradeHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isBadRequest());

        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTradeHistories() throws Exception {
        // Initialize the database
        tradeHistoryRepository.saveAndFlush(tradeHistory);

        // Get all the tradeHistoryList
        restTradeHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=tradeId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].tradeId").value(hasItem(tradeHistory.getTradeId().intValue())))
            .andExpect(jsonPath("$.[*].scUserId").value(hasItem(DEFAULT_SC_USER_ID)))
            .andExpect(jsonPath("$.[*].assetCode").value(hasItem(DEFAULT_ASSET_CODE)))
            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].tradePrice").value(hasItem(DEFAULT_TRADE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].tradeType").value(hasItem(DEFAULT_TRADE_TYPE)))
            .andExpect(jsonPath("$.[*].tradeQuantity").value(hasItem(DEFAULT_TRADE_QUANTITY)))
            .andExpect(jsonPath("$.[*].tradeDate").value(hasItem(DEFAULT_TRADE_DATE.toString())));
    }

    @Test
    @Transactional
    void getTradeHistory() throws Exception {
        // Initialize the database
        tradeHistoryRepository.saveAndFlush(tradeHistory);

        // Get the tradeHistory
        restTradeHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, tradeHistory.getTradeId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.tradeId").value(tradeHistory.getTradeId().intValue()))
            .andExpect(jsonPath("$.scUserId").value(DEFAULT_SC_USER_ID))
            .andExpect(jsonPath("$.assetCode").value(DEFAULT_ASSET_CODE))
            .andExpect(jsonPath("$.assetType").value(DEFAULT_ASSET_TYPE.toString()))
            .andExpect(jsonPath("$.tradePrice").value(DEFAULT_TRADE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.tradeType").value(DEFAULT_TRADE_TYPE))
            .andExpect(jsonPath("$.tradeQuantity").value(DEFAULT_TRADE_QUANTITY))
            .andExpect(jsonPath("$.tradeDate").value(DEFAULT_TRADE_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTradeHistory() throws Exception {
        // Get the tradeHistory
        restTradeHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTradeHistory() throws Exception {
        // Initialize the database
        tradeHistoryRepository.saveAndFlush(tradeHistory);

        int databaseSizeBeforeUpdate = tradeHistoryRepository.findAll().size();

        // Update the tradeHistory
        TradeHistory updatedTradeHistory = tradeHistoryRepository.findById(tradeHistory.getTradeId()).get();
        // Disconnect from session so that the updates on updatedTradeHistory are not directly saved in db
        em.detach(updatedTradeHistory);
        updatedTradeHistory
            .scUserId(UPDATED_SC_USER_ID)
            .assetCode(UPDATED_ASSET_CODE)
            .assetType(UPDATED_ASSET_TYPE)
            .tradePrice(UPDATED_TRADE_PRICE)
            .tradeType(UPDATED_TRADE_TYPE)
            .tradeQuantity(UPDATED_TRADE_QUANTITY)
            .tradeDate(UPDATED_TRADE_DATE);

        restTradeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTradeHistory.getTradeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTradeHistory))
            )
            .andExpect(status().isOk());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeUpdate);
        TradeHistory testTradeHistory = tradeHistoryList.get(tradeHistoryList.size() - 1);
        assertThat(testTradeHistory.getScUserId()).isEqualTo(UPDATED_SC_USER_ID);
        assertThat(testTradeHistory.getAssetCode()).isEqualTo(UPDATED_ASSET_CODE);
        assertThat(testTradeHistory.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testTradeHistory.getTradePrice()).isEqualTo(UPDATED_TRADE_PRICE);
        assertThat(testTradeHistory.getTradeType()).isEqualTo(UPDATED_TRADE_TYPE);
        assertThat(testTradeHistory.getTradeQuantity()).isEqualTo(UPDATED_TRADE_QUANTITY);
        assertThat(testTradeHistory.getTradeDate()).isEqualTo(UPDATED_TRADE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTradeHistory() throws Exception {
        int databaseSizeBeforeUpdate = tradeHistoryRepository.findAll().size();
        tradeHistory.setTradeId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tradeHistory.getTradeId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tradeHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTradeHistory() throws Exception {
        int databaseSizeBeforeUpdate = tradeHistoryRepository.findAll().size();
        tradeHistory.setTradeId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tradeHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTradeHistory() throws Exception {
        int databaseSizeBeforeUpdate = tradeHistoryRepository.findAll().size();
        tradeHistory.setTradeId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradeHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tradeHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTradeHistoryWithPatch() throws Exception {
        // Initialize the database
        tradeHistoryRepository.saveAndFlush(tradeHistory);

        int databaseSizeBeforeUpdate = tradeHistoryRepository.findAll().size();

        // Update the tradeHistory using partial update
        TradeHistory partialUpdatedTradeHistory = new TradeHistory();
        partialUpdatedTradeHistory.setTradeId(tradeHistory.getTradeId());

        partialUpdatedTradeHistory
            .scUserId(UPDATED_SC_USER_ID)
            .assetCode(UPDATED_ASSET_CODE)
            .tradePrice(UPDATED_TRADE_PRICE)
            .tradeQuantity(UPDATED_TRADE_QUANTITY);

        restTradeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTradeHistory.getTradeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTradeHistory))
            )
            .andExpect(status().isOk());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeUpdate);
        TradeHistory testTradeHistory = tradeHistoryList.get(tradeHistoryList.size() - 1);
        assertThat(testTradeHistory.getScUserId()).isEqualTo(UPDATED_SC_USER_ID);
        assertThat(testTradeHistory.getAssetCode()).isEqualTo(UPDATED_ASSET_CODE);
        assertThat(testTradeHistory.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
        assertThat(testTradeHistory.getTradePrice()).isEqualTo(UPDATED_TRADE_PRICE);
        assertThat(testTradeHistory.getTradeType()).isEqualTo(DEFAULT_TRADE_TYPE);
        assertThat(testTradeHistory.getTradeQuantity()).isEqualTo(UPDATED_TRADE_QUANTITY);
        assertThat(testTradeHistory.getTradeDate()).isEqualTo(DEFAULT_TRADE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTradeHistoryWithPatch() throws Exception {
        // Initialize the database
        tradeHistoryRepository.saveAndFlush(tradeHistory);

        int databaseSizeBeforeUpdate = tradeHistoryRepository.findAll().size();

        // Update the tradeHistory using partial update
        TradeHistory partialUpdatedTradeHistory = new TradeHistory();
        partialUpdatedTradeHistory.setTradeId(tradeHistory.getTradeId());

        partialUpdatedTradeHistory
            .scUserId(UPDATED_SC_USER_ID)
            .assetCode(UPDATED_ASSET_CODE)
            .assetType(UPDATED_ASSET_TYPE)
            .tradePrice(UPDATED_TRADE_PRICE)
            .tradeType(UPDATED_TRADE_TYPE)
            .tradeQuantity(UPDATED_TRADE_QUANTITY)
            .tradeDate(UPDATED_TRADE_DATE);

        restTradeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTradeHistory.getTradeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTradeHistory))
            )
            .andExpect(status().isOk());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeUpdate);
        TradeHistory testTradeHistory = tradeHistoryList.get(tradeHistoryList.size() - 1);
        assertThat(testTradeHistory.getScUserId()).isEqualTo(UPDATED_SC_USER_ID);
        assertThat(testTradeHistory.getAssetCode()).isEqualTo(UPDATED_ASSET_CODE);
        assertThat(testTradeHistory.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testTradeHistory.getTradePrice()).isEqualTo(UPDATED_TRADE_PRICE);
        assertThat(testTradeHistory.getTradeType()).isEqualTo(UPDATED_TRADE_TYPE);
        assertThat(testTradeHistory.getTradeQuantity()).isEqualTo(UPDATED_TRADE_QUANTITY);
        assertThat(testTradeHistory.getTradeDate()).isEqualTo(UPDATED_TRADE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTradeHistory() throws Exception {
        int databaseSizeBeforeUpdate = tradeHistoryRepository.findAll().size();
        tradeHistory.setTradeId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tradeHistory.getTradeId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tradeHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTradeHistory() throws Exception {
        int databaseSizeBeforeUpdate = tradeHistoryRepository.findAll().size();
        tradeHistory.setTradeId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tradeHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTradeHistory() throws Exception {
        int databaseSizeBeforeUpdate = tradeHistoryRepository.findAll().size();
        tradeHistory.setTradeId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tradeHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TradeHistory in the database
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTradeHistory() throws Exception {
        // Initialize the database
        tradeHistoryRepository.saveAndFlush(tradeHistory);

        int databaseSizeBeforeDelete = tradeHistoryRepository.findAll().size();

        // Delete the tradeHistory
        restTradeHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, tradeHistory.getTradeId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TradeHistory> tradeHistoryList = tradeHistoryRepository.findAll();
        assertThat(tradeHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
