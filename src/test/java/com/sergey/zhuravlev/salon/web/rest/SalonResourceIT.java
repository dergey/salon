package com.sergey.zhuravlev.salon.web.rest;

import com.sergey.zhuravlev.salon.SalonApp;
import com.sergey.zhuravlev.salon.domain.Salon;
import com.sergey.zhuravlev.salon.repository.SalonRepository;
import com.sergey.zhuravlev.salon.service.SalonService;
import com.sergey.zhuravlev.salon.web.rest.errors.ExceptionTranslator;

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

import static com.sergey.zhuravlev.salon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SalonResource} REST controller.
 */
@SpringBootTest(classes = SalonApp.class)
public class SalonResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private SalonRepository salonRepository;

    @Autowired
    private SalonService salonService;

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

    private MockMvc restSalonMockMvc;

    private Salon salon;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalonResource salonResource = new SalonResource(salonService);
        this.restSalonMockMvc = MockMvcBuilders.standaloneSetup(salonResource)
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
    public static Salon createEntity(EntityManager em) {
        Salon salon = new Salon()
            .title(DEFAULT_TITLE);
        return salon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salon createUpdatedEntity(EntityManager em) {
        Salon salon = new Salon()
            .title(UPDATED_TITLE);
        return salon;
    }

    @BeforeEach
    public void initTest() {
        salon = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalon() throws Exception {
        int databaseSizeBeforeCreate = salonRepository.findAll().size();

        // Create the Salon
        restSalonMockMvc.perform(post("/api/salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salon)))
            .andExpect(status().isCreated());

        // Validate the Salon in the database
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeCreate + 1);
        Salon testSalon = salonList.get(salonList.size() - 1);
        assertThat(testSalon.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createSalonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salonRepository.findAll().size();

        // Create the Salon with an existing ID
        salon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalonMockMvc.perform(post("/api/salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salon)))
            .andExpect(status().isBadRequest());

        // Validate the Salon in the database
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = salonRepository.findAll().size();
        // set the field null
        salon.setTitle(null);

        // Create the Salon, which fails.

        restSalonMockMvc.perform(post("/api/salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salon)))
            .andExpect(status().isBadRequest());

        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalons() throws Exception {
        // Initialize the database
        salonRepository.saveAndFlush(salon);

        // Get all the salonList
        restSalonMockMvc.perform(get("/api/salons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salon.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getSalon() throws Exception {
        // Initialize the database
        salonRepository.saveAndFlush(salon);

        // Get the salon
        restSalonMockMvc.perform(get("/api/salons/{id}", salon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salon.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    public void getNonExistingSalon() throws Exception {
        // Get the salon
        restSalonMockMvc.perform(get("/api/salons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalon() throws Exception {
        // Initialize the database
        salonService.save(salon);

        int databaseSizeBeforeUpdate = salonRepository.findAll().size();

        // Update the salon
        Salon updatedSalon = salonRepository.findById(salon.getId()).get();
        // Disconnect from session so that the updates on updatedSalon are not directly saved in db
        em.detach(updatedSalon);
        updatedSalon
            .title(UPDATED_TITLE);

        restSalonMockMvc.perform(put("/api/salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalon)))
            .andExpect(status().isOk());

        // Validate the Salon in the database
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeUpdate);
        Salon testSalon = salonList.get(salonList.size() - 1);
        assertThat(testSalon.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingSalon() throws Exception {
        int databaseSizeBeforeUpdate = salonRepository.findAll().size();

        // Create the Salon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalonMockMvc.perform(put("/api/salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salon)))
            .andExpect(status().isBadRequest());

        // Validate the Salon in the database
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalon() throws Exception {
        // Initialize the database
        salonService.save(salon);

        int databaseSizeBeforeDelete = salonRepository.findAll().size();

        // Delete the salon
        restSalonMockMvc.perform(delete("/api/salons/{id}", salon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Salon> salonList = salonRepository.findAll();
        assertThat(salonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
