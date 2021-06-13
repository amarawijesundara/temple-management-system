package com.temple.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.temple.IntegrationTest;
import com.temple.domain.GuardianDetails;
import com.temple.domain.enumeration.ContactType;
import com.temple.repository.GuardianDetailsRepository;
import com.temple.service.dto.GuardianDetailsDTO;
import com.temple.service.mapper.GuardianDetailsMapper;
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
 * Integration tests for the {@link GuardianDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuardianDetailsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final ContactType DEFAULT_CONTACT_TYPE = ContactType.FATHER;
    private static final ContactType UPDATED_CONTACT_TYPE = ContactType.MOTHER;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/guardian-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GuardianDetailsRepository guardianDetailsRepository;

    @Autowired
    private GuardianDetailsMapper guardianDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGuardianDetailsMockMvc;

    private GuardianDetails guardianDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuardianDetails createEntity(EntityManager em) {
        GuardianDetails guardianDetails = new GuardianDetails()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .contactType(DEFAULT_CONTACT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return guardianDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuardianDetails createUpdatedEntity(EntityManager em) {
        GuardianDetails guardianDetails = new GuardianDetails()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .contactType(UPDATED_CONTACT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return guardianDetails;
    }

    @BeforeEach
    public void initTest() {
        guardianDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createGuardianDetails() throws Exception {
        int databaseSizeBeforeCreate = guardianDetailsRepository.findAll().size();
        // Create the GuardianDetails
        GuardianDetailsDTO guardianDetailsDTO = guardianDetailsMapper.toDto(guardianDetails);
        restGuardianDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guardianDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        GuardianDetails testGuardianDetails = guardianDetailsList.get(guardianDetailsList.size() - 1);
        assertThat(testGuardianDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGuardianDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testGuardianDetails.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
        assertThat(testGuardianDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testGuardianDetails.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createGuardianDetailsWithExistingId() throws Exception {
        // Create the GuardianDetails with an existing ID
        guardianDetails.setId(1L);
        GuardianDetailsDTO guardianDetailsDTO = guardianDetailsMapper.toDto(guardianDetails);

        int databaseSizeBeforeCreate = guardianDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuardianDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guardianDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGuardianDetails() throws Exception {
        // Initialize the database
        guardianDetailsRepository.saveAndFlush(guardianDetails);

        // Get all the guardianDetailsList
        restGuardianDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guardianDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getGuardianDetails() throws Exception {
        // Initialize the database
        guardianDetailsRepository.saveAndFlush(guardianDetails);

        // Get the guardianDetails
        restGuardianDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, guardianDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guardianDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.contactType").value(DEFAULT_CONTACT_TYPE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGuardianDetails() throws Exception {
        // Get the guardianDetails
        restGuardianDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGuardianDetails() throws Exception {
        // Initialize the database
        guardianDetailsRepository.saveAndFlush(guardianDetails);

        int databaseSizeBeforeUpdate = guardianDetailsRepository.findAll().size();

        // Update the guardianDetails
        GuardianDetails updatedGuardianDetails = guardianDetailsRepository.findById(guardianDetails.getId()).get();
        // Disconnect from session so that the updates on updatedGuardianDetails are not directly saved in db
        em.detach(updatedGuardianDetails);
        updatedGuardianDetails
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .contactType(UPDATED_CONTACT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        GuardianDetailsDTO guardianDetailsDTO = guardianDetailsMapper.toDto(updatedGuardianDetails);

        restGuardianDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guardianDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guardianDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeUpdate);
        GuardianDetails testGuardianDetails = guardianDetailsList.get(guardianDetailsList.size() - 1);
        assertThat(testGuardianDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuardianDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testGuardianDetails.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
        assertThat(testGuardianDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGuardianDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingGuardianDetails() throws Exception {
        int databaseSizeBeforeUpdate = guardianDetailsRepository.findAll().size();
        guardianDetails.setId(count.incrementAndGet());

        // Create the GuardianDetails
        GuardianDetailsDTO guardianDetailsDTO = guardianDetailsMapper.toDto(guardianDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuardianDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guardianDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guardianDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGuardianDetails() throws Exception {
        int databaseSizeBeforeUpdate = guardianDetailsRepository.findAll().size();
        guardianDetails.setId(count.incrementAndGet());

        // Create the GuardianDetails
        GuardianDetailsDTO guardianDetailsDTO = guardianDetailsMapper.toDto(guardianDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuardianDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guardianDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGuardianDetails() throws Exception {
        int databaseSizeBeforeUpdate = guardianDetailsRepository.findAll().size();
        guardianDetails.setId(count.incrementAndGet());

        // Create the GuardianDetails
        GuardianDetailsDTO guardianDetailsDTO = guardianDetailsMapper.toDto(guardianDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuardianDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guardianDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGuardianDetailsWithPatch() throws Exception {
        // Initialize the database
        guardianDetailsRepository.saveAndFlush(guardianDetails);

        int databaseSizeBeforeUpdate = guardianDetailsRepository.findAll().size();

        // Update the guardianDetails using partial update
        GuardianDetails partialUpdatedGuardianDetails = new GuardianDetails();
        partialUpdatedGuardianDetails.setId(guardianDetails.getId());

        partialUpdatedGuardianDetails.address(UPDATED_ADDRESS).updatedDate(UPDATED_UPDATED_DATE);

        restGuardianDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuardianDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuardianDetails))
            )
            .andExpect(status().isOk());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeUpdate);
        GuardianDetails testGuardianDetails = guardianDetailsList.get(guardianDetailsList.size() - 1);
        assertThat(testGuardianDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGuardianDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testGuardianDetails.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
        assertThat(testGuardianDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testGuardianDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateGuardianDetailsWithPatch() throws Exception {
        // Initialize the database
        guardianDetailsRepository.saveAndFlush(guardianDetails);

        int databaseSizeBeforeUpdate = guardianDetailsRepository.findAll().size();

        // Update the guardianDetails using partial update
        GuardianDetails partialUpdatedGuardianDetails = new GuardianDetails();
        partialUpdatedGuardianDetails.setId(guardianDetails.getId());

        partialUpdatedGuardianDetails
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .contactType(UPDATED_CONTACT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restGuardianDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuardianDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuardianDetails))
            )
            .andExpect(status().isOk());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeUpdate);
        GuardianDetails testGuardianDetails = guardianDetailsList.get(guardianDetailsList.size() - 1);
        assertThat(testGuardianDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuardianDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testGuardianDetails.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
        assertThat(testGuardianDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGuardianDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingGuardianDetails() throws Exception {
        int databaseSizeBeforeUpdate = guardianDetailsRepository.findAll().size();
        guardianDetails.setId(count.incrementAndGet());

        // Create the GuardianDetails
        GuardianDetailsDTO guardianDetailsDTO = guardianDetailsMapper.toDto(guardianDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuardianDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guardianDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guardianDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGuardianDetails() throws Exception {
        int databaseSizeBeforeUpdate = guardianDetailsRepository.findAll().size();
        guardianDetails.setId(count.incrementAndGet());

        // Create the GuardianDetails
        GuardianDetailsDTO guardianDetailsDTO = guardianDetailsMapper.toDto(guardianDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuardianDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guardianDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGuardianDetails() throws Exception {
        int databaseSizeBeforeUpdate = guardianDetailsRepository.findAll().size();
        guardianDetails.setId(count.incrementAndGet());

        // Create the GuardianDetails
        GuardianDetailsDTO guardianDetailsDTO = guardianDetailsMapper.toDto(guardianDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuardianDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guardianDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuardianDetails in the database
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGuardianDetails() throws Exception {
        // Initialize the database
        guardianDetailsRepository.saveAndFlush(guardianDetails);

        int databaseSizeBeforeDelete = guardianDetailsRepository.findAll().size();

        // Delete the guardianDetails
        restGuardianDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, guardianDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GuardianDetails> guardianDetailsList = guardianDetailsRepository.findAll();
        assertThat(guardianDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
