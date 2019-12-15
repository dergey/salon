package com.sergey.zhuravlev.salon.web.rest;

import com.sergey.zhuravlev.salon.SalonApp;
import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import com.sergey.zhuravlev.salon.repository.ServiceProvidedRepository;
import com.sergey.zhuravlev.salon.service.ServiceProvidedService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.sergey.zhuravlev.salon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServiceProvidedResource} REST controller.
 */
@SpringBootTest(classes = SalonApp.class)
public class ServiceProvidedResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private ServiceProvidedRepository serviceProvidedRepository;

    @Autowired
    private ServiceProvidedService serviceProvidedService;

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

    private MockMvc restServiceProvidedMockMvc;

    private ServiceProvided serviceProvided;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceProvidedResource serviceProvidedResource = new ServiceProvidedResource(serviceProvidedService);
        this.restServiceProvidedMockMvc = MockMvcBuilders.standaloneSetup(serviceProvidedResource)
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
    public static ServiceProvided createEntity(EntityManager em) {
        ServiceProvided serviceProvided = new ServiceProvided()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .note(DEFAULT_NOTE);
        return serviceProvided;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceProvided createUpdatedEntity(EntityManager em) {
        ServiceProvided serviceProvided = new ServiceProvided()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .note(UPDATED_NOTE);
        return serviceProvided;
    }

    @BeforeEach
    public void initTest() {
        serviceProvided = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceProvided() throws Exception {
        int databaseSizeBeforeCreate = serviceProvidedRepository.findAll().size();

        // Create the ServiceProvided
        restServiceProvidedMockMvc.perform(post("/api/service-provideds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvided)))
            .andExpect(status().isCreated());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceProvided testServiceProvided = serviceProvidedList.get(serviceProvidedList.size() - 1);
        assertThat(testServiceProvided.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testServiceProvided.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testServiceProvided.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createServiceProvidedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceProvidedRepository.findAll().size();

        // Create the ServiceProvided with an existing ID
        serviceProvided.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceProvidedMockMvc.perform(post("/api/service-provideds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvided)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceProvideds() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get all the serviceProvidedList
        restServiceProvidedMockMvc.perform(get("/api/service-provideds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceProvided.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getServiceProvided() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get the serviceProvided
        restServiceProvidedMockMvc.perform(get("/api/service-provideds/{id}", serviceProvided.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceProvided.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    public void getNonExistingServiceProvided() throws Exception {
        // Get the serviceProvided
        restServiceProvidedMockMvc.perform(get("/api/service-provideds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceProvided() throws Exception {
        // Initialize the database
        serviceProvidedService.save(serviceProvided);

        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();

        // Update the serviceProvided
        ServiceProvided updatedServiceProvided = serviceProvidedRepository.findById(serviceProvided.getId()).get();
        // Disconnect from session so that the updates on updatedServiceProvided are not directly saved in db
        em.detach(updatedServiceProvided);
        updatedServiceProvided
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .note(UPDATED_NOTE);

        restServiceProvidedMockMvc.perform(put("/api/service-provideds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceProvided)))
            .andExpect(status().isOk());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
        ServiceProvided testServiceProvided = serviceProvidedList.get(serviceProvidedList.size() - 1);
        assertThat(testServiceProvided.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testServiceProvided.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testServiceProvided.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceProvided() throws Exception {
        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();

        // Create the ServiceProvided

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceProvidedMockMvc.perform(put("/api/service-provideds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvided)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceProvided() throws Exception {
        // Initialize the database
        serviceProvidedService.save(serviceProvided);

        int databaseSizeBeforeDelete = serviceProvidedRepository.findAll().size();

        // Delete the serviceProvided
        restServiceProvidedMockMvc.perform(delete("/api/service-provideds/{id}", serviceProvided.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
