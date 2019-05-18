package com.metashare.web.rest;

import com.metashare.MetaShareApp;
import com.metashare.domain.ResumeHead;
import com.metashare.repository.ResumeHeadRepository;
import com.metashare.service.ResumeHeadService;
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
import java.util.List;

import static com.metashare.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ResumeHeadResource} REST controller.
 */
@SpringBootTest(classes = MetaShareApp.class)
public class ResumeHeadResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCE = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCE = "BBBBBBBBBB";

    @Autowired
    private ResumeHeadRepository resumeHeadRepository;

    @Autowired
    private ResumeHeadService resumeHeadService;

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

    private MockMvc restResumeHeadMockMvc;

    private ResumeHead resumeHead;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResumeHeadResource resumeHeadResource = new ResumeHeadResource(resumeHeadService);
        this.restResumeHeadMockMvc = MockMvcBuilders.standaloneSetup(resumeHeadResource)
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
    public static ResumeHead createEntity(EntityManager em) {
        ResumeHead resumeHead = new ResumeHead()
            .name(DEFAULT_NAME)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .age(DEFAULT_AGE)
            .photo(DEFAULT_PHOTO)
            .residence(DEFAULT_RESIDENCE);
        return resumeHead;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResumeHead createUpdatedEntity(EntityManager em) {
        ResumeHead resumeHead = new ResumeHead()
            .name(UPDATED_NAME)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .age(UPDATED_AGE)
            .photo(UPDATED_PHOTO)
            .residence(UPDATED_RESIDENCE);
        return resumeHead;
    }

    @BeforeEach
    public void initTest() {
        resumeHead = createEntity(em);
    }

    @Test
    @Transactional
    public void createResumeHead() throws Exception {
        int databaseSizeBeforeCreate = resumeHeadRepository.findAll().size();

        // Create the ResumeHead
        restResumeHeadMockMvc.perform(post("/api/resume-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeHead)))
            .andExpect(status().isCreated());

        // Validate the ResumeHead in the database
        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeHead testResumeHead = resumeHeadList.get(resumeHeadList.size() - 1);
        assertThat(testResumeHead.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResumeHead.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testResumeHead.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testResumeHead.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testResumeHead.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testResumeHead.getResidence()).isEqualTo(DEFAULT_RESIDENCE);
    }

    @Test
    @Transactional
    public void createResumeHeadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resumeHeadRepository.findAll().size();

        // Create the ResumeHead with an existing ID
        resumeHead.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeHeadMockMvc.perform(post("/api/resume-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeHead)))
            .andExpect(status().isBadRequest());

        // Validate the ResumeHead in the database
        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeHeadRepository.findAll().size();
        // set the field null
        resumeHead.setName(null);

        // Create the ResumeHead, which fails.

        restResumeHeadMockMvc.perform(post("/api/resume-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeHead)))
            .andExpect(status().isBadRequest());

        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeHeadRepository.findAll().size();
        // set the field null
        resumeHead.setMobile(null);

        // Create the ResumeHead, which fails.

        restResumeHeadMockMvc.perform(post("/api/resume-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeHead)))
            .andExpect(status().isBadRequest());

        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeHeadRepository.findAll().size();
        // set the field null
        resumeHead.setEmail(null);

        // Create the ResumeHead, which fails.

        restResumeHeadMockMvc.perform(post("/api/resume-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeHead)))
            .andExpect(status().isBadRequest());

        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeHeadRepository.findAll().size();
        // set the field null
        resumeHead.setAge(null);

        // Create the ResumeHead, which fails.

        restResumeHeadMockMvc.perform(post("/api/resume-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeHead)))
            .andExpect(status().isBadRequest());

        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResidenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = resumeHeadRepository.findAll().size();
        // set the field null
        resumeHead.setResidence(null);

        // Create the ResumeHead, which fails.

        restResumeHeadMockMvc.perform(post("/api/resume-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeHead)))
            .andExpect(status().isBadRequest());

        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResumeHeads() throws Exception {
        // Initialize the database
        resumeHeadRepository.saveAndFlush(resumeHead);

        // Get all the resumeHeadList
        restResumeHeadMockMvc.perform(get("/api/resume-heads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeHead.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].residence").value(hasItem(DEFAULT_RESIDENCE.toString())));
    }
    
    @Test
    @Transactional
    public void getResumeHead() throws Exception {
        // Initialize the database
        resumeHeadRepository.saveAndFlush(resumeHead);

        // Get the resumeHead
        restResumeHeadMockMvc.perform(get("/api/resume-heads/{id}", resumeHead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resumeHead.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.residence").value(DEFAULT_RESIDENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResumeHead() throws Exception {
        // Get the resumeHead
        restResumeHeadMockMvc.perform(get("/api/resume-heads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResumeHead() throws Exception {
        // Initialize the database
        resumeHeadService.save(resumeHead);

        int databaseSizeBeforeUpdate = resumeHeadRepository.findAll().size();

        // Update the resumeHead
        ResumeHead updatedResumeHead = resumeHeadRepository.findById(resumeHead.getId()).get();
        // Disconnect from session so that the updates on updatedResumeHead are not directly saved in db
        em.detach(updatedResumeHead);
        updatedResumeHead
            .name(UPDATED_NAME)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .age(UPDATED_AGE)
            .photo(UPDATED_PHOTO)
            .residence(UPDATED_RESIDENCE);

        restResumeHeadMockMvc.perform(put("/api/resume-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResumeHead)))
            .andExpect(status().isOk());

        // Validate the ResumeHead in the database
        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeUpdate);
        ResumeHead testResumeHead = resumeHeadList.get(resumeHeadList.size() - 1);
        assertThat(testResumeHead.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResumeHead.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testResumeHead.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testResumeHead.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testResumeHead.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testResumeHead.getResidence()).isEqualTo(UPDATED_RESIDENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingResumeHead() throws Exception {
        int databaseSizeBeforeUpdate = resumeHeadRepository.findAll().size();

        // Create the ResumeHead

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResumeHeadMockMvc.perform(put("/api/resume-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeHead)))
            .andExpect(status().isBadRequest());

        // Validate the ResumeHead in the database
        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResumeHead() throws Exception {
        // Initialize the database
        resumeHeadService.save(resumeHead);

        int databaseSizeBeforeDelete = resumeHeadRepository.findAll().size();

        // Delete the resumeHead
        restResumeHeadMockMvc.perform(delete("/api/resume-heads/{id}", resumeHead.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ResumeHead> resumeHeadList = resumeHeadRepository.findAll();
        assertThat(resumeHeadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeHead.class);
        ResumeHead resumeHead1 = new ResumeHead();
        resumeHead1.setId(1L);
        ResumeHead resumeHead2 = new ResumeHead();
        resumeHead2.setId(resumeHead1.getId());
        assertThat(resumeHead1).isEqualTo(resumeHead2);
        resumeHead2.setId(2L);
        assertThat(resumeHead1).isNotEqualTo(resumeHead2);
        resumeHead1.setId(null);
        assertThat(resumeHead1).isNotEqualTo(resumeHead2);
    }
}
