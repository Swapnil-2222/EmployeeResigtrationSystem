package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourtCase;
import com.mycompany.myapp.repository.CourtCaseRepository;
import com.mycompany.myapp.service.criteria.CourtCaseCriteria;
import com.mycompany.myapp.service.dto.CourtCaseDTO;
import com.mycompany.myapp.service.mapper.CourtCaseMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CourtCaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourtCaseResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_SALARY = "AAAAAAAAAA";
    private static final String UPDATED_SALARY = "BBBBBBBBBB";

    private static final String DEFAULT_GST_ON_SALARY = "AAAAAAAAAA";
    private static final String UPDATED_GST_ON_SALARY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/court-cases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourtCaseRepository courtCaseRepository;

    @Autowired
    private CourtCaseMapper courtCaseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourtCaseMockMvc;

    private CourtCase courtCase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCase createEntity(EntityManager em) {
        CourtCase courtCase = new CourtCase()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address(DEFAULT_ADDRESS)
            .contactNo(DEFAULT_CONTACT_NO)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .salary(DEFAULT_SALARY)
            .gstOnSalary(DEFAULT_GST_ON_SALARY)
            .lastModified(DEFAULT_LAST_MODIFIED);
        return courtCase;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCase createUpdatedEntity(EntityManager em) {
        CourtCase courtCase = new CourtCase()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .contactNo(UPDATED_CONTACT_NO)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .salary(UPDATED_SALARY)
            .gstOnSalary(UPDATED_GST_ON_SALARY)
            .lastModified(UPDATED_LAST_MODIFIED);
        return courtCase;
    }

    @BeforeEach
    public void initTest() {
        courtCase = createEntity(em);
    }

    @Test
    @Transactional
    void createCourtCase() throws Exception {
        int databaseSizeBeforeCreate = courtCaseRepository.findAll().size();
        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);
        restCourtCaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseDTO)))
            .andExpect(status().isCreated());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeCreate + 1);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCourtCase.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCourtCase.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCourtCase.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testCourtCase.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testCourtCase.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testCourtCase.getGstOnSalary()).isEqualTo(DEFAULT_GST_ON_SALARY);
        assertThat(testCourtCase.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void createCourtCaseWithExistingId() throws Exception {
        // Create the CourtCase with an existing ID
        courtCase.setId(1L);
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        int databaseSizeBeforeCreate = courtCaseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourtCaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourtCases() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY)))
            .andExpect(jsonPath("$.[*].gstOnSalary").value(hasItem(DEFAULT_GST_ON_SALARY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)));
    }

    @Test
    @Transactional
    void getCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get the courtCase
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL_ID, courtCase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courtCase.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY))
            .andExpect(jsonPath("$.gstOnSalary").value(DEFAULT_GST_ON_SALARY))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED));
    }

    @Test
    @Transactional
    void getCourtCasesByIdFiltering() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        Long id = courtCase.getId();

        defaultCourtCaseShouldBeFound("id.equals=" + id);
        defaultCourtCaseShouldNotBeFound("id.notEquals=" + id);

        defaultCourtCaseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourtCaseShouldNotBeFound("id.greaterThan=" + id);

        defaultCourtCaseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourtCaseShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstName equals to DEFAULT_FIRST_NAME
        defaultCourtCaseShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the courtCaseList where firstName equals to UPDATED_FIRST_NAME
        defaultCourtCaseShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstName not equals to DEFAULT_FIRST_NAME
        defaultCourtCaseShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the courtCaseList where firstName not equals to UPDATED_FIRST_NAME
        defaultCourtCaseShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultCourtCaseShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the courtCaseList where firstName equals to UPDATED_FIRST_NAME
        defaultCourtCaseShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstName is not null
        defaultCourtCaseShouldBeFound("firstName.specified=true");

        // Get all the courtCaseList where firstName is null
        defaultCourtCaseShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstName contains DEFAULT_FIRST_NAME
        defaultCourtCaseShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the courtCaseList where firstName contains UPDATED_FIRST_NAME
        defaultCourtCaseShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where firstName does not contain DEFAULT_FIRST_NAME
        defaultCourtCaseShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the courtCaseList where firstName does not contain UPDATED_FIRST_NAME
        defaultCourtCaseShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastName equals to DEFAULT_LAST_NAME
        defaultCourtCaseShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the courtCaseList where lastName equals to UPDATED_LAST_NAME
        defaultCourtCaseShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastName not equals to DEFAULT_LAST_NAME
        defaultCourtCaseShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the courtCaseList where lastName not equals to UPDATED_LAST_NAME
        defaultCourtCaseShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultCourtCaseShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the courtCaseList where lastName equals to UPDATED_LAST_NAME
        defaultCourtCaseShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastName is not null
        defaultCourtCaseShouldBeFound("lastName.specified=true");

        // Get all the courtCaseList where lastName is null
        defaultCourtCaseShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastName contains DEFAULT_LAST_NAME
        defaultCourtCaseShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the courtCaseList where lastName contains UPDATED_LAST_NAME
        defaultCourtCaseShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastName does not contain DEFAULT_LAST_NAME
        defaultCourtCaseShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the courtCaseList where lastName does not contain UPDATED_LAST_NAME
        defaultCourtCaseShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address equals to DEFAULT_ADDRESS
        defaultCourtCaseShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the courtCaseList where address equals to UPDATED_ADDRESS
        defaultCourtCaseShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address not equals to DEFAULT_ADDRESS
        defaultCourtCaseShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the courtCaseList where address not equals to UPDATED_ADDRESS
        defaultCourtCaseShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCourtCaseShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the courtCaseList where address equals to UPDATED_ADDRESS
        defaultCourtCaseShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address is not null
        defaultCourtCaseShouldBeFound("address.specified=true");

        // Get all the courtCaseList where address is null
        defaultCourtCaseShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address contains DEFAULT_ADDRESS
        defaultCourtCaseShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the courtCaseList where address contains UPDATED_ADDRESS
        defaultCourtCaseShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where address does not contain DEFAULT_ADDRESS
        defaultCourtCaseShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the courtCaseList where address does not contain UPDATED_ADDRESS
        defaultCourtCaseShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where contactNo equals to DEFAULT_CONTACT_NO
        defaultCourtCaseShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the courtCaseList where contactNo equals to UPDATED_CONTACT_NO
        defaultCourtCaseShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultCourtCaseShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the courtCaseList where contactNo not equals to UPDATED_CONTACT_NO
        defaultCourtCaseShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultCourtCaseShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the courtCaseList where contactNo equals to UPDATED_CONTACT_NO
        defaultCourtCaseShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where contactNo is not null
        defaultCourtCaseShouldBeFound("contactNo.specified=true");

        // Get all the courtCaseList where contactNo is null
        defaultCourtCaseShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByContactNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where contactNo contains DEFAULT_CONTACT_NO
        defaultCourtCaseShouldBeFound("contactNo.contains=" + DEFAULT_CONTACT_NO);

        // Get all the courtCaseList where contactNo contains UPDATED_CONTACT_NO
        defaultCourtCaseShouldNotBeFound("contactNo.contains=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where contactNo does not contain DEFAULT_CONTACT_NO
        defaultCourtCaseShouldNotBeFound("contactNo.doesNotContain=" + DEFAULT_CONTACT_NO);

        // Get all the courtCaseList where contactNo does not contain UPDATED_CONTACT_NO
        defaultCourtCaseShouldBeFound("contactNo.doesNotContain=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultCourtCaseShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the courtCaseList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultCourtCaseShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultCourtCaseShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the courtCaseList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultCourtCaseShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultCourtCaseShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the courtCaseList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultCourtCaseShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where emailAddress is not null
        defaultCourtCaseShouldBeFound("emailAddress.specified=true");

        // Get all the courtCaseList where emailAddress is null
        defaultCourtCaseShouldNotBeFound("emailAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultCourtCaseShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the courtCaseList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultCourtCaseShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultCourtCaseShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the courtCaseList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultCourtCaseShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where salary equals to DEFAULT_SALARY
        defaultCourtCaseShouldBeFound("salary.equals=" + DEFAULT_SALARY);

        // Get all the courtCaseList where salary equals to UPDATED_SALARY
        defaultCourtCaseShouldNotBeFound("salary.equals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where salary not equals to DEFAULT_SALARY
        defaultCourtCaseShouldNotBeFound("salary.notEquals=" + DEFAULT_SALARY);

        // Get all the courtCaseList where salary not equals to UPDATED_SALARY
        defaultCourtCaseShouldBeFound("salary.notEquals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySalaryIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where salary in DEFAULT_SALARY or UPDATED_SALARY
        defaultCourtCaseShouldBeFound("salary.in=" + DEFAULT_SALARY + "," + UPDATED_SALARY);

        // Get all the courtCaseList where salary equals to UPDATED_SALARY
        defaultCourtCaseShouldNotBeFound("salary.in=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where salary is not null
        defaultCourtCaseShouldBeFound("salary.specified=true");

        // Get all the courtCaseList where salary is null
        defaultCourtCaseShouldNotBeFound("salary.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySalaryContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where salary contains DEFAULT_SALARY
        defaultCourtCaseShouldBeFound("salary.contains=" + DEFAULT_SALARY);

        // Get all the courtCaseList where salary contains UPDATED_SALARY
        defaultCourtCaseShouldNotBeFound("salary.contains=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySalaryNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where salary does not contain DEFAULT_SALARY
        defaultCourtCaseShouldNotBeFound("salary.doesNotContain=" + DEFAULT_SALARY);

        // Get all the courtCaseList where salary does not contain UPDATED_SALARY
        defaultCourtCaseShouldBeFound("salary.doesNotContain=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGstOnSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gstOnSalary equals to DEFAULT_GST_ON_SALARY
        defaultCourtCaseShouldBeFound("gstOnSalary.equals=" + DEFAULT_GST_ON_SALARY);

        // Get all the courtCaseList where gstOnSalary equals to UPDATED_GST_ON_SALARY
        defaultCourtCaseShouldNotBeFound("gstOnSalary.equals=" + UPDATED_GST_ON_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGstOnSalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gstOnSalary not equals to DEFAULT_GST_ON_SALARY
        defaultCourtCaseShouldNotBeFound("gstOnSalary.notEquals=" + DEFAULT_GST_ON_SALARY);

        // Get all the courtCaseList where gstOnSalary not equals to UPDATED_GST_ON_SALARY
        defaultCourtCaseShouldBeFound("gstOnSalary.notEquals=" + UPDATED_GST_ON_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGstOnSalaryIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gstOnSalary in DEFAULT_GST_ON_SALARY or UPDATED_GST_ON_SALARY
        defaultCourtCaseShouldBeFound("gstOnSalary.in=" + DEFAULT_GST_ON_SALARY + "," + UPDATED_GST_ON_SALARY);

        // Get all the courtCaseList where gstOnSalary equals to UPDATED_GST_ON_SALARY
        defaultCourtCaseShouldNotBeFound("gstOnSalary.in=" + UPDATED_GST_ON_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGstOnSalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gstOnSalary is not null
        defaultCourtCaseShouldBeFound("gstOnSalary.specified=true");

        // Get all the courtCaseList where gstOnSalary is null
        defaultCourtCaseShouldNotBeFound("gstOnSalary.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByGstOnSalaryContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gstOnSalary contains DEFAULT_GST_ON_SALARY
        defaultCourtCaseShouldBeFound("gstOnSalary.contains=" + DEFAULT_GST_ON_SALARY);

        // Get all the courtCaseList where gstOnSalary contains UPDATED_GST_ON_SALARY
        defaultCourtCaseShouldNotBeFound("gstOnSalary.contains=" + UPDATED_GST_ON_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByGstOnSalaryNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where gstOnSalary does not contain DEFAULT_GST_ON_SALARY
        defaultCourtCaseShouldNotBeFound("gstOnSalary.doesNotContain=" + DEFAULT_GST_ON_SALARY);

        // Get all the courtCaseList where gstOnSalary does not contain UPDATED_GST_ON_SALARY
        defaultCourtCaseShouldBeFound("gstOnSalary.doesNotContain=" + UPDATED_GST_ON_SALARY);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified is not null
        defaultCourtCaseShouldBeFound("lastModified.specified=true");

        // Get all the courtCaseList where lastModified is null
        defaultCourtCaseShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified contains UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCourtCasesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultCourtCaseShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the courtCaseList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultCourtCaseShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourtCaseShouldBeFound(String filter) throws Exception {
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY)))
            .andExpect(jsonPath("$.[*].gstOnSalary").value(hasItem(DEFAULT_GST_ON_SALARY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)));

        // Check, that the count call also returns 1
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourtCaseShouldNotBeFound(String filter) throws Exception {
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourtCase() throws Exception {
        // Get the courtCase
        restCourtCaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase
        CourtCase updatedCourtCase = courtCaseRepository.findById(courtCase.getId()).get();
        // Disconnect from session so that the updates on updatedCourtCase are not directly saved in db
        em.detach(updatedCourtCase);
        updatedCourtCase
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .contactNo(UPDATED_CONTACT_NO)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .salary(UPDATED_SALARY)
            .gstOnSalary(UPDATED_GST_ON_SALARY)
            .lastModified(UPDATED_LAST_MODIFIED);
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(updatedCourtCase);

        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courtCaseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCourtCase.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCourtCase.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCourtCase.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testCourtCase.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testCourtCase.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testCourtCase.getGstOnSalary()).isEqualTo(UPDATED_GST_ON_SALARY);
        assertThat(testCourtCase.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void putNonExistingCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courtCaseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCaseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourtCaseWithPatch() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase using partial update
        CourtCase partialUpdatedCourtCase = new CourtCase();
        partialUpdatedCourtCase.setId(courtCase.getId());

        partialUpdatedCourtCase.contactNo(UPDATED_CONTACT_NO);

        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCourtCase.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCourtCase.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCourtCase.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testCourtCase.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testCourtCase.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testCourtCase.getGstOnSalary()).isEqualTo(DEFAULT_GST_ON_SALARY);
        assertThat(testCourtCase.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void fullUpdateCourtCaseWithPatch() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase using partial update
        CourtCase partialUpdatedCourtCase = new CourtCase();
        partialUpdatedCourtCase.setId(courtCase.getId());

        partialUpdatedCourtCase
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .contactNo(UPDATED_CONTACT_NO)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .salary(UPDATED_SALARY)
            .gstOnSalary(UPDATED_GST_ON_SALARY)
            .lastModified(UPDATED_LAST_MODIFIED);

        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCourtCase.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCourtCase.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCourtCase.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testCourtCase.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testCourtCase.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testCourtCase.getGstOnSalary()).isEqualTo(UPDATED_GST_ON_SALARY);
        assertThat(testCourtCase.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courtCaseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // Create the CourtCase
        CourtCaseDTO courtCaseDTO = courtCaseMapper.toDto(courtCase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(courtCaseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeDelete = courtCaseRepository.findAll().size();

        // Delete the courtCase
        restCourtCaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, courtCase.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
