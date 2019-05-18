package com.metashare.web.rest;

import com.metashare.MetaShareApp;
import com.metashare.domain.Goods;
import com.metashare.repository.GoodsRepository;
import com.metashare.service.GoodsService;
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
import java.util.List;

import static com.metashare.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link GoodsResource} REST controller.
 */
@SpringBootTest(classes = MetaShareApp.class)
public class GoodsResourceIT {

    private static final Long DEFAULT_GOODS_ID = 1L;
    private static final Long UPDATED_GOODS_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE_CODE = "AAAAAA";
    private static final String UPDATED_PROVINCE_CODE = "BBBBBB";

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Integer DEFAULT_STACK = 1;
    private static final Integer UPDATED_STACK = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsService goodsService;

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

    private MockMvc restGoodsMockMvc;

    private Goods goods;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GoodsResource goodsResource = new GoodsResource(goodsService);
        this.restGoodsMockMvc = MockMvcBuilders.standaloneSetup(goodsResource)
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
    public static Goods createEntity(EntityManager em) {
        Goods goods = new Goods()
            .goodsId(DEFAULT_GOODS_ID)
            .name(DEFAULT_NAME)
            .provinceCode(DEFAULT_PROVINCE_CODE)
            .rate(DEFAULT_RATE)
            .status(DEFAULT_STATUS)
            .stack(DEFAULT_STACK)
            .price(DEFAULT_PRICE);
        return goods;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goods createUpdatedEntity(EntityManager em) {
        Goods goods = new Goods()
            .goodsId(UPDATED_GOODS_ID)
            .name(UPDATED_NAME)
            .provinceCode(UPDATED_PROVINCE_CODE)
            .rate(UPDATED_RATE)
            .status(UPDATED_STATUS)
            .stack(UPDATED_STACK)
            .price(UPDATED_PRICE);
        return goods;
    }

    @BeforeEach
    public void initTest() {
        goods = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoods() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isCreated());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate + 1);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getGoodsId()).isEqualTo(DEFAULT_GOODS_ID);
        assertThat(testGoods.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoods.getProvinceCode()).isEqualTo(DEFAULT_PROVINCE_CODE);
        assertThat(testGoods.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testGoods.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testGoods.getStack()).isEqualTo(DEFAULT_STACK);
        assertThat(testGoods.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createGoodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods with an existing ID
        goods.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGoodsIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setGoodsId(null);

        // Create the Goods, which fails.

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStackIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setStack(null);

        // Create the Goods, which fails.

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setPrice(null);

        // Create the Goods, which fails.

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get all the goodsList
        restGoodsMockMvc.perform(get("/api/goods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goods.getId().intValue())))
            .andExpect(jsonPath("$.[*].goodsId").value(hasItem(DEFAULT_GOODS_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].provinceCode").value(hasItem(DEFAULT_PROVINCE_CODE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].stack").value(hasItem(DEFAULT_STACK)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", goods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goods.getId().intValue()))
            .andExpect(jsonPath("$.goodsId").value(DEFAULT_GOODS_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.provinceCode").value(DEFAULT_PROVINCE_CODE.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.stack").value(DEFAULT_STACK))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGoods() throws Exception {
        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoods() throws Exception {
        // Initialize the database
        goodsService.save(goods);

        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Update the goods
        Goods updatedGoods = goodsRepository.findById(goods.getId()).get();
        // Disconnect from session so that the updates on updatedGoods are not directly saved in db
        em.detach(updatedGoods);
        updatedGoods
            .goodsId(UPDATED_GOODS_ID)
            .name(UPDATED_NAME)
            .provinceCode(UPDATED_PROVINCE_CODE)
            .rate(UPDATED_RATE)
            .status(UPDATED_STATUS)
            .stack(UPDATED_STACK)
            .price(UPDATED_PRICE);

        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGoods)))
            .andExpect(status().isOk());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getGoodsId()).isEqualTo(UPDATED_GOODS_ID);
        assertThat(testGoods.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoods.getProvinceCode()).isEqualTo(UPDATED_PROVINCE_CODE);
        assertThat(testGoods.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testGoods.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testGoods.getStack()).isEqualTo(UPDATED_STACK);
        assertThat(testGoods.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingGoods() throws Exception {
        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Create the Goods

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoods() throws Exception {
        // Initialize the database
        goodsService.save(goods);

        int databaseSizeBeforeDelete = goodsRepository.findAll().size();

        // Delete the goods
        restGoodsMockMvc.perform(delete("/api/goods/{id}", goods.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Goods.class);
        Goods goods1 = new Goods();
        goods1.setId(1L);
        Goods goods2 = new Goods();
        goods2.setId(goods1.getId());
        assertThat(goods1).isEqualTo(goods2);
        goods2.setId(2L);
        assertThat(goods1).isNotEqualTo(goods2);
        goods1.setId(null);
        assertThat(goods1).isNotEqualTo(goods2);
    }
}
