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
import org.sc.backend.domain.Positions;
import org.sc.backend.domain.enumeration.AssetType;
import org.sc.backend.repository.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PositionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PositionsResourceIT {

    private static final String DEFAULT_SC_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SC_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_CODE = "BBBBBBBBBB";

    private static final AssetType DEFAULT_ASSET_TYPE = AssetType.STOCK;
    private static final AssetType UPDATED_ASSET_TYPE = AssetType.MUTUALFUND;

    private static final Float DEFAULT_BUY_PRICE = 0F;
    private static final Float UPDATED_BUY_PRICE = 1F;

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final String ENTITY_API_URL = "/api/positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{positionId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PositionsRepository positionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPositionsMockMvc;

    private Positions positions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Positions createEntity(EntityManager em) {
        Positions positions = new Positions()
            .scUserId(DEFAULT_SC_USER_ID)
            .assetCode(DEFAULT_ASSET_CODE)
            .assetType(DEFAULT_ASSET_TYPE)
            .buyPrice(DEFAULT_BUY_PRICE)
            .quantity(DEFAULT_QUANTITY);
        return positions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Positions createUpdatedEntity(EntityManager em) {
        Positions positions = new Positions()
            .scUserId(UPDATED_SC_USER_ID)
            .assetCode(UPDATED_ASSET_CODE)
            .assetType(UPDATED_ASSET_TYPE)
            .buyPrice(UPDATED_BUY_PRICE)
            .quantity(UPDATED_QUANTITY);
        return positions;
    }

    @BeforeEach
    public void initTest() {
        positions = createEntity(em);
    }

    @Test
    @Transactional
    void createPositions() throws Exception {
        int databaseSizeBeforeCreate = positionsRepository.findAll().size();
        // Create the Positions
        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positions)))
            .andExpect(status().isCreated());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeCreate + 1);
        Positions testPositions = positionsList.get(positionsList.size() - 1);
        assertThat(testPositions.getScUserId()).isEqualTo(DEFAULT_SC_USER_ID);
        assertThat(testPositions.getAssetCode()).isEqualTo(DEFAULT_ASSET_CODE);
        assertThat(testPositions.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
        assertThat(testPositions.getBuyPrice()).isEqualTo(DEFAULT_BUY_PRICE);
        assertThat(testPositions.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void createPositionsWithExistingId() throws Exception {
        // Create the Positions with an existing ID
        positions.setPositionId(1L);

        int databaseSizeBeforeCreate = positionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positions)))
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkScUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionsRepository.findAll().size();
        // set the field null
        positions.setScUserId(null);

        // Create the Positions, which fails.

        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positions)))
            .andExpect(status().isBadRequest());

        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssetCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionsRepository.findAll().size();
        // set the field null
        positions.setAssetCode(null);

        // Create the Positions, which fails.

        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positions)))
            .andExpect(status().isBadRequest());

        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssetTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionsRepository.findAll().size();
        // set the field null
        positions.setAssetType(null);

        // Create the Positions, which fails.

        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positions)))
            .andExpect(status().isBadRequest());

        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBuyPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionsRepository.findAll().size();
        // set the field null
        positions.setBuyPrice(null);

        // Create the Positions, which fails.

        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positions)))
            .andExpect(status().isBadRequest());

        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionsRepository.findAll().size();
        // set the field null
        positions.setQuantity(null);

        // Create the Positions, which fails.

        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positions)))
            .andExpect(status().isBadRequest());

        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPositions() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get all the positionsList
        restPositionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=positionId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].positionId").value(hasItem(positions.getPositionId().intValue())))
            .andExpect(jsonPath("$.[*].scUserId").value(hasItem(DEFAULT_SC_USER_ID)))
            .andExpect(jsonPath("$.[*].assetCode").value(hasItem(DEFAULT_ASSET_CODE)))
            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].buyPrice").value(hasItem(DEFAULT_BUY_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    void getPositions() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get the positions
        restPositionsMockMvc
            .perform(get(ENTITY_API_URL_ID, positions.getPositionId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.positionId").value(positions.getPositionId().intValue()))
            .andExpect(jsonPath("$.scUserId").value(DEFAULT_SC_USER_ID))
            .andExpect(jsonPath("$.assetCode").value(DEFAULT_ASSET_CODE))
            .andExpect(jsonPath("$.assetType").value(DEFAULT_ASSET_TYPE.toString()))
            .andExpect(jsonPath("$.buyPrice").value(DEFAULT_BUY_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    void getNonExistingPositions() throws Exception {
        // Get the positions
        restPositionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPositions() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();

        // Update the positions
        Positions updatedPositions = positionsRepository.findById(positions.getPositionId()).get();
        // Disconnect from session so that the updates on updatedPositions are not directly saved in db
        em.detach(updatedPositions);
        updatedPositions
            .scUserId(UPDATED_SC_USER_ID)
            .assetCode(UPDATED_ASSET_CODE)
            .assetType(UPDATED_ASSET_TYPE)
            .buyPrice(UPDATED_BUY_PRICE)
            .quantity(UPDATED_QUANTITY);

        restPositionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPositions.getPositionId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPositions))
            )
            .andExpect(status().isOk());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
        Positions testPositions = positionsList.get(positionsList.size() - 1);
        assertThat(testPositions.getScUserId()).isEqualTo(UPDATED_SC_USER_ID);
        assertThat(testPositions.getAssetCode()).isEqualTo(UPDATED_ASSET_CODE);
        assertThat(testPositions.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testPositions.getBuyPrice()).isEqualTo(UPDATED_BUY_PRICE);
        assertThat(testPositions.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setPositionId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, positions.getPositionId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(positions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setPositionId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(positions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setPositionId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePositionsWithPatch() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();

        // Update the positions using partial update
        Positions partialUpdatedPositions = new Positions();
        partialUpdatedPositions.setPositionId(positions.getPositionId());

        partialUpdatedPositions.assetCode(UPDATED_ASSET_CODE).buyPrice(UPDATED_BUY_PRICE).quantity(UPDATED_QUANTITY);

        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPositions.getPositionId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPositions))
            )
            .andExpect(status().isOk());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
        Positions testPositions = positionsList.get(positionsList.size() - 1);
        assertThat(testPositions.getScUserId()).isEqualTo(DEFAULT_SC_USER_ID);
        assertThat(testPositions.getAssetCode()).isEqualTo(UPDATED_ASSET_CODE);
        assertThat(testPositions.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
        assertThat(testPositions.getBuyPrice()).isEqualTo(UPDATED_BUY_PRICE);
        assertThat(testPositions.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdatePositionsWithPatch() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();

        // Update the positions using partial update
        Positions partialUpdatedPositions = new Positions();
        partialUpdatedPositions.setPositionId(positions.getPositionId());

        partialUpdatedPositions
            .scUserId(UPDATED_SC_USER_ID)
            .assetCode(UPDATED_ASSET_CODE)
            .assetType(UPDATED_ASSET_TYPE)
            .buyPrice(UPDATED_BUY_PRICE)
            .quantity(UPDATED_QUANTITY);

        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPositions.getPositionId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPositions))
            )
            .andExpect(status().isOk());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
        Positions testPositions = positionsList.get(positionsList.size() - 1);
        assertThat(testPositions.getScUserId()).isEqualTo(UPDATED_SC_USER_ID);
        assertThat(testPositions.getAssetCode()).isEqualTo(UPDATED_ASSET_CODE);
        assertThat(testPositions.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testPositions.getBuyPrice()).isEqualTo(UPDATED_BUY_PRICE);
        assertThat(testPositions.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setPositionId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, positions.getPositionId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(positions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setPositionId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(positions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setPositionId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(positions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePositions() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        int databaseSizeBeforeDelete = positionsRepository.findAll().size();

        // Delete the positions
        restPositionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, positions.getPositionId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
