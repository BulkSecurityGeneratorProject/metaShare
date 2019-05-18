package com.metashare.web.rest;

import com.metashare.MetaShareApp;
import com.metashare.domain.Order;
import com.metashare.repository.OrderRepository;
import com.metashare.service.OrderService;
import com.metashare.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.metashare.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link OrderResource} REST controller.
 */
@SpringBootTest(classes = MetaShareApp.class)
public class OrderResourceIT {

    private static final String DEFAULT_ORDER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GOODS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GOODS_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_SUBTOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_TOTAL_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX_TATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_TATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SUM_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUM_AMOUNT = new BigDecimal(2);

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_UPDATEBY = 1L;
    private static final Long UPDATED_UPDATEBY = 2L;

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOrderMockMvc;

    private Order order;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderResource orderResource = new OrderResource(orderService);
        this.restOrderMockMvc = MockMvcBuilders.standaloneSetup(orderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .orderCode(DEFAULT_ORDER_CODE)
            .goodsName(DEFAULT_GOODS_NAME)
            .quantity(DEFAULT_QUANTITY)
            .subtotalAmount(DEFAULT_SUBTOTAL_AMOUNT)
            .price(DEFAULT_PRICE)
            .taxTotalAmount(DEFAULT_TAX_TOTAL_AMOUNT)
            .taxTate(DEFAULT_TAX_TATE)
            .sumAmount(DEFAULT_SUM_AMOUNT)
            .createBy(DEFAULT_CREATE_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updateby(DEFAULT_UPDATEBY)
            .updateTime(DEFAULT_UPDATE_TIME)
            .version(DEFAULT_VERSION)
            .status(DEFAULT_STATUS);
        return order;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .orderCode(UPDATED_ORDER_CODE)
            .goodsName(UPDATED_GOODS_NAME)
            .quantity(UPDATED_QUANTITY)
            .subtotalAmount(UPDATED_SUBTOTAL_AMOUNT)
            .price(UPDATED_PRICE)
            .taxTotalAmount(UPDATED_TAX_TOTAL_AMOUNT)
            .taxTate(UPDATED_TAX_TATE)
            .sumAmount(UPDATED_SUM_AMOUNT)
            .createBy(UPDATED_CREATE_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updateby(UPDATED_UPDATEBY)
            .updateTime(UPDATED_UPDATE_TIME)
            .version(UPDATED_VERSION)
            .status(UPDATED_STATUS);
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getOrderCode()).isEqualTo(DEFAULT_ORDER_CODE);
        assertThat(testOrder.getGoodsName()).isEqualTo(DEFAULT_GOODS_NAME);
        assertThat(testOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrder.getSubtotalAmount()).isEqualTo(DEFAULT_SUBTOTAL_AMOUNT);
        assertThat(testOrder.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOrder.getTaxTotalAmount()).isEqualTo(DEFAULT_TAX_TOTAL_AMOUNT);
        assertThat(testOrder.getTaxTate()).isEqualTo(DEFAULT_TAX_TATE);
        assertThat(testOrder.getSumAmount()).isEqualTo(DEFAULT_SUM_AMOUNT);
        assertThat(testOrder.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testOrder.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testOrder.getUpdateby()).isEqualTo(DEFAULT_UPDATEBY);
        assertThat(testOrder.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testOrder.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order with an existing ID
        order.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGoodsNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setGoodsName(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setQuantity(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setSubtotalAmount(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setPrice(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setTaxTotalAmount(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxTateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setTaxTate(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSumAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setSumAmount(null);

        // Create the Order, which fails.

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderCode").value(hasItem(DEFAULT_ORDER_CODE.toString())))
            .andExpect(jsonPath("$.[*].goodsName").value(hasItem(DEFAULT_GOODS_NAME.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].subtotalAmount").value(hasItem(DEFAULT_SUBTOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].taxTotalAmount").value(hasItem(DEFAULT_TAX_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].taxTate").value(hasItem(DEFAULT_TAX_TATE.intValue())))
            .andExpect(jsonPath("$.[*].sumAmount").value(hasItem(DEFAULT_SUM_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateby").value(hasItem(DEFAULT_UPDATEBY.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.orderCode").value(DEFAULT_ORDER_CODE.toString()))
            .andExpect(jsonPath("$.goodsName").value(DEFAULT_GOODS_NAME.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.subtotalAmount").value(DEFAULT_SUBTOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.taxTotalAmount").value(DEFAULT_TAX_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.taxTate").value(DEFAULT_TAX_TATE.intValue()))
            .andExpect(jsonPath("$.sumAmount").value(DEFAULT_SUM_AMOUNT.intValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateby").value(DEFAULT_UPDATEBY.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrder() throws Exception {
        // Initialize the database
        orderService.save(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .orderCode(UPDATED_ORDER_CODE)
            .goodsName(UPDATED_GOODS_NAME)
            .quantity(UPDATED_QUANTITY)
            .subtotalAmount(UPDATED_SUBTOTAL_AMOUNT)
            .price(UPDATED_PRICE)
            .taxTotalAmount(UPDATED_TAX_TOTAL_AMOUNT)
            .taxTate(UPDATED_TAX_TATE)
            .sumAmount(UPDATED_SUM_AMOUNT)
            .createBy(UPDATED_CREATE_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updateby(UPDATED_UPDATEBY)
            .updateTime(UPDATED_UPDATE_TIME)
            .version(UPDATED_VERSION)
            .status(UPDATED_STATUS);

        restOrderMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrder)))
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getOrderCode()).isEqualTo(UPDATED_ORDER_CODE);
        assertThat(testOrder.getGoodsName()).isEqualTo(UPDATED_GOODS_NAME);
        assertThat(testOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrder.getSubtotalAmount()).isEqualTo(UPDATED_SUBTOTAL_AMOUNT);
        assertThat(testOrder.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOrder.getTaxTotalAmount()).isEqualTo(UPDATED_TAX_TOTAL_AMOUNT);
        assertThat(testOrder.getTaxTate()).isEqualTo(UPDATED_TAX_TATE);
        assertThat(testOrder.getSumAmount()).isEqualTo(UPDATED_SUM_AMOUNT);
        assertThat(testOrder.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testOrder.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testOrder.getUpdateby()).isEqualTo(UPDATED_UPDATEBY);
        assertThat(testOrder.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testOrder.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testOrder.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Create the Order

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderService.save(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);
        order2.setId(2L);
        assertThat(order1).isNotEqualTo(order2);
        order1.setId(null);
        assertThat(order1).isNotEqualTo(order2);
    }
}
