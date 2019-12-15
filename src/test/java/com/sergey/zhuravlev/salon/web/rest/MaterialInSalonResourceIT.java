package com.sergey.zhuravlev.salon.web.rest;

import com.sergey.zhuravlev.salon.SalonApp;
import com.sergey.zhuravlev.salon.domain.MaterialInSalon;
import com.sergey.zhuravlev.salon.repository.MaterialInSalonRepository;
import com.sergey.zhuravlev.salon.service.MaterialInSalonService;
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
 * Integration tests for the {@link MaterialInSalonResource} REST controller.
 */
@SpringBootTest(classes = SalonApp.class)
public class MaterialInSalonResourceIT {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Autowired
    private MaterialInSalonRepository materialInSalonRepository;

    @Autowired
    private MaterialInSalonService materialInSalonService;

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

    private MockMvc restMaterialInSalonMockMvc;

    private MaterialInSalon materialInSalon;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaterialInSalonResource materialInSalonResource = new MaterialInSalonResource(materialInSalonService);
        this.restMaterialInSalonMockMvc = MockMvcBuilders.standaloneSetup(materialInSalonResource)
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
    public static MaterialInSalon createEntity(EntityManager em) {
        MaterialInSalon materialInSalon = new MaterialInSalon()
            .count(DEFAULT_COUNT);
        return materialInSalon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialInSalon createUpdatedEntity(EntityManager em) {
        MaterialInSalon materialInSalon = new MaterialInSalon()
            .count(UPDATED_COUNT);
        return materialInSalon;
    }

    @BeforeEach
    public void initTest() {
        materialInSalon = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialInSalon() throws Exception {
        int databaseSizeBeforeCreate = materialInSalonRepository.findAll().size();

        // Create the MaterialInSalon
        restMaterialInSalonMockMvc.perform(post("/api/material-in-salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialInSalon)))
            .andExpect(status().isCreated());

        // Validate the MaterialInSalon in the database
        List<MaterialInSalon> materialInSalonList = materialInSalonRepository.findAll();
        assertThat(materialInSalonList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialInSalon testMaterialInSalon = materialInSalonList.get(materialInSalonList.size() - 1);
        assertThat(testMaterialInSalon.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createMaterialInSalonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialInSalonRepository.findAll().size();

        // Create the MaterialInSalon with an existing ID
        materialInSalon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialInSalonMockMvc.perform(post("/api/material-in-salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialInSalon)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialInSalon in the database
        List<MaterialInSalon> materialInSalonList = materialInSalonRepository.findAll();
        assertThat(materialInSalonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialInSalonRepository.findAll().size();
        // set the field null
        materialInSalon.setCount(null);

        // Create the MaterialInSalon, which fails.

        restMaterialInSalonMockMvc.perform(post("/api/material-in-salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialInSalon)))
            .andExpect(status().isBadRequest());

        List<MaterialInSalon> materialInSalonList = materialInSalonRepository.findAll();
        assertThat(materialInSalonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterialInSalons() throws Exception {
        // Initialize the database
        materialInSalonRepository.saveAndFlush(materialInSalon);

        // Get all the materialInSalonList
        restMaterialInSalonMockMvc.perform(get("/api/material-in-salons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialInSalon.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }
    
    @Test
    @Transactional
    public void getMaterialInSalon() throws Exception {
        // Initialize the database
        materialInSalonRepository.saveAndFlush(materialInSalon);

        // Get the materialInSalon
        restMaterialInSalonMockMvc.perform(get("/api/material-in-salons/{id}", materialInSalon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(materialInSalon.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingMaterialInSalon() throws Exception {
        // Get the materialInSalon
        restMaterialInSalonMockMvc.perform(get("/api/material-in-salons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialInSalon() throws Exception {
        // Initialize the database
        materialInSalonService.save(materialInSalon);

        int databaseSizeBeforeUpdate = materialInSalonRepository.findAll().size();

        // Update the materialInSalon
        MaterialInSalon updatedMaterialInSalon = materialInSalonRepository.findById(materialInSalon.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialInSalon are not directly saved in db
        em.detach(updatedMaterialInSalon);
        updatedMaterialInSalon
            .count(UPDATED_COUNT);

        restMaterialInSalonMockMvc.perform(put("/api/material-in-salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaterialInSalon)))
            .andExpect(status().isOk());

        // Validate the MaterialInSalon in the database
        List<MaterialInSalon> materialInSalonList = materialInSalonRepository.findAll();
        assertThat(materialInSalonList).hasSize(databaseSizeBeforeUpdate);
        MaterialInSalon testMaterialInSalon = materialInSalonList.get(materialInSalonList.size() - 1);
        assertThat(testMaterialInSalon.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialInSalon() throws Exception {
        int databaseSizeBeforeUpdate = materialInSalonRepository.findAll().size();

        // Create the MaterialInSalon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialInSalonMockMvc.perform(put("/api/material-in-salons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialInSalon)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialInSalon in the database
        List<MaterialInSalon> materialInSalonList = materialInSalonRepository.findAll();
        assertThat(materialInSalonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaterialInSalon() throws Exception {
        // Initialize the database
        materialInSalonService.save(materialInSalon);

        int databaseSizeBeforeDelete = materialInSalonRepository.findAll().size();

        // Delete the materialInSalon
        restMaterialInSalonMockMvc.perform(delete("/api/material-in-salons/{id}", materialInSalon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialInSalon> materialInSalonList = materialInSalonRepository.findAll();
        assertThat(materialInSalonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
