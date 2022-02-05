package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.SalonApp;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration tests for the {@link UsedMaterialController} REST controller.
 */
@SpringBootTest(classes = SalonApp.class)
public class UsedMaterialControllerIT {
/*
    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final Boolean DEFAULT_DECOMMISSION = false;
    private static final Boolean UPDATED_DECOMMISSION = true;

    @Autowired
    private UsedMaterialRepository usedMaterialRepository;

    @Autowired
    private UsedMaterialService usedMaterialService;

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

    private MockMvc restUsedMaterialMockMvc;

    private UsedMaterial usedMaterial;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsedMaterialController usedMaterialController = new UsedMaterialController(usedMaterialService);
        this.restUsedMaterialMockMvc = MockMvcBuilders.standaloneSetup(usedMaterialController)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    public static UsedMaterial createEntity(EntityManager em) {
        UsedMaterial usedMaterial = new UsedMaterial()
            .count(DEFAULT_COUNT)
            .decommission(DEFAULT_DECOMMISSION);
        return usedMaterial;
    }

    public static UsedMaterial createUpdatedEntity(EntityManager em) {
        UsedMaterial usedMaterial = new UsedMaterial()
            .count(UPDATED_COUNT)
            .decommission(UPDATED_DECOMMISSION);
        return usedMaterial;
    }

    @BeforeEach
    public void initTest() {
        usedMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsedMaterial() throws Exception {
        int databaseSizeBeforeCreate = usedMaterialRepository.findAll().size();

        // Create the UsedMaterial
        restUsedMaterialMockMvc.perform(post("/api/used-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usedMaterial)))
            .andExpect(status().isCreated());

        // Validate the UsedMaterial in the database
        List<UsedMaterial> usedMaterialList = usedMaterialRepository.findAll();
        assertThat(usedMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        UsedMaterial testUsedMaterial = usedMaterialList.get(usedMaterialList.size() - 1);
        assertThat(testUsedMaterial.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testUsedMaterial.isDecommission()).isEqualTo(DEFAULT_DECOMMISSION);
    }

    @Test
    @Transactional
    public void createUsedMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usedMaterialRepository.findAll().size();

        // Create the UsedMaterial with an existing ID
        usedMaterial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsedMaterialMockMvc.perform(post("/api/used-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usedMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the UsedMaterial in the database
        List<UsedMaterial> usedMaterialList = usedMaterialRepository.findAll();
        assertThat(usedMaterialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = usedMaterialRepository.findAll().size();
        // set the field null
        usedMaterial.setCount(null);

        // Create the UsedMaterial, which fails.

        restUsedMaterialMockMvc.perform(post("/api/used-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usedMaterial)))
            .andExpect(status().isBadRequest());

        List<UsedMaterial> usedMaterialList = usedMaterialRepository.findAll();
        assertThat(usedMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsedMaterials() throws Exception {
        // Initialize the database
        usedMaterialRepository.saveAndFlush(usedMaterial);

        // Get all the usedMaterialList
        restUsedMaterialMockMvc.perform(get("/api/used-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usedMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].decommission").value(hasItem(DEFAULT_DECOMMISSION.booleanValue())));
    }

    @Test
    @Transactional
    public void getUsedMaterial() throws Exception {
        // Initialize the database
        usedMaterialRepository.saveAndFlush(usedMaterial);

        // Get the usedMaterial
        restUsedMaterialMockMvc.perform(get("/api/used-materials/{id}", usedMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usedMaterial.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.decommission").value(DEFAULT_DECOMMISSION.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUsedMaterial() throws Exception {
        // Get the usedMaterial
        restUsedMaterialMockMvc.perform(get("/api/used-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsedMaterial() throws Exception {
        // Initialize the database
        usedMaterialService.save(usedMaterial);

        int databaseSizeBeforeUpdate = usedMaterialRepository.findAll().size();

        // Update the usedMaterial
        UsedMaterial updatedUsedMaterial = usedMaterialRepository.findById(usedMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedUsedMaterial are not directly saved in db
        em.detach(updatedUsedMaterial);
        updatedUsedMaterial
            .count(UPDATED_COUNT)
            .decommission(UPDATED_DECOMMISSION);

        restUsedMaterialMockMvc.perform(put("/api/used-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsedMaterial)))
            .andExpect(status().isOk());

        // Validate the UsedMaterial in the database
        List<UsedMaterial> usedMaterialList = usedMaterialRepository.findAll();
        assertThat(usedMaterialList).hasSize(databaseSizeBeforeUpdate);
        UsedMaterial testUsedMaterial = usedMaterialList.get(usedMaterialList.size() - 1);
        assertThat(testUsedMaterial.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testUsedMaterial.isDecommission()).isEqualTo(UPDATED_DECOMMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingUsedMaterial() throws Exception {
        int databaseSizeBeforeUpdate = usedMaterialRepository.findAll().size();

        // Create the UsedMaterial

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsedMaterialMockMvc.perform(put("/api/used-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usedMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the UsedMaterial in the database
        List<UsedMaterial> usedMaterialList = usedMaterialRepository.findAll();
        assertThat(usedMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsedMaterial() throws Exception {
        // Initialize the database
        usedMaterialService.save(usedMaterial);

        int databaseSizeBeforeDelete = usedMaterialRepository.findAll().size();

        // Delete the usedMaterial
        restUsedMaterialMockMvc.perform(delete("/api/used-materials/{id}", usedMaterial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsedMaterial> usedMaterialList = usedMaterialRepository.findAll();
        assertThat(usedMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }
*/
}
