package com.temple.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.temple.IntegrationTest;
import com.temple.domain.EmergencyContactDetails;
import com.temple.domain.enumeration.ContactType;
import com.temple.repository.EmergencyContactDetailsRepository;
import com.temple.service.dto.EmergencyContactDetailsDTO;
import com.temple.service.mapper.EmergencyContactDetailsMapper;
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
 * Integration tests for the {@link EmergencyContactDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmergencyContactDetailsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEPHONE = 1;
    private static final Integer UPDATED_TELEPHONE = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final ContactType DEFAULT_CONTACT_TYPE = ContactType.FATHER;
    private static final ContactType UPDATED_CONTACT_TYPE = ContactType.MOTHER;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/emergency-contact-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmergencyContactDetailsRepository emergencyContactDetailsRepository;

    @Autowired
    private EmergencyContactDetailsMapper emergencyContactDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmergencyContactDetailsMockMvc;

    private EmergencyContactDetails emergencyContactDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmergencyContactDetails createEntity(EntityManager em) {
        EmergencyContactDetails emergencyContactDetails = new EmergencyContactDetails()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .contactType(DEFAULT_CONTACT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return emergencyContactDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmergencyContactDetails createUpdatedEntity(EntityManager em) {
        EmergencyContactDetails emergencyContactDetails = new EmergencyContactDetails()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .contactType(UPDATED_CONTACT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return emergencyContactDetails;
    }

    @BeforeEach
    public void initTest() {
        emergencyContactDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createEmergencyContactDetails() throws Exception {
        int databaseSizeBeforeCreate = emergencyContactDetailsRepository.findAll().size();
        // Create the EmergencyContactDetails
        EmergencyContactDetailsDTO emergencyContactDetailsDTO = emergencyContactDetailsMapper.toDto(emergencyContactDetails);
        restEmergencyContactDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EmergencyContactDetails testEmergencyContactDetails = emergencyContactDetailsList.get(emergencyContactDetailsList.size() - 1);
        assertThat(testEmergencyContactDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmergencyContactDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEmergencyContactDetails.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEmergencyContactDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmergencyContactDetails.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
        assertThat(testEmergencyContactDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmergencyContactDetails.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createEmergencyContactDetailsWithExistingId() throws Exception {
        // Create the EmergencyContactDetails with an existing ID
        emergencyContactDetails.setId(1L);
        EmergencyContactDetailsDTO emergencyContactDetailsDTO = emergencyContactDetailsMapper.toDto(emergencyContactDetails);

        int databaseSizeBeforeCreate = emergencyContactDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmergencyContactDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmergencyContactDetails() throws Exception {
        // Initialize the database
        emergencyContactDetailsRepository.saveAndFlush(emergencyContactDetails);

        // Get all the emergencyContactDetailsList
        restEmergencyContactDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencyContactDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getEmergencyContactDetails() throws Exception {
        // Initialize the database
        emergencyContactDetailsRepository.saveAndFlush(emergencyContactDetails);

        // Get the emergencyContactDetails
        restEmergencyContactDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, emergencyContactDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emergencyContactDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.contactType").value(DEFAULT_CONTACT_TYPE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEmergencyContactDetails() throws Exception {
        // Get the emergencyContactDetails
        restEmergencyContactDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmergencyContactDetails() throws Exception {
        // Initialize the database
        emergencyContactDetailsRepository.saveAndFlush(emergencyContactDetails);

        int databaseSizeBeforeUpdate = emergencyContactDetailsRepository.findAll().size();

        // Update the emergencyContactDetails
        EmergencyContactDetails updatedEmergencyContactDetails = emergencyContactDetailsRepository
            .findById(emergencyContactDetails.getId())
            .get();
        // Disconnect from session so that the updates on updatedEmergencyContactDetails are not directly saved in db
        em.detach(updatedEmergencyContactDetails);
        updatedEmergencyContactDetails
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .contactType(UPDATED_CONTACT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        EmergencyContactDetailsDTO emergencyContactDetailsDTO = emergencyContactDetailsMapper.toDto(updatedEmergencyContactDetails);

        restEmergencyContactDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emergencyContactDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmergencyContactDetails testEmergencyContactDetails = emergencyContactDetailsList.get(emergencyContactDetailsList.size() - 1);
        assertThat(testEmergencyContactDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmergencyContactDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEmergencyContactDetails.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEmergencyContactDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmergencyContactDetails.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
        assertThat(testEmergencyContactDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmergencyContactDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingEmergencyContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = emergencyContactDetailsRepository.findAll().size();
        emergencyContactDetails.setId(count.incrementAndGet());

        // Create the EmergencyContactDetails
        EmergencyContactDetailsDTO emergencyContactDetailsDTO = emergencyContactDetailsMapper.toDto(emergencyContactDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmergencyContactDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emergencyContactDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmergencyContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = emergencyContactDetailsRepository.findAll().size();
        emergencyContactDetails.setId(count.incrementAndGet());

        // Create the EmergencyContactDetails
        EmergencyContactDetailsDTO emergencyContactDetailsDTO = emergencyContactDetailsMapper.toDto(emergencyContactDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmergencyContactDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmergencyContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = emergencyContactDetailsRepository.findAll().size();
        emergencyContactDetails.setId(count.incrementAndGet());

        // Create the EmergencyContactDetails
        EmergencyContactDetailsDTO emergencyContactDetailsDTO = emergencyContactDetailsMapper.toDto(emergencyContactDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmergencyContactDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmergencyContactDetailsWithPatch() throws Exception {
        // Initialize the database
        emergencyContactDetailsRepository.saveAndFlush(emergencyContactDetails);

        int databaseSizeBeforeUpdate = emergencyContactDetailsRepository.findAll().size();

        // Update the emergencyContactDetails using partial update
        EmergencyContactDetails partialUpdatedEmergencyContactDetails = new EmergencyContactDetails();
        partialUpdatedEmergencyContactDetails.setId(emergencyContactDetails.getId());

        partialUpdatedEmergencyContactDetails.address(UPDATED_ADDRESS).email(UPDATED_EMAIL).updatedDate(UPDATED_UPDATED_DATE);

        restEmergencyContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmergencyContactDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmergencyContactDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmergencyContactDetails testEmergencyContactDetails = emergencyContactDetailsList.get(emergencyContactDetailsList.size() - 1);
        assertThat(testEmergencyContactDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmergencyContactDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEmergencyContactDetails.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEmergencyContactDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmergencyContactDetails.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
        assertThat(testEmergencyContactDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmergencyContactDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEmergencyContactDetailsWithPatch() throws Exception {
        // Initialize the database
        emergencyContactDetailsRepository.saveAndFlush(emergencyContactDetails);

        int databaseSizeBeforeUpdate = emergencyContactDetailsRepository.findAll().size();

        // Update the emergencyContactDetails using partial update
        EmergencyContactDetails partialUpdatedEmergencyContactDetails = new EmergencyContactDetails();
        partialUpdatedEmergencyContactDetails.setId(emergencyContactDetails.getId());

        partialUpdatedEmergencyContactDetails
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .contactType(UPDATED_CONTACT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restEmergencyContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmergencyContactDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmergencyContactDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmergencyContactDetails testEmergencyContactDetails = emergencyContactDetailsList.get(emergencyContactDetailsList.size() - 1);
        assertThat(testEmergencyContactDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmergencyContactDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEmergencyContactDetails.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEmergencyContactDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmergencyContactDetails.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
        assertThat(testEmergencyContactDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmergencyContactDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingEmergencyContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = emergencyContactDetailsRepository.findAll().size();
        emergencyContactDetails.setId(count.incrementAndGet());

        // Create the EmergencyContactDetails
        EmergencyContactDetailsDTO emergencyContactDetailsDTO = emergencyContactDetailsMapper.toDto(emergencyContactDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmergencyContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emergencyContactDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmergencyContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = emergencyContactDetailsRepository.findAll().size();
        emergencyContactDetails.setId(count.incrementAndGet());

        // Create the EmergencyContactDetails
        EmergencyContactDetailsDTO emergencyContactDetailsDTO = emergencyContactDetailsMapper.toDto(emergencyContactDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmergencyContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmergencyContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = emergencyContactDetailsRepository.findAll().size();
        emergencyContactDetails.setId(count.incrementAndGet());

        // Create the EmergencyContactDetails
        EmergencyContactDetailsDTO emergencyContactDetailsDTO = emergencyContactDetailsMapper.toDto(emergencyContactDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmergencyContactDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emergencyContactDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmergencyContactDetails in the database
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmergencyContactDetails() throws Exception {
        // Initialize the database
        emergencyContactDetailsRepository.saveAndFlush(emergencyContactDetails);

        int databaseSizeBeforeDelete = emergencyContactDetailsRepository.findAll().size();

        // Delete the emergencyContactDetails
        restEmergencyContactDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, emergencyContactDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmergencyContactDetails> emergencyContactDetailsList = emergencyContactDetailsRepository.findAll();
        assertThat(emergencyContactDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
