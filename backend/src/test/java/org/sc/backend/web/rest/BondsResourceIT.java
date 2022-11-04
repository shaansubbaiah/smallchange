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
import org.sc.backend.domain.Bonds;
import org.sc.backend.repository.BondsRepository;
import org.sc.backend.service.criteria.BondsCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BondsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BondsResourceIT {

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

    private static final Integer DEFAULT_DURATION_MONTHS = 0;
    private static final Integer UPDATED_DURATION_MONTHS = 1;
    private static final Integer SMALLER_DURATION_MONTHS = 0 - 1;

    private static final String DEFAULT_BOND_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BOND_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bonds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{code}";

    @Autowired
    private BondsRepository bondsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBondsMockMvc;

    private Bonds bonds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonds createEntity(EntityManager em) {
        Bonds bonds = new Bonds()
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .interestRate(DEFAULT_INTEREST_RATE)
            .durationMonths(DEFAULT_DURATION_MONTHS)
            .bondType(DEFAULT_BOND_TYPE)
            .exchangeName(DEFAULT_EXCHANGE_NAME);
        return bonds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonds createUpdatedEntity(EntityManager em) {
        Bonds bonds = new Bonds()
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .interestRate(UPDATED_INTEREST_RATE)
            .durationMonths(UPDATED_DURATION_MONTHS)
            .bondType(UPDATED_BOND_TYPE)
            .exchangeName(UPDATED_EXCHANGE_NAME);
        return bonds;
    }

    @BeforeEach
    public void initTest() {
        bonds = createEntity(em);
    }

    @Test
    @Transactional
    void createBonds() throws Exception {
        int databaseSizeBeforeCreate = bondsRepository.findAll().size();
        // Create the Bonds
        restBondsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isCreated());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeCreate + 1);
        Bonds testBonds = bondsList.get(bondsList.size() - 1);
        assertThat(testBonds.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBonds.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBonds.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testBonds.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testBonds.getDurationMonths()).isEqualTo(DEFAULT_DURATION_MONTHS);
        assertThat(testBonds.getBondType()).isEqualTo(DEFAULT_BOND_TYPE);
        assertThat(testBonds.getExchangeName()).isEqualTo(DEFAULT_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void createBondsWithExistingId() throws Exception {
        // Create the Bonds with an existing ID
        bonds.setCode("existing_id");

        int databaseSizeBeforeCreate = bondsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBondsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondsRepository.findAll().size();
        // set the field null
        bonds.setName(null);

        // Create the Bonds, which fails.

        restBondsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isBadRequest());

        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondsRepository.findAll().size();
        // set the field null
        bonds.setQuantity(null);

        // Create the Bonds, which fails.

        restBondsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isBadRequest());

        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrentPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondsRepository.findAll().size();
        // set the field null
        bonds.setCurrentPrice(null);

        // Create the Bonds, which fails.

        restBondsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isBadRequest());

        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInterestRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondsRepository.findAll().size();
        // set the field null
        bonds.setInterestRate(null);

        // Create the Bonds, which fails.

        restBondsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isBadRequest());

        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDurationMonthsIsRequired() throws Exception {
        int databaseSizeBeforeTest = bondsRepository.findAll().size();
        // set the field null
        bonds.setDurationMonths(null);

        // Create the Bonds, which fails.

        restBondsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isBadRequest());

        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBonds() throws Exception {
        // Initialize the database
        bonds.setCode(UUID.randomUUID().toString());
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList
        restBondsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].code").value(hasItem(bonds.getCode())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].durationMonths").value(hasItem(DEFAULT_DURATION_MONTHS)))
            .andExpect(jsonPath("$.[*].bondType").value(hasItem(DEFAULT_BOND_TYPE)))
            .andExpect(jsonPath("$.[*].exchangeName").value(hasItem(DEFAULT_EXCHANGE_NAME)));
    }

    @Test
    @Transactional
    void getBonds() throws Exception {
        // Initialize the database
        bonds.setCode(UUID.randomUUID().toString());
        bondsRepository.saveAndFlush(bonds);

        // Get the bonds
        restBondsMockMvc
            .perform(get(ENTITY_API_URL_ID, bonds.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.code").value(bonds.getCode()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.durationMonths").value(DEFAULT_DURATION_MONTHS))
            .andExpect(jsonPath("$.bondType").value(DEFAULT_BOND_TYPE))
            .andExpect(jsonPath("$.exchangeName").value(DEFAULT_EXCHANGE_NAME));
    }

    @Test
    @Transactional
    void getBondsByIdFiltering() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        String id = bonds.getCode();

        defaultBondsShouldBeFound("code.equals=" + id);
        defaultBondsShouldNotBeFound("code.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllBondsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where name equals to DEFAULT_NAME
        defaultBondsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bondsList where name equals to UPDATED_NAME
        defaultBondsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBondsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBondsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bondsList where name equals to UPDATED_NAME
        defaultBondsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBondsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where name is not null
        defaultBondsShouldBeFound("name.specified=true");

        // Get all the bondsList where name is null
        defaultBondsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBondsByNameContainsSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where name contains DEFAULT_NAME
        defaultBondsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the bondsList where name contains UPDATED_NAME
        defaultBondsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBondsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where name does not contain DEFAULT_NAME
        defaultBondsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the bondsList where name does not contain UPDATED_NAME
        defaultBondsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBondsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where quantity equals to DEFAULT_QUANTITY
        defaultBondsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the bondsList where quantity equals to UPDATED_QUANTITY
        defaultBondsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBondsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultBondsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the bondsList where quantity equals to UPDATED_QUANTITY
        defaultBondsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBondsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where quantity is not null
        defaultBondsShouldBeFound("quantity.specified=true");

        // Get all the bondsList where quantity is null
        defaultBondsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllBondsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultBondsShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the bondsList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultBondsShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBondsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultBondsShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the bondsList where quantity is less than or equal to SMALLER_QUANTITY
        defaultBondsShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBondsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where quantity is less than DEFAULT_QUANTITY
        defaultBondsShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the bondsList where quantity is less than UPDATED_QUANTITY
        defaultBondsShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBondsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where quantity is greater than DEFAULT_QUANTITY
        defaultBondsShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the bondsList where quantity is greater than SMALLER_QUANTITY
        defaultBondsShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBondsByCurrentPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where currentPrice equals to DEFAULT_CURRENT_PRICE
        defaultBondsShouldBeFound("currentPrice.equals=" + DEFAULT_CURRENT_PRICE);

        // Get all the bondsList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultBondsShouldNotBeFound("currentPrice.equals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllBondsByCurrentPriceIsInShouldWork() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where currentPrice in DEFAULT_CURRENT_PRICE or UPDATED_CURRENT_PRICE
        defaultBondsShouldBeFound("currentPrice.in=" + DEFAULT_CURRENT_PRICE + "," + UPDATED_CURRENT_PRICE);

        // Get all the bondsList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultBondsShouldNotBeFound("currentPrice.in=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllBondsByCurrentPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where currentPrice is not null
        defaultBondsShouldBeFound("currentPrice.specified=true");

        // Get all the bondsList where currentPrice is null
        defaultBondsShouldNotBeFound("currentPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllBondsByCurrentPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where currentPrice is greater than or equal to DEFAULT_CURRENT_PRICE
        defaultBondsShouldBeFound("currentPrice.greaterThanOrEqual=" + DEFAULT_CURRENT_PRICE);

        // Get all the bondsList where currentPrice is greater than or equal to UPDATED_CURRENT_PRICE
        defaultBondsShouldNotBeFound("currentPrice.greaterThanOrEqual=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllBondsByCurrentPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where currentPrice is less than or equal to DEFAULT_CURRENT_PRICE
        defaultBondsShouldBeFound("currentPrice.lessThanOrEqual=" + DEFAULT_CURRENT_PRICE);

        // Get all the bondsList where currentPrice is less than or equal to SMALLER_CURRENT_PRICE
        defaultBondsShouldNotBeFound("currentPrice.lessThanOrEqual=" + SMALLER_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllBondsByCurrentPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where currentPrice is less than DEFAULT_CURRENT_PRICE
        defaultBondsShouldNotBeFound("currentPrice.lessThan=" + DEFAULT_CURRENT_PRICE);

        // Get all the bondsList where currentPrice is less than UPDATED_CURRENT_PRICE
        defaultBondsShouldBeFound("currentPrice.lessThan=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllBondsByCurrentPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where currentPrice is greater than DEFAULT_CURRENT_PRICE
        defaultBondsShouldNotBeFound("currentPrice.greaterThan=" + DEFAULT_CURRENT_PRICE);

        // Get all the bondsList where currentPrice is greater than SMALLER_CURRENT_PRICE
        defaultBondsShouldBeFound("currentPrice.greaterThan=" + SMALLER_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllBondsByInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where interestRate equals to DEFAULT_INTEREST_RATE
        defaultBondsShouldBeFound("interestRate.equals=" + DEFAULT_INTEREST_RATE);

        // Get all the bondsList where interestRate equals to UPDATED_INTEREST_RATE
        defaultBondsShouldNotBeFound("interestRate.equals=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllBondsByInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where interestRate in DEFAULT_INTEREST_RATE or UPDATED_INTEREST_RATE
        defaultBondsShouldBeFound("interestRate.in=" + DEFAULT_INTEREST_RATE + "," + UPDATED_INTEREST_RATE);

        // Get all the bondsList where interestRate equals to UPDATED_INTEREST_RATE
        defaultBondsShouldNotBeFound("interestRate.in=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllBondsByInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where interestRate is not null
        defaultBondsShouldBeFound("interestRate.specified=true");

        // Get all the bondsList where interestRate is null
        defaultBondsShouldNotBeFound("interestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllBondsByInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where interestRate is greater than or equal to DEFAULT_INTEREST_RATE
        defaultBondsShouldBeFound("interestRate.greaterThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the bondsList where interestRate is greater than or equal to UPDATED_INTEREST_RATE
        defaultBondsShouldNotBeFound("interestRate.greaterThanOrEqual=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllBondsByInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where interestRate is less than or equal to DEFAULT_INTEREST_RATE
        defaultBondsShouldBeFound("interestRate.lessThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the bondsList where interestRate is less than or equal to SMALLER_INTEREST_RATE
        defaultBondsShouldNotBeFound("interestRate.lessThanOrEqual=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllBondsByInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where interestRate is less than DEFAULT_INTEREST_RATE
        defaultBondsShouldNotBeFound("interestRate.lessThan=" + DEFAULT_INTEREST_RATE);

        // Get all the bondsList where interestRate is less than UPDATED_INTEREST_RATE
        defaultBondsShouldBeFound("interestRate.lessThan=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllBondsByInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where interestRate is greater than DEFAULT_INTEREST_RATE
        defaultBondsShouldNotBeFound("interestRate.greaterThan=" + DEFAULT_INTEREST_RATE);

        // Get all the bondsList where interestRate is greater than SMALLER_INTEREST_RATE
        defaultBondsShouldBeFound("interestRate.greaterThan=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllBondsByDurationMonthsIsEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where durationMonths equals to DEFAULT_DURATION_MONTHS
        defaultBondsShouldBeFound("durationMonths.equals=" + DEFAULT_DURATION_MONTHS);

        // Get all the bondsList where durationMonths equals to UPDATED_DURATION_MONTHS
        defaultBondsShouldNotBeFound("durationMonths.equals=" + UPDATED_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllBondsByDurationMonthsIsInShouldWork() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where durationMonths in DEFAULT_DURATION_MONTHS or UPDATED_DURATION_MONTHS
        defaultBondsShouldBeFound("durationMonths.in=" + DEFAULT_DURATION_MONTHS + "," + UPDATED_DURATION_MONTHS);

        // Get all the bondsList where durationMonths equals to UPDATED_DURATION_MONTHS
        defaultBondsShouldNotBeFound("durationMonths.in=" + UPDATED_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllBondsByDurationMonthsIsNullOrNotNull() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where durationMonths is not null
        defaultBondsShouldBeFound("durationMonths.specified=true");

        // Get all the bondsList where durationMonths is null
        defaultBondsShouldNotBeFound("durationMonths.specified=false");
    }

    @Test
    @Transactional
    void getAllBondsByDurationMonthsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where durationMonths is greater than or equal to DEFAULT_DURATION_MONTHS
        defaultBondsShouldBeFound("durationMonths.greaterThanOrEqual=" + DEFAULT_DURATION_MONTHS);

        // Get all the bondsList where durationMonths is greater than or equal to UPDATED_DURATION_MONTHS
        defaultBondsShouldNotBeFound("durationMonths.greaterThanOrEqual=" + UPDATED_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllBondsByDurationMonthsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where durationMonths is less than or equal to DEFAULT_DURATION_MONTHS
        defaultBondsShouldBeFound("durationMonths.lessThanOrEqual=" + DEFAULT_DURATION_MONTHS);

        // Get all the bondsList where durationMonths is less than or equal to SMALLER_DURATION_MONTHS
        defaultBondsShouldNotBeFound("durationMonths.lessThanOrEqual=" + SMALLER_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllBondsByDurationMonthsIsLessThanSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where durationMonths is less than DEFAULT_DURATION_MONTHS
        defaultBondsShouldNotBeFound("durationMonths.lessThan=" + DEFAULT_DURATION_MONTHS);

        // Get all the bondsList where durationMonths is less than UPDATED_DURATION_MONTHS
        defaultBondsShouldBeFound("durationMonths.lessThan=" + UPDATED_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllBondsByDurationMonthsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where durationMonths is greater than DEFAULT_DURATION_MONTHS
        defaultBondsShouldNotBeFound("durationMonths.greaterThan=" + DEFAULT_DURATION_MONTHS);

        // Get all the bondsList where durationMonths is greater than SMALLER_DURATION_MONTHS
        defaultBondsShouldBeFound("durationMonths.greaterThan=" + SMALLER_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllBondsByBondTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where bondType equals to DEFAULT_BOND_TYPE
        defaultBondsShouldBeFound("bondType.equals=" + DEFAULT_BOND_TYPE);

        // Get all the bondsList where bondType equals to UPDATED_BOND_TYPE
        defaultBondsShouldNotBeFound("bondType.equals=" + UPDATED_BOND_TYPE);
    }

    @Test
    @Transactional
    void getAllBondsByBondTypeIsInShouldWork() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where bondType in DEFAULT_BOND_TYPE or UPDATED_BOND_TYPE
        defaultBondsShouldBeFound("bondType.in=" + DEFAULT_BOND_TYPE + "," + UPDATED_BOND_TYPE);

        // Get all the bondsList where bondType equals to UPDATED_BOND_TYPE
        defaultBondsShouldNotBeFound("bondType.in=" + UPDATED_BOND_TYPE);
    }

    @Test
    @Transactional
    void getAllBondsByBondTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where bondType is not null
        defaultBondsShouldBeFound("bondType.specified=true");

        // Get all the bondsList where bondType is null
        defaultBondsShouldNotBeFound("bondType.specified=false");
    }

    @Test
    @Transactional
    void getAllBondsByBondTypeContainsSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where bondType contains DEFAULT_BOND_TYPE
        defaultBondsShouldBeFound("bondType.contains=" + DEFAULT_BOND_TYPE);

        // Get all the bondsList where bondType contains UPDATED_BOND_TYPE
        defaultBondsShouldNotBeFound("bondType.contains=" + UPDATED_BOND_TYPE);
    }

    @Test
    @Transactional
    void getAllBondsByBondTypeNotContainsSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where bondType does not contain DEFAULT_BOND_TYPE
        defaultBondsShouldNotBeFound("bondType.doesNotContain=" + DEFAULT_BOND_TYPE);

        // Get all the bondsList where bondType does not contain UPDATED_BOND_TYPE
        defaultBondsShouldBeFound("bondType.doesNotContain=" + UPDATED_BOND_TYPE);
    }

    @Test
    @Transactional
    void getAllBondsByExchangeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where exchangeName equals to DEFAULT_EXCHANGE_NAME
        defaultBondsShouldBeFound("exchangeName.equals=" + DEFAULT_EXCHANGE_NAME);

        // Get all the bondsList where exchangeName equals to UPDATED_EXCHANGE_NAME
        defaultBondsShouldNotBeFound("exchangeName.equals=" + UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void getAllBondsByExchangeNameIsInShouldWork() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where exchangeName in DEFAULT_EXCHANGE_NAME or UPDATED_EXCHANGE_NAME
        defaultBondsShouldBeFound("exchangeName.in=" + DEFAULT_EXCHANGE_NAME + "," + UPDATED_EXCHANGE_NAME);

        // Get all the bondsList where exchangeName equals to UPDATED_EXCHANGE_NAME
        defaultBondsShouldNotBeFound("exchangeName.in=" + UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void getAllBondsByExchangeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where exchangeName is not null
        defaultBondsShouldBeFound("exchangeName.specified=true");

        // Get all the bondsList where exchangeName is null
        defaultBondsShouldNotBeFound("exchangeName.specified=false");
    }

    @Test
    @Transactional
    void getAllBondsByExchangeNameContainsSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where exchangeName contains DEFAULT_EXCHANGE_NAME
        defaultBondsShouldBeFound("exchangeName.contains=" + DEFAULT_EXCHANGE_NAME);

        // Get all the bondsList where exchangeName contains UPDATED_EXCHANGE_NAME
        defaultBondsShouldNotBeFound("exchangeName.contains=" + UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void getAllBondsByExchangeNameNotContainsSomething() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList where exchangeName does not contain DEFAULT_EXCHANGE_NAME
        defaultBondsShouldNotBeFound("exchangeName.doesNotContain=" + DEFAULT_EXCHANGE_NAME);

        // Get all the bondsList where exchangeName does not contain UPDATED_EXCHANGE_NAME
        defaultBondsShouldBeFound("exchangeName.doesNotContain=" + UPDATED_EXCHANGE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBondsShouldBeFound(String filter) throws Exception {
        restBondsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].code").value(hasItem(bonds.getCode())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].durationMonths").value(hasItem(DEFAULT_DURATION_MONTHS)))
            .andExpect(jsonPath("$.[*].bondType").value(hasItem(DEFAULT_BOND_TYPE)))
            .andExpect(jsonPath("$.[*].exchangeName").value(hasItem(DEFAULT_EXCHANGE_NAME)));

        // Check, that the count call also returns 1
        restBondsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBondsShouldNotBeFound(String filter) throws Exception {
        restBondsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBondsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBonds() throws Exception {
        // Get the bonds
        restBondsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBonds() throws Exception {
        // Initialize the database
        bonds.setCode(UUID.randomUUID().toString());
        bondsRepository.saveAndFlush(bonds);

        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();

        // Update the bonds
        Bonds updatedBonds = bondsRepository.findById(bonds.getCode()).get();
        // Disconnect from session so that the updates on updatedBonds are not directly saved in db
        em.detach(updatedBonds);
        updatedBonds
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .interestRate(UPDATED_INTEREST_RATE)
            .durationMonths(UPDATED_DURATION_MONTHS)
            .bondType(UPDATED_BOND_TYPE)
            .exchangeName(UPDATED_EXCHANGE_NAME);

        restBondsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBonds.getCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBonds))
            )
            .andExpect(status().isOk());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
        Bonds testBonds = bondsList.get(bondsList.size() - 1);
        assertThat(testBonds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBonds.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBonds.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testBonds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testBonds.getDurationMonths()).isEqualTo(UPDATED_DURATION_MONTHS);
        assertThat(testBonds.getBondType()).isEqualTo(UPDATED_BOND_TYPE);
        assertThat(testBonds.getExchangeName()).isEqualTo(UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setCode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bonds.getCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonds))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonds))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBondsWithPatch() throws Exception {
        // Initialize the database
        bonds.setCode(UUID.randomUUID().toString());
        bondsRepository.saveAndFlush(bonds);

        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();

        // Update the bonds using partial update
        Bonds partialUpdatedBonds = new Bonds();
        partialUpdatedBonds.setCode(bonds.getCode());

        partialUpdatedBonds
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .durationMonths(UPDATED_DURATION_MONTHS)
            .exchangeName(UPDATED_EXCHANGE_NAME);

        restBondsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonds.getCode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonds))
            )
            .andExpect(status().isOk());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
        Bonds testBonds = bondsList.get(bondsList.size() - 1);
        assertThat(testBonds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBonds.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBonds.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testBonds.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testBonds.getDurationMonths()).isEqualTo(UPDATED_DURATION_MONTHS);
        assertThat(testBonds.getBondType()).isEqualTo(DEFAULT_BOND_TYPE);
        assertThat(testBonds.getExchangeName()).isEqualTo(UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBondsWithPatch() throws Exception {
        // Initialize the database
        bonds.setCode(UUID.randomUUID().toString());
        bondsRepository.saveAndFlush(bonds);

        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();

        // Update the bonds using partial update
        Bonds partialUpdatedBonds = new Bonds();
        partialUpdatedBonds.setCode(bonds.getCode());

        partialUpdatedBonds
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .interestRate(UPDATED_INTEREST_RATE)
            .durationMonths(UPDATED_DURATION_MONTHS)
            .bondType(UPDATED_BOND_TYPE)
            .exchangeName(UPDATED_EXCHANGE_NAME);

        restBondsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonds.getCode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonds))
            )
            .andExpect(status().isOk());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
        Bonds testBonds = bondsList.get(bondsList.size() - 1);
        assertThat(testBonds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBonds.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBonds.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testBonds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testBonds.getDurationMonths()).isEqualTo(UPDATED_DURATION_MONTHS);
        assertThat(testBonds.getBondType()).isEqualTo(UPDATED_BOND_TYPE);
        assertThat(testBonds.getExchangeName()).isEqualTo(UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setCode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bonds.getCode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonds))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonds))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBonds() throws Exception {
        // Initialize the database
        bonds.setCode(UUID.randomUUID().toString());
        bondsRepository.saveAndFlush(bonds);

        int databaseSizeBeforeDelete = bondsRepository.findAll().size();

        // Delete the bonds
        restBondsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bonds.getCode()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
