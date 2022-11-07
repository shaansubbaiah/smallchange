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
import org.sc.backend.domain.MutualFunds;
import org.sc.backend.repository.MutualFundsRepository;
import org.sc.backend.web.rest.admin.MutualFundsResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MutualFundsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MutualFundsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;
    private static final Integer SMALLER_QUANTITY = 0 - 1;

    private static final Float DEFAULT_CURRENT_PRICE = 0F;
    private static final Float UPDATED_CURRENT_PRICE = 1F;
    private static final Float SMALLER_CURRENT_PRICE = 0F - 1F;

    private static final Float DEFAULT_INTEREST_RATE = 0F;
    private static final Float UPDATED_INTEREST_RATE = 1F;
    private static final Float SMALLER_INTEREST_RATE = 0F - 1F;

    private static final String DEFAULT_MF_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MF_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mutual-funds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{code}";

    @Autowired
    private MutualFundsRepository mutualFundsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMutualFundsMockMvc;

    private MutualFunds mutualFunds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MutualFunds createEntity(EntityManager em) {
        MutualFunds mutualFunds = new MutualFunds()
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .interestRate(DEFAULT_INTEREST_RATE)
            .mfType(DEFAULT_MF_TYPE);
        return mutualFunds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MutualFunds createUpdatedEntity(EntityManager em) {
        MutualFunds mutualFunds = new MutualFunds()
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .interestRate(UPDATED_INTEREST_RATE)
            .mfType(UPDATED_MF_TYPE);
        return mutualFunds;
    }

    @BeforeEach
    public void initTest() {
        mutualFunds = createEntity(em);
    }

    @Test
    @Transactional
    void createMutualFunds() throws Exception {
        int databaseSizeBeforeCreate = mutualFundsRepository.findAll().size();
        // Create the MutualFunds
        restMutualFundsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mutualFunds)))
            .andExpect(status().isCreated());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeCreate + 1);
        MutualFunds testMutualFunds = mutualFundsList.get(mutualFundsList.size() - 1);
        assertThat(testMutualFunds.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMutualFunds.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMutualFunds.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testMutualFunds.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testMutualFunds.getMfType()).isEqualTo(DEFAULT_MF_TYPE);
    }

    @Test
    @Transactional
    void createMutualFundsWithExistingId() throws Exception {
        // Create the MutualFunds with an existing ID
        mutualFunds.setCode("existing_id");

        int databaseSizeBeforeCreate = mutualFundsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMutualFundsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mutualFunds)))
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mutualFundsRepository.findAll().size();
        // set the field null
        mutualFunds.setName(null);

        // Create the MutualFunds, which fails.

        restMutualFundsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mutualFunds)))
            .andExpect(status().isBadRequest());

        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = mutualFundsRepository.findAll().size();
        // set the field null
        mutualFunds.setQuantity(null);

        // Create the MutualFunds, which fails.

        restMutualFundsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mutualFunds)))
            .andExpect(status().isBadRequest());

        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrentPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mutualFundsRepository.findAll().size();
        // set the field null
        mutualFunds.setCurrentPrice(null);

        // Create the MutualFunds, which fails.

        restMutualFundsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mutualFunds)))
            .andExpect(status().isBadRequest());

        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMutualFunds() throws Exception {
        // Initialize the database
        mutualFunds.setCode(UUID.randomUUID().toString());
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList
        restMutualFundsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].code").value(hasItem(mutualFunds.getCode())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].mfType").value(hasItem(DEFAULT_MF_TYPE)));
    }

    @Test
    @Transactional
    void getMutualFunds() throws Exception {
        // Initialize the database
        mutualFunds.setCode(UUID.randomUUID().toString());
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get the mutualFunds
        restMutualFundsMockMvc
            .perform(get(ENTITY_API_URL_ID, mutualFunds.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.code").value(mutualFunds.getCode()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.mfType").value(DEFAULT_MF_TYPE));
    }

    @Test
    @Transactional
    void getMutualFundsByIdFiltering() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        String id = mutualFunds.getCode();

        defaultMutualFundsShouldBeFound("code.equals=" + id);
        defaultMutualFundsShouldNotBeFound("code.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllMutualFundsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where name equals to DEFAULT_NAME
        defaultMutualFundsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mutualFundsList where name equals to UPDATED_NAME
        defaultMutualFundsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMutualFundsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMutualFundsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mutualFundsList where name equals to UPDATED_NAME
        defaultMutualFundsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMutualFundsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where name is not null
        defaultMutualFundsShouldBeFound("name.specified=true");

        // Get all the mutualFundsList where name is null
        defaultMutualFundsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllMutualFundsByNameContainsSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where name contains DEFAULT_NAME
        defaultMutualFundsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mutualFundsList where name contains UPDATED_NAME
        defaultMutualFundsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMutualFundsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where name does not contain DEFAULT_NAME
        defaultMutualFundsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mutualFundsList where name does not contain UPDATED_NAME
        defaultMutualFundsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMutualFundsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where quantity equals to DEFAULT_QUANTITY
        defaultMutualFundsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the mutualFundsList where quantity equals to UPDATED_QUANTITY
        defaultMutualFundsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllMutualFundsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultMutualFundsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the mutualFundsList where quantity equals to UPDATED_QUANTITY
        defaultMutualFundsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllMutualFundsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where quantity is not null
        defaultMutualFundsShouldBeFound("quantity.specified=true");

        // Get all the mutualFundsList where quantity is null
        defaultMutualFundsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllMutualFundsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultMutualFundsShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mutualFundsList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultMutualFundsShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllMutualFundsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultMutualFundsShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the mutualFundsList where quantity is less than or equal to SMALLER_QUANTITY
        defaultMutualFundsShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllMutualFundsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where quantity is less than DEFAULT_QUANTITY
        defaultMutualFundsShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the mutualFundsList where quantity is less than UPDATED_QUANTITY
        defaultMutualFundsShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllMutualFundsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where quantity is greater than DEFAULT_QUANTITY
        defaultMutualFundsShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the mutualFundsList where quantity is greater than SMALLER_QUANTITY
        defaultMutualFundsShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllMutualFundsByCurrentPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where currentPrice equals to DEFAULT_CURRENT_PRICE
        defaultMutualFundsShouldBeFound("currentPrice.equals=" + DEFAULT_CURRENT_PRICE);

        // Get all the mutualFundsList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultMutualFundsShouldNotBeFound("currentPrice.equals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByCurrentPriceIsInShouldWork() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where currentPrice in DEFAULT_CURRENT_PRICE or UPDATED_CURRENT_PRICE
        defaultMutualFundsShouldBeFound("currentPrice.in=" + DEFAULT_CURRENT_PRICE + "," + UPDATED_CURRENT_PRICE);

        // Get all the mutualFundsList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultMutualFundsShouldNotBeFound("currentPrice.in=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByCurrentPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where currentPrice is not null
        defaultMutualFundsShouldBeFound("currentPrice.specified=true");

        // Get all the mutualFundsList where currentPrice is null
        defaultMutualFundsShouldNotBeFound("currentPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllMutualFundsByCurrentPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where currentPrice is greater than or equal to DEFAULT_CURRENT_PRICE
        defaultMutualFundsShouldBeFound("currentPrice.greaterThanOrEqual=" + DEFAULT_CURRENT_PRICE);

        // Get all the mutualFundsList where currentPrice is greater than or equal to UPDATED_CURRENT_PRICE
        defaultMutualFundsShouldNotBeFound("currentPrice.greaterThanOrEqual=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByCurrentPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where currentPrice is less than or equal to DEFAULT_CURRENT_PRICE
        defaultMutualFundsShouldBeFound("currentPrice.lessThanOrEqual=" + DEFAULT_CURRENT_PRICE);

        // Get all the mutualFundsList where currentPrice is less than or equal to SMALLER_CURRENT_PRICE
        defaultMutualFundsShouldNotBeFound("currentPrice.lessThanOrEqual=" + SMALLER_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByCurrentPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where currentPrice is less than DEFAULT_CURRENT_PRICE
        defaultMutualFundsShouldNotBeFound("currentPrice.lessThan=" + DEFAULT_CURRENT_PRICE);

        // Get all the mutualFundsList where currentPrice is less than UPDATED_CURRENT_PRICE
        defaultMutualFundsShouldBeFound("currentPrice.lessThan=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByCurrentPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where currentPrice is greater than DEFAULT_CURRENT_PRICE
        defaultMutualFundsShouldNotBeFound("currentPrice.greaterThan=" + DEFAULT_CURRENT_PRICE);

        // Get all the mutualFundsList where currentPrice is greater than SMALLER_CURRENT_PRICE
        defaultMutualFundsShouldBeFound("currentPrice.greaterThan=" + SMALLER_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where interestRate equals to DEFAULT_INTEREST_RATE
        defaultMutualFundsShouldBeFound("interestRate.equals=" + DEFAULT_INTEREST_RATE);

        // Get all the mutualFundsList where interestRate equals to UPDATED_INTEREST_RATE
        defaultMutualFundsShouldNotBeFound("interestRate.equals=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where interestRate in DEFAULT_INTEREST_RATE or UPDATED_INTEREST_RATE
        defaultMutualFundsShouldBeFound("interestRate.in=" + DEFAULT_INTEREST_RATE + "," + UPDATED_INTEREST_RATE);

        // Get all the mutualFundsList where interestRate equals to UPDATED_INTEREST_RATE
        defaultMutualFundsShouldNotBeFound("interestRate.in=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where interestRate is not null
        defaultMutualFundsShouldBeFound("interestRate.specified=true");

        // Get all the mutualFundsList where interestRate is null
        defaultMutualFundsShouldNotBeFound("interestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllMutualFundsByInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where interestRate is greater than or equal to DEFAULT_INTEREST_RATE
        defaultMutualFundsShouldBeFound("interestRate.greaterThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the mutualFundsList where interestRate is greater than or equal to UPDATED_INTEREST_RATE
        defaultMutualFundsShouldNotBeFound("interestRate.greaterThanOrEqual=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where interestRate is less than or equal to DEFAULT_INTEREST_RATE
        defaultMutualFundsShouldBeFound("interestRate.lessThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the mutualFundsList where interestRate is less than or equal to SMALLER_INTEREST_RATE
        defaultMutualFundsShouldNotBeFound("interestRate.lessThanOrEqual=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where interestRate is less than DEFAULT_INTEREST_RATE
        defaultMutualFundsShouldNotBeFound("interestRate.lessThan=" + DEFAULT_INTEREST_RATE);

        // Get all the mutualFundsList where interestRate is less than UPDATED_INTEREST_RATE
        defaultMutualFundsShouldBeFound("interestRate.lessThan=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where interestRate is greater than DEFAULT_INTEREST_RATE
        defaultMutualFundsShouldNotBeFound("interestRate.greaterThan=" + DEFAULT_INTEREST_RATE);

        // Get all the mutualFundsList where interestRate is greater than SMALLER_INTEREST_RATE
        defaultMutualFundsShouldBeFound("interestRate.greaterThan=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByMfTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where mfType equals to DEFAULT_MF_TYPE
        defaultMutualFundsShouldBeFound("mfType.equals=" + DEFAULT_MF_TYPE);

        // Get all the mutualFundsList where mfType equals to UPDATED_MF_TYPE
        defaultMutualFundsShouldNotBeFound("mfType.equals=" + UPDATED_MF_TYPE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByMfTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where mfType in DEFAULT_MF_TYPE or UPDATED_MF_TYPE
        defaultMutualFundsShouldBeFound("mfType.in=" + DEFAULT_MF_TYPE + "," + UPDATED_MF_TYPE);

        // Get all the mutualFundsList where mfType equals to UPDATED_MF_TYPE
        defaultMutualFundsShouldNotBeFound("mfType.in=" + UPDATED_MF_TYPE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByMfTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where mfType is not null
        defaultMutualFundsShouldBeFound("mfType.specified=true");

        // Get all the mutualFundsList where mfType is null
        defaultMutualFundsShouldNotBeFound("mfType.specified=false");
    }

    @Test
    @Transactional
    void getAllMutualFundsByMfTypeContainsSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where mfType contains DEFAULT_MF_TYPE
        defaultMutualFundsShouldBeFound("mfType.contains=" + DEFAULT_MF_TYPE);

        // Get all the mutualFundsList where mfType contains UPDATED_MF_TYPE
        defaultMutualFundsShouldNotBeFound("mfType.contains=" + UPDATED_MF_TYPE);
    }

    @Test
    @Transactional
    void getAllMutualFundsByMfTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList where mfType does not contain DEFAULT_MF_TYPE
        defaultMutualFundsShouldNotBeFound("mfType.doesNotContain=" + DEFAULT_MF_TYPE);

        // Get all the mutualFundsList where mfType does not contain UPDATED_MF_TYPE
        defaultMutualFundsShouldBeFound("mfType.doesNotContain=" + UPDATED_MF_TYPE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMutualFundsShouldBeFound(String filter) throws Exception {
        restMutualFundsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].code").value(hasItem(mutualFunds.getCode())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].mfType").value(hasItem(DEFAULT_MF_TYPE)));

        // Check, that the count call also returns 1
        restMutualFundsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMutualFundsShouldNotBeFound(String filter) throws Exception {
        restMutualFundsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMutualFundsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMutualFunds() throws Exception {
        // Get the mutualFunds
        restMutualFundsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMutualFunds() throws Exception {
        // Initialize the database
        mutualFunds.setCode(UUID.randomUUID().toString());
        mutualFundsRepository.saveAndFlush(mutualFunds);

        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();

        // Update the mutualFunds
        MutualFunds updatedMutualFunds = mutualFundsRepository.findById(mutualFunds.getCode()).get();
        // Disconnect from session so that the updates on updatedMutualFunds are not directly saved in db
        em.detach(updatedMutualFunds);
        updatedMutualFunds
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .interestRate(UPDATED_INTEREST_RATE)
            .mfType(UPDATED_MF_TYPE);

        restMutualFundsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMutualFunds.getCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMutualFunds))
            )
            .andExpect(status().isOk());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
        MutualFunds testMutualFunds = mutualFundsList.get(mutualFundsList.size() - 1);
        assertThat(testMutualFunds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMutualFunds.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMutualFunds.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testMutualFunds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMutualFunds.getMfType()).isEqualTo(UPDATED_MF_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setCode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mutualFunds.getCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mutualFunds)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMutualFundsWithPatch() throws Exception {
        // Initialize the database
        mutualFunds.setCode(UUID.randomUUID().toString());
        mutualFundsRepository.saveAndFlush(mutualFunds);

        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();

        // Update the mutualFunds using partial update
        MutualFunds partialUpdatedMutualFunds = new MutualFunds();
        partialUpdatedMutualFunds.setCode(mutualFunds.getCode());

        partialUpdatedMutualFunds.interestRate(UPDATED_INTEREST_RATE).mfType(UPDATED_MF_TYPE);

        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMutualFunds.getCode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMutualFunds))
            )
            .andExpect(status().isOk());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
        MutualFunds testMutualFunds = mutualFundsList.get(mutualFundsList.size() - 1);
        assertThat(testMutualFunds.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMutualFunds.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMutualFunds.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testMutualFunds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMutualFunds.getMfType()).isEqualTo(UPDATED_MF_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateMutualFundsWithPatch() throws Exception {
        // Initialize the database
        mutualFunds.setCode(UUID.randomUUID().toString());
        mutualFundsRepository.saveAndFlush(mutualFunds);

        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();

        // Update the mutualFunds using partial update
        MutualFunds partialUpdatedMutualFunds = new MutualFunds();
        partialUpdatedMutualFunds.setCode(mutualFunds.getCode());

        partialUpdatedMutualFunds
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .interestRate(UPDATED_INTEREST_RATE)
            .mfType(UPDATED_MF_TYPE);

        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMutualFunds.getCode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMutualFunds))
            )
            .andExpect(status().isOk());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
        MutualFunds testMutualFunds = mutualFundsList.get(mutualFundsList.size() - 1);
        assertThat(testMutualFunds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMutualFunds.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMutualFunds.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testMutualFunds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMutualFunds.getMfType()).isEqualTo(UPDATED_MF_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setCode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mutualFunds.getCode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMutualFunds() throws Exception {
        // Initialize the database
        mutualFunds.setCode(UUID.randomUUID().toString());
        mutualFundsRepository.saveAndFlush(mutualFunds);

        int databaseSizeBeforeDelete = mutualFundsRepository.findAll().size();

        // Delete the mutualFunds
        restMutualFundsMockMvc
            .perform(delete(ENTITY_API_URL_ID, mutualFunds.getCode()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
