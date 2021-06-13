package com.temple.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.temple.IntegrationTest;
import com.temple.domain.PersonalDetails;
import com.temple.domain.enumeration.Ethnicity;
import com.temple.domain.enumeration.Nationality;
import com.temple.repository.PersonalDetailsRepository;
import com.temple.service.dto.PersonalDetailsDTO;
import com.temple.service.mapper.PersonalDetailsMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PersonalDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonalDetailsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_NIC = "AAAAAAAAAA";
    private static final String UPDATED_NIC = "BBBBBBBBBB";

    private static final Nationality DEFAULT_NATIONALITY = Nationality.SRILANKAN;
    private static final Nationality UPDATED_NATIONALITY = Nationality.INDIAN;

    private static final Ethnicity DEFAULT_ETHNICITY = Ethnicity.SINHALESE;
    private static final Ethnicity UPDATED_ETHNICITY = Ethnicity.TAMIL;

    private static final String DEFAULT_PASSPORT = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TELEPHONE = 1;
    private static final Integer UPDATED_TELEPHONE = 2;

    private static final Integer DEFAULT_MOBILE = 1;
    private static final Integer UPDATED_MOBILE = 2;

    private static final Boolean DEFAULT_IS_BIKSHU = false;
    private static final Boolean UPDATED_IS_BIKSHU = true;

    private static final Boolean DEFAULT_IS_ANAGARIKA = false;
    private static final Boolean UPDATED_IS_ANAGARIKA = true;

    private static final Boolean DEFAULT_IS_UPASAKA = false;
    private static final Boolean UPDATED_IS_UPASAKA = true;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/personal-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    @Autowired
    private PersonalDetailsMapper personalDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonalDetailsMockMvc;

    private PersonalDetails personalDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalDetails createEntity(EntityManager em) {
        PersonalDetails personalDetails = new PersonalDetails()
            .name(DEFAULT_NAME)
            .gender(DEFAULT_GENDER)
            .nic(DEFAULT_NIC)
            .nationality(DEFAULT_NATIONALITY)
            .ethnicity(DEFAULT_ETHNICITY)
            .passport(DEFAULT_PASSPORT)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .telephone(DEFAULT_TELEPHONE)
            .mobile(DEFAULT_MOBILE)
            .isBikshu(DEFAULT_IS_BIKSHU)
            .isAnagarika(DEFAULT_IS_ANAGARIKA)
            .isUpasaka(DEFAULT_IS_UPASAKA)
            .notes(DEFAULT_NOTES)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return personalDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalDetails createUpdatedEntity(EntityManager em) {
        PersonalDetails personalDetails = new PersonalDetails()
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .nic(UPDATED_NIC)
            .nationality(UPDATED_NATIONALITY)
            .ethnicity(UPDATED_ETHNICITY)
            .passport(UPDATED_PASSPORT)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .isBikshu(UPDATED_IS_BIKSHU)
            .isAnagarika(UPDATED_IS_ANAGARIKA)
            .isUpasaka(UPDATED_IS_UPASAKA)
            .notes(UPDATED_NOTES)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return personalDetails;
    }

    @BeforeEach
    public void initTest() {
        personalDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonalDetails() throws Exception {
        int databaseSizeBeforeCreate = personalDetailsRepository.findAll().size();
        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);
        restPersonalDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PersonalDetails testPersonalDetails = personalDetailsList.get(personalDetailsList.size() - 1);
        assertThat(testPersonalDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonalDetails.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPersonalDetails.getNic()).isEqualTo(DEFAULT_NIC);
        assertThat(testPersonalDetails.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPersonalDetails.getEthnicity()).isEqualTo(DEFAULT_ETHNICITY);
        assertThat(testPersonalDetails.getPassport()).isEqualTo(DEFAULT_PASSPORT);
        assertThat(testPersonalDetails.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPersonalDetails.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testPersonalDetails.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testPersonalDetails.getIsBikshu()).isEqualTo(DEFAULT_IS_BIKSHU);
        assertThat(testPersonalDetails.getIsAnagarika()).isEqualTo(DEFAULT_IS_ANAGARIKA);
        assertThat(testPersonalDetails.getIsUpasaka()).isEqualTo(DEFAULT_IS_UPASAKA);
        assertThat(testPersonalDetails.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testPersonalDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPersonalDetails.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createPersonalDetailsWithExistingId() throws Exception {
        // Create the PersonalDetails with an existing ID
        personalDetails.setId(1L);
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        int databaseSizeBeforeCreate = personalDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonalDetails() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        // Get all the personalDetailsList
        restPersonalDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].nic").value(hasItem(DEFAULT_NIC)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].ethnicity").value(hasItem(DEFAULT_ETHNICITY.toString())))
            .andExpect(jsonPath("$.[*].passport").value(hasItem(DEFAULT_PASSPORT)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].isBikshu").value(hasItem(DEFAULT_IS_BIKSHU.booleanValue())))
            .andExpect(jsonPath("$.[*].isAnagarika").value(hasItem(DEFAULT_IS_ANAGARIKA.booleanValue())))
            .andExpect(jsonPath("$.[*].isUpasaka").value(hasItem(DEFAULT_IS_UPASAKA.booleanValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPersonalDetails() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        // Get the personalDetails
        restPersonalDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, personalDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personalDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.nic").value(DEFAULT_NIC))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.ethnicity").value(DEFAULT_ETHNICITY.toString()))
            .andExpect(jsonPath("$.passport").value(DEFAULT_PASSPORT))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.isBikshu").value(DEFAULT_IS_BIKSHU.booleanValue()))
            .andExpect(jsonPath("$.isAnagarika").value(DEFAULT_IS_ANAGARIKA.booleanValue()))
            .andExpect(jsonPath("$.isUpasaka").value(DEFAULT_IS_UPASAKA.booleanValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPersonalDetails() throws Exception {
        // Get the personalDetails
        restPersonalDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonalDetails() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();

        // Update the personalDetails
        PersonalDetails updatedPersonalDetails = personalDetailsRepository.findById(personalDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPersonalDetails are not directly saved in db
        em.detach(updatedPersonalDetails);
        updatedPersonalDetails
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .nic(UPDATED_NIC)
            .nationality(UPDATED_NATIONALITY)
            .ethnicity(UPDATED_ETHNICITY)
            .passport(UPDATED_PASSPORT)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .isBikshu(UPDATED_IS_BIKSHU)
            .isAnagarika(UPDATED_IS_ANAGARIKA)
            .isUpasaka(UPDATED_IS_UPASAKA)
            .notes(UPDATED_NOTES)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(updatedPersonalDetails);

        restPersonalDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
        PersonalDetails testPersonalDetails = personalDetailsList.get(personalDetailsList.size() - 1);
        assertThat(testPersonalDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonalDetails.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPersonalDetails.getNic()).isEqualTo(UPDATED_NIC);
        assertThat(testPersonalDetails.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPersonalDetails.getEthnicity()).isEqualTo(UPDATED_ETHNICITY);
        assertThat(testPersonalDetails.getPassport()).isEqualTo(UPDATED_PASSPORT);
        assertThat(testPersonalDetails.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPersonalDetails.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testPersonalDetails.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPersonalDetails.getIsBikshu()).isEqualTo(UPDATED_IS_BIKSHU);
        assertThat(testPersonalDetails.getIsAnagarika()).isEqualTo(UPDATED_IS_ANAGARIKA);
        assertThat(testPersonalDetails.getIsUpasaka()).isEqualTo(UPDATED_IS_UPASAKA);
        assertThat(testPersonalDetails.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPersonalDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPersonalDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonalDetailsWithPatch() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();

        // Update the personalDetails using partial update
        PersonalDetails partialUpdatedPersonalDetails = new PersonalDetails();
        partialUpdatedPersonalDetails.setId(personalDetails.getId());

        partialUpdatedPersonalDetails
            .gender(UPDATED_GENDER)
            .ethnicity(UPDATED_ETHNICITY)
            .passport(UPDATED_PASSPORT)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .mobile(UPDATED_MOBILE)
            .isUpasaka(UPDATED_IS_UPASAKA)
            .notes(UPDATED_NOTES)
            .createdDate(UPDATED_CREATED_DATE);

        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonalDetails))
            )
            .andExpect(status().isOk());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
        PersonalDetails testPersonalDetails = personalDetailsList.get(personalDetailsList.size() - 1);
        assertThat(testPersonalDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonalDetails.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPersonalDetails.getNic()).isEqualTo(DEFAULT_NIC);
        assertThat(testPersonalDetails.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPersonalDetails.getEthnicity()).isEqualTo(UPDATED_ETHNICITY);
        assertThat(testPersonalDetails.getPassport()).isEqualTo(UPDATED_PASSPORT);
        assertThat(testPersonalDetails.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPersonalDetails.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testPersonalDetails.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPersonalDetails.getIsBikshu()).isEqualTo(DEFAULT_IS_BIKSHU);
        assertThat(testPersonalDetails.getIsAnagarika()).isEqualTo(DEFAULT_IS_ANAGARIKA);
        assertThat(testPersonalDetails.getIsUpasaka()).isEqualTo(UPDATED_IS_UPASAKA);
        assertThat(testPersonalDetails.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPersonalDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPersonalDetails.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePersonalDetailsWithPatch() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();

        // Update the personalDetails using partial update
        PersonalDetails partialUpdatedPersonalDetails = new PersonalDetails();
        partialUpdatedPersonalDetails.setId(personalDetails.getId());

        partialUpdatedPersonalDetails
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .nic(UPDATED_NIC)
            .nationality(UPDATED_NATIONALITY)
            .ethnicity(UPDATED_ETHNICITY)
            .passport(UPDATED_PASSPORT)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .isBikshu(UPDATED_IS_BIKSHU)
            .isAnagarika(UPDATED_IS_ANAGARIKA)
            .isUpasaka(UPDATED_IS_UPASAKA)
            .notes(UPDATED_NOTES)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonalDetails))
            )
            .andExpect(status().isOk());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
        PersonalDetails testPersonalDetails = personalDetailsList.get(personalDetailsList.size() - 1);
        assertThat(testPersonalDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonalDetails.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPersonalDetails.getNic()).isEqualTo(UPDATED_NIC);
        assertThat(testPersonalDetails.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPersonalDetails.getEthnicity()).isEqualTo(UPDATED_ETHNICITY);
        assertThat(testPersonalDetails.getPassport()).isEqualTo(UPDATED_PASSPORT);
        assertThat(testPersonalDetails.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPersonalDetails.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testPersonalDetails.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPersonalDetails.getIsBikshu()).isEqualTo(UPDATED_IS_BIKSHU);
        assertThat(testPersonalDetails.getIsAnagarika()).isEqualTo(UPDATED_IS_ANAGARIKA);
        assertThat(testPersonalDetails.getIsUpasaka()).isEqualTo(UPDATED_IS_UPASAKA);
        assertThat(testPersonalDetails.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPersonalDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPersonalDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personalDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonalDetails() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        int databaseSizeBeforeDelete = personalDetailsRepository.findAll().size();

        // Delete the personalDetails
        restPersonalDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, personalDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
