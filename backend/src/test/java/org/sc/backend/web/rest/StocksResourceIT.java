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
import org.sc.backend.domain.Stocks;
import org.sc.backend.repository.StocksRepository;
import org.sc.backend.service.criteria.StocksCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StocksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StocksResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;
    private static final Integer SMALLER_QUANTITY = 0 - 1;

    private static final Float DEFAULT_CURRENT_PRICE = 0F;
    private static final Float UPDATED_CURRENT_PRICE = 1F;
    private static final Float SMALLER_CURRENT_PRICE = 0F - 1F;

    private static final String DEFAULT_STOCK_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{code}";

    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStocksMockMvc;

    private Stocks stocks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stocks createEntity(EntityManager em) {
        Stocks stocks = new Stocks()
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .stockType(DEFAULT_STOCK_TYPE)
            .exchangeName(DEFAULT_EXCHANGE_NAME);
        return stocks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stocks createUpdatedEntity(EntityManager em) {
        Stocks stocks = new Stocks()
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .stockType(UPDATED_STOCK_TYPE)
            .exchangeName(UPDATED_EXCHANGE_NAME);
        return stocks;
    }

    @BeforeEach
    public void initTest() {
        stocks = createEntity(em);
    }

    @Test
    @Transactional
    void createStocks() throws Exception {
        int databaseSizeBeforeCreate = stocksRepository.findAll().size();
        // Create the Stocks
        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isCreated());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeCreate + 1);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStocks.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStocks.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testStocks.getStockType()).isEqualTo(DEFAULT_STOCK_TYPE);
        assertThat(testStocks.getExchangeName()).isEqualTo(DEFAULT_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void createStocksWithExistingId() throws Exception {
        // Create the Stocks with an existing ID
        stocks.setCode("existing_id");

        int databaseSizeBeforeCreate = stocksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stocksRepository.findAll().size();
        // set the field null
        stocks.setName(null);

        // Create the Stocks, which fails.

        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stocksRepository.findAll().size();
        // set the field null
        stocks.setQuantity(null);

        // Create the Stocks, which fails.

        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrentPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stocksRepository.findAll().size();
        // set the field null
        stocks.setCurrentPrice(null);

        // Create the Stocks, which fails.

        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStocks() throws Exception {
        // Initialize the database
        stocks.setCode(UUID.randomUUID().toString());
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList
        restStocksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].code").value(hasItem(stocks.getCode())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockType").value(hasItem(DEFAULT_STOCK_TYPE)))
            .andExpect(jsonPath("$.[*].exchangeName").value(hasItem(DEFAULT_EXCHANGE_NAME)));
    }

    @Test
    @Transactional
    void getStocks() throws Exception {
        // Initialize the database
        stocks.setCode(UUID.randomUUID().toString());
        stocksRepository.saveAndFlush(stocks);

        // Get the stocks
        restStocksMockMvc
            .perform(get(ENTITY_API_URL_ID, stocks.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.code").value(stocks.getCode()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.stockType").value(DEFAULT_STOCK_TYPE))
            .andExpect(jsonPath("$.exchangeName").value(DEFAULT_EXCHANGE_NAME));
    }

    @Test
    @Transactional
    void getStocksByIdFiltering() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        String id = stocks.getCode();

        defaultStocksShouldBeFound("code.equals=" + id);
        defaultStocksShouldNotBeFound("code.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllStocksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where name equals to DEFAULT_NAME
        defaultStocksShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stocksList where name equals to UPDATED_NAME
        defaultStocksShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStocksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStocksShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stocksList where name equals to UPDATED_NAME
        defaultStocksShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStocksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where name is not null
        defaultStocksShouldBeFound("name.specified=true");

        // Get all the stocksList where name is null
        defaultStocksShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllStocksByNameContainsSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where name contains DEFAULT_NAME
        defaultStocksShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stocksList where name contains UPDATED_NAME
        defaultStocksShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStocksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where name does not contain DEFAULT_NAME
        defaultStocksShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stocksList where name does not contain UPDATED_NAME
        defaultStocksShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStocksByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where quantity equals to DEFAULT_QUANTITY
        defaultStocksShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the stocksList where quantity equals to UPDATED_QUANTITY
        defaultStocksShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllStocksByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultStocksShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the stocksList where quantity equals to UPDATED_QUANTITY
        defaultStocksShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllStocksByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where quantity is not null
        defaultStocksShouldBeFound("quantity.specified=true");

        // Get all the stocksList where quantity is null
        defaultStocksShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllStocksByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultStocksShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the stocksList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultStocksShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllStocksByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultStocksShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the stocksList where quantity is less than or equal to SMALLER_QUANTITY
        defaultStocksShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllStocksByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where quantity is less than DEFAULT_QUANTITY
        defaultStocksShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the stocksList where quantity is less than UPDATED_QUANTITY
        defaultStocksShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllStocksByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where quantity is greater than DEFAULT_QUANTITY
        defaultStocksShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the stocksList where quantity is greater than SMALLER_QUANTITY
        defaultStocksShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllStocksByCurrentPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where currentPrice equals to DEFAULT_CURRENT_PRICE
        defaultStocksShouldBeFound("currentPrice.equals=" + DEFAULT_CURRENT_PRICE);

        // Get all the stocksList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultStocksShouldNotBeFound("currentPrice.equals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllStocksByCurrentPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where currentPrice in DEFAULT_CURRENT_PRICE or UPDATED_CURRENT_PRICE
        defaultStocksShouldBeFound("currentPrice.in=" + DEFAULT_CURRENT_PRICE + "," + UPDATED_CURRENT_PRICE);

        // Get all the stocksList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultStocksShouldNotBeFound("currentPrice.in=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllStocksByCurrentPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where currentPrice is not null
        defaultStocksShouldBeFound("currentPrice.specified=true");

        // Get all the stocksList where currentPrice is null
        defaultStocksShouldNotBeFound("currentPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllStocksByCurrentPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where currentPrice is greater than or equal to DEFAULT_CURRENT_PRICE
        defaultStocksShouldBeFound("currentPrice.greaterThanOrEqual=" + DEFAULT_CURRENT_PRICE);

        // Get all the stocksList where currentPrice is greater than or equal to UPDATED_CURRENT_PRICE
        defaultStocksShouldNotBeFound("currentPrice.greaterThanOrEqual=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllStocksByCurrentPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where currentPrice is less than or equal to DEFAULT_CURRENT_PRICE
        defaultStocksShouldBeFound("currentPrice.lessThanOrEqual=" + DEFAULT_CURRENT_PRICE);

        // Get all the stocksList where currentPrice is less than or equal to SMALLER_CURRENT_PRICE
        defaultStocksShouldNotBeFound("currentPrice.lessThanOrEqual=" + SMALLER_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllStocksByCurrentPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where currentPrice is less than DEFAULT_CURRENT_PRICE
        defaultStocksShouldNotBeFound("currentPrice.lessThan=" + DEFAULT_CURRENT_PRICE);

        // Get all the stocksList where currentPrice is less than UPDATED_CURRENT_PRICE
        defaultStocksShouldBeFound("currentPrice.lessThan=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllStocksByCurrentPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where currentPrice is greater than DEFAULT_CURRENT_PRICE
        defaultStocksShouldNotBeFound("currentPrice.greaterThan=" + DEFAULT_CURRENT_PRICE);

        // Get all the stocksList where currentPrice is greater than SMALLER_CURRENT_PRICE
        defaultStocksShouldBeFound("currentPrice.greaterThan=" + SMALLER_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllStocksByStockTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where stockType equals to DEFAULT_STOCK_TYPE
        defaultStocksShouldBeFound("stockType.equals=" + DEFAULT_STOCK_TYPE);

        // Get all the stocksList where stockType equals to UPDATED_STOCK_TYPE
        defaultStocksShouldNotBeFound("stockType.equals=" + UPDATED_STOCK_TYPE);
    }

    @Test
    @Transactional
    void getAllStocksByStockTypeIsInShouldWork() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where stockType in DEFAULT_STOCK_TYPE or UPDATED_STOCK_TYPE
        defaultStocksShouldBeFound("stockType.in=" + DEFAULT_STOCK_TYPE + "," + UPDATED_STOCK_TYPE);

        // Get all the stocksList where stockType equals to UPDATED_STOCK_TYPE
        defaultStocksShouldNotBeFound("stockType.in=" + UPDATED_STOCK_TYPE);
    }

    @Test
    @Transactional
    void getAllStocksByStockTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where stockType is not null
        defaultStocksShouldBeFound("stockType.specified=true");

        // Get all the stocksList where stockType is null
        defaultStocksShouldNotBeFound("stockType.specified=false");
    }

    @Test
    @Transactional
    void getAllStocksByStockTypeContainsSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where stockType contains DEFAULT_STOCK_TYPE
        defaultStocksShouldBeFound("stockType.contains=" + DEFAULT_STOCK_TYPE);

        // Get all the stocksList where stockType contains UPDATED_STOCK_TYPE
        defaultStocksShouldNotBeFound("stockType.contains=" + UPDATED_STOCK_TYPE);
    }

    @Test
    @Transactional
    void getAllStocksByStockTypeNotContainsSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where stockType does not contain DEFAULT_STOCK_TYPE
        defaultStocksShouldNotBeFound("stockType.doesNotContain=" + DEFAULT_STOCK_TYPE);

        // Get all the stocksList where stockType does not contain UPDATED_STOCK_TYPE
        defaultStocksShouldBeFound("stockType.doesNotContain=" + UPDATED_STOCK_TYPE);
    }

    @Test
    @Transactional
    void getAllStocksByExchangeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where exchangeName equals to DEFAULT_EXCHANGE_NAME
        defaultStocksShouldBeFound("exchangeName.equals=" + DEFAULT_EXCHANGE_NAME);

        // Get all the stocksList where exchangeName equals to UPDATED_EXCHANGE_NAME
        defaultStocksShouldNotBeFound("exchangeName.equals=" + UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void getAllStocksByExchangeNameIsInShouldWork() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where exchangeName in DEFAULT_EXCHANGE_NAME or UPDATED_EXCHANGE_NAME
        defaultStocksShouldBeFound("exchangeName.in=" + DEFAULT_EXCHANGE_NAME + "," + UPDATED_EXCHANGE_NAME);

        // Get all the stocksList where exchangeName equals to UPDATED_EXCHANGE_NAME
        defaultStocksShouldNotBeFound("exchangeName.in=" + UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void getAllStocksByExchangeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where exchangeName is not null
        defaultStocksShouldBeFound("exchangeName.specified=true");

        // Get all the stocksList where exchangeName is null
        defaultStocksShouldNotBeFound("exchangeName.specified=false");
    }

    @Test
    @Transactional
    void getAllStocksByExchangeNameContainsSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where exchangeName contains DEFAULT_EXCHANGE_NAME
        defaultStocksShouldBeFound("exchangeName.contains=" + DEFAULT_EXCHANGE_NAME);

        // Get all the stocksList where exchangeName contains UPDATED_EXCHANGE_NAME
        defaultStocksShouldNotBeFound("exchangeName.contains=" + UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void getAllStocksByExchangeNameNotContainsSomething() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList where exchangeName does not contain DEFAULT_EXCHANGE_NAME
        defaultStocksShouldNotBeFound("exchangeName.doesNotContain=" + DEFAULT_EXCHANGE_NAME);

        // Get all the stocksList where exchangeName does not contain UPDATED_EXCHANGE_NAME
        defaultStocksShouldBeFound("exchangeName.doesNotContain=" + UPDATED_EXCHANGE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStocksShouldBeFound(String filter) throws Exception {
        restStocksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].code").value(hasItem(stocks.getCode())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockType").value(hasItem(DEFAULT_STOCK_TYPE)))
            .andExpect(jsonPath("$.[*].exchangeName").value(hasItem(DEFAULT_EXCHANGE_NAME)));

        // Check, that the count call also returns 1
        restStocksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStocksShouldNotBeFound(String filter) throws Exception {
        restStocksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStocksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=code,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStocks() throws Exception {
        // Get the stocks
        restStocksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStocks() throws Exception {
        // Initialize the database
        stocks.setCode(UUID.randomUUID().toString());
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();

        // Update the stocks
        Stocks updatedStocks = stocksRepository.findById(stocks.getCode()).get();
        // Disconnect from session so that the updates on updatedStocks are not directly saved in db
        em.detach(updatedStocks);
        updatedStocks
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .stockType(UPDATED_STOCK_TYPE)
            .exchangeName(UPDATED_EXCHANGE_NAME);

        restStocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStocks.getCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStocks))
            )
            .andExpect(status().isOk());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStocks.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStocks.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testStocks.getStockType()).isEqualTo(UPDATED_STOCK_TYPE);
        assertThat(testStocks.getExchangeName()).isEqualTo(UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setCode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stocks.getCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStocksWithPatch() throws Exception {
        // Initialize the database
        stocks.setCode(UUID.randomUUID().toString());
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();

        // Update the stocks using partial update
        Stocks partialUpdatedStocks = new Stocks();
        partialUpdatedStocks.setCode(stocks.getCode());

        partialUpdatedStocks.name(UPDATED_NAME).stockType(UPDATED_STOCK_TYPE).exchangeName(UPDATED_EXCHANGE_NAME);

        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStocks.getCode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStocks))
            )
            .andExpect(status().isOk());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStocks.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStocks.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testStocks.getStockType()).isEqualTo(UPDATED_STOCK_TYPE);
        assertThat(testStocks.getExchangeName()).isEqualTo(UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateStocksWithPatch() throws Exception {
        // Initialize the database
        stocks.setCode(UUID.randomUUID().toString());
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();

        // Update the stocks using partial update
        Stocks partialUpdatedStocks = new Stocks();
        partialUpdatedStocks.setCode(stocks.getCode());

        partialUpdatedStocks
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .stockType(UPDATED_STOCK_TYPE)
            .exchangeName(UPDATED_EXCHANGE_NAME);

        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStocks.getCode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStocks))
            )
            .andExpect(status().isOk());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStocks.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStocks.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testStocks.getStockType()).isEqualTo(UPDATED_STOCK_TYPE);
        assertThat(testStocks.getExchangeName()).isEqualTo(UPDATED_EXCHANGE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setCode(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stocks.getCode())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setCode(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStocks() throws Exception {
        // Initialize the database
        stocks.setCode(UUID.randomUUID().toString());
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeDelete = stocksRepository.findAll().size();

        // Delete the stocks
        restStocksMockMvc
            .perform(delete(ENTITY_API_URL_ID, stocks.getCode()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
