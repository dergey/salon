package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.SalonApp;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration tests for the {@link SpecializationController} REST controller.
 */
@SpringBootTest(classes = SalonApp.class)
public class SpecializationControllerIT {
/*
    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private SpecializationService specializationService;

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

    private MockMvc restSpecializationMockMvc;

    private Specialization specialization;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecializationController specializationController = new SpecializationController(specializationService);
        this.restSpecializationMockMvc = MockMvcBuilders.standaloneSetup(specializationController)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    public static Specialization createEntity(EntityManager em) {
        Specialization specialization = new Specialization()
            .note(DEFAULT_NOTE);
        return specialization;
    }

    public static Specialization createUpdatedEntity(EntityManager em) {
        Specialization specialization = new Specialization()
            .note(UPDATED_NOTE);
        return specialization;
    }

    @BeforeEach
    public void initTest() {
        specialization = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialization() throws Exception {
        int databaseSizeBeforeCreate = specializationRepository.findAll().size();

        // Create the Specialization
        restSpecializationMockMvc.perform(post("/api/specializations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialization)))
            .andExpect(status().isCreated());

        // Validate the Specialization in the database
        List<Specialization> specializationList = specializationRepository.findAll();
        assertThat(specializationList).hasSize(databaseSizeBeforeCreate + 1);
        Specialization testSpecialization = specializationList.get(specializationList.size() - 1);
        assertThat(testSpecialization.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createSpecializationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specializationRepository.findAll().size();

        // Create the Specialization with an existing ID
        specialization.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecializationMockMvc.perform(post("/api/specializations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialization)))
            .andExpect(status().isBadRequest());

        // Validate the Specialization in the database
        List<Specialization> specializationList = specializationRepository.findAll();
        assertThat(specializationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSpecializations() throws Exception {
        // Initialize the database
        specializationRepository.saveAndFlush(specialization);

        // Get all the specializationList
        restSpecializationMockMvc.perform(get("/api/specializations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialization.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    public void getSpecialization() throws Exception {
        // Initialize the database
        specializationRepository.saveAndFlush(specialization);

        // Get the specialization
        restSpecializationMockMvc.perform(get("/api/specializations/{id}", specialization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(specialization.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialization() throws Exception {
        // Get the specialization
        restSpecializationMockMvc.perform(get("/api/specializations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialization() throws Exception {
        // Initialize the database
        specializationService.save(specialization);

        int databaseSizeBeforeUpdate = specializationRepository.findAll().size();

        // Update the specialization
        Specialization updatedSpecialization = specializationRepository.findById(specialization.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialization are not directly saved in db
        em.detach(updatedSpecialization);
        updatedSpecialization
            .note(UPDATED_NOTE);

        restSpecializationMockMvc.perform(put("/api/specializations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpecialization)))
            .andExpect(status().isOk());

        // Validate the Specialization in the database
        List<Specialization> specializationList = specializationRepository.findAll();
        assertThat(specializationList).hasSize(databaseSizeBeforeUpdate);
        Specialization testSpecialization = specializationList.get(specializationList.size() - 1);
        assertThat(testSpecialization.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialization() throws Exception {
        int databaseSizeBeforeUpdate = specializationRepository.findAll().size();

        // Create the Specialization

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecializationMockMvc.perform(put("/api/specializations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialization)))
            .andExpect(status().isBadRequest());

        // Validate the Specialization in the database
        List<Specialization> specializationList = specializationRepository.findAll();
        assertThat(specializationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialization() throws Exception {
        // Initialize the database
        specializationService.save(specialization);

        int databaseSizeBeforeDelete = specializationRepository.findAll().size();

        // Delete the specialization
        restSpecializationMockMvc.perform(delete("/api/specializations/{id}", specialization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specialization> specializationList = specializationRepository.findAll();
        assertThat(specializationList).hasSize(databaseSizeBeforeDelete - 1);
    }
*/
}
