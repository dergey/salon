package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.SalonApp;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration tests for the {@link ServiceController} REST controller.
 */
@SpringBootTest(classes = SalonApp.class)
public class ServiceControllerIT {
/*
    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(1L);
    private static final BigDecimal UPDATED_PRICE = BigDecimal.valueOf(2L);

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceService serviceService;

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

    private MockMvc restServiceMockMvc;

    private Service service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceController serviceController = new ServiceController(serviceService);
        this.restServiceMockMvc = MockMvcBuilders.standaloneSetup(serviceController)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    public static Service createEntity(EntityManager em) {
        Service service = Service.builder()
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .build();
        return service;
    }

    public static Service createUpdatedEntity(EntityManager em) {
        Service service = Service.builder()
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .build();
        return service;
    }

    @BeforeEach
    public void initTest() {
        service = createEntity(em);
    }

    @Test
    @Transactional
    public void createService() throws Exception {
        int databaseSizeBeforeCreate = serviceRepository.findAll().size();

        // Create the Service
        restServiceMockMvc.perform(post("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isCreated());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeCreate + 1);
        Service testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testService.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRepository.findAll().size();

        // Create the Service with an existing ID
        service.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceMockMvc.perform(post("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRepository.findAll().size();
        // set the field null
        service.setTitle(null);

        // Create the Service, which fails.

        restServiceMockMvc.perform(post("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isBadRequest());

        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRepository.findAll().size();
        // set the field null
        service.setPrice(null);

        // Create the Service, which fails.

        restServiceMockMvc.perform(post("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isBadRequest());

        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServices() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList
        restServiceMockMvc.perform(get("/api/services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get the service
        restServiceMockMvc.perform(get("/api/services/{id}", service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(service.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingService() throws Exception {
        // Get the service
        restServiceMockMvc.perform(get("/api/services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateService() throws Exception {
        // Initialize the database
        serviceService.save(service);

        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Update the service
        Service updatedService = serviceRepository.findById(service.getId()).get();
        // Disconnect from session so that the updates on updatedService are not directly saved in db
        em.detach(updatedService);
        updatedService.setTitle(UPDATED_TITLE);
        updatedService.setPrice(UPDATED_PRICE);

        restServiceMockMvc.perform(put("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedService)))
            .andExpect(status().isOk());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
        Service testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testService.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingService() throws Exception {
        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Create the Service

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMockMvc.perform(put("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteService() throws Exception {
        // Initialize the database
        serviceService.save(service);

        int databaseSizeBeforeDelete = serviceRepository.findAll().size();

        // Delete the service
        restServiceMockMvc.perform(delete("/api/services/{id}", service.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
*/
}
