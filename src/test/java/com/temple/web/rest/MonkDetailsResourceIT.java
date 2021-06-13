package com.temple.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.temple.IntegrationTest;
import com.temple.domain.MonkDetails;
import com.temple.repository.MonkDetailsRepository;
import com.temple.service.dto.MonkDetailsDTO;
import com.temple.service.mapper.MonkDetailsMapper;
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
 * Integration tests for the {@link MonkDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MonkDetailsResourceIT {

    private static final String DEFAULT_NAME_ENGLISH = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ENGLISH = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_SINHALA = "AAAAAAAAAA";
    private static final String UPDATED_NAME_SINHALA = "BBBBBBBBBB";

    private static final String DEFAULT_PAWIDI_NO = "AAAAAAAAAA";
    private static final String UPDATED_PAWIDI_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SAMANERA_NO = "AAAAAAAAAA";
    private static final String UPDATED_SAMANERA_NO = "BBBBBBBBBB";

    private static final String DEFAULT_UPASAMPADA_NO = "AAAAAAAAAA";
    private static final String UPDATED_UPASAMPADA_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAWIDI_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAWIDI_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPASAMPADA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPASAMPADA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/monk-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MonkDetailsRepository monkDetailsRepository;

    @Autowired
    private MonkDetailsMapper monkDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonkDetailsMockMvc;

    private MonkDetails monkDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonkDetails createEntity(EntityManager em) {
        MonkDetails monkDetails = new MonkDetails()
            .nameEnglish(DEFAULT_NAME_ENGLISH)
            .nameSinhala(DEFAULT_NAME_SINHALA)
            .pawidiNo(DEFAULT_PAWIDI_NO)
            .samaneraNo(DEFAULT_SAMANERA_NO)
            .upasampadaNo(DEFAULT_UPASAMPADA_NO)
            .pawidiDate(DEFAULT_PAWIDI_DATE)
            .upasampadaDate(DEFAULT_UPASAMPADA_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return monkDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonkDetails createUpdatedEntity(EntityManager em) {
        MonkDetails monkDetails = new MonkDetails()
            .nameEnglish(UPDATED_NAME_ENGLISH)
            .nameSinhala(UPDATED_NAME_SINHALA)
            .pawidiNo(UPDATED_PAWIDI_NO)
            .samaneraNo(UPDATED_SAMANERA_NO)
            .upasampadaNo(UPDATED_UPASAMPADA_NO)
            .pawidiDate(UPDATED_PAWIDI_DATE)
            .upasampadaDate(UPDATED_UPASAMPADA_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return monkDetails;
    }

    @BeforeEach
    public void initTest() {
        monkDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createMonkDetails() throws Exception {
        int databaseSizeBeforeCreate = monkDetailsRepository.findAll().size();
        // Create the MonkDetails
        MonkDetailsDTO monkDetailsDTO = monkDetailsMapper.toDto(monkDetails);
        restMonkDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monkDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        MonkDetails testMonkDetails = monkDetailsList.get(monkDetailsList.size() - 1);
        assertThat(testMonkDetails.getNameEnglish()).isEqualTo(DEFAULT_NAME_ENGLISH);
        assertThat(testMonkDetails.getNameSinhala()).isEqualTo(DEFAULT_NAME_SINHALA);
        assertThat(testMonkDetails.getPawidiNo()).isEqualTo(DEFAULT_PAWIDI_NO);
        assertThat(testMonkDetails.getSamaneraNo()).isEqualTo(DEFAULT_SAMANERA_NO);
        assertThat(testMonkDetails.getUpasampadaNo()).isEqualTo(DEFAULT_UPASAMPADA_NO);
        assertThat(testMonkDetails.getPawidiDate()).isEqualTo(DEFAULT_PAWIDI_DATE);
        assertThat(testMonkDetails.getUpasampadaDate()).isEqualTo(DEFAULT_UPASAMPADA_DATE);
        assertThat(testMonkDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMonkDetails.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createMonkDetailsWithExistingId() throws Exception {
        // Create the MonkDetails with an existing ID
        monkDetails.setId(1L);
        MonkDetailsDTO monkDetailsDTO = monkDetailsMapper.toDto(monkDetails);

        int databaseSizeBeforeCreate = monkDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonkDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monkDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMonkDetails() throws Exception {
        // Initialize the database
        monkDetailsRepository.saveAndFlush(monkDetails);

        // Get all the monkDetailsList
        restMonkDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monkDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEnglish").value(hasItem(DEFAULT_NAME_ENGLISH)))
            .andExpect(jsonPath("$.[*].nameSinhala").value(hasItem(DEFAULT_NAME_SINHALA)))
            .andExpect(jsonPath("$.[*].pawidiNo").value(hasItem(DEFAULT_PAWIDI_NO)))
            .andExpect(jsonPath("$.[*].samaneraNo").value(hasItem(DEFAULT_SAMANERA_NO)))
            .andExpect(jsonPath("$.[*].upasampadaNo").value(hasItem(DEFAULT_UPASAMPADA_NO)))
            .andExpect(jsonPath("$.[*].pawidiDate").value(hasItem(DEFAULT_PAWIDI_DATE.toString())))
            .andExpect(jsonPath("$.[*].upasampadaDate").value(hasItem(DEFAULT_UPASAMPADA_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getMonkDetails() throws Exception {
        // Initialize the database
        monkDetailsRepository.saveAndFlush(monkDetails);

        // Get the monkDetails
        restMonkDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, monkDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monkDetails.getId().intValue()))
            .andExpect(jsonPath("$.nameEnglish").value(DEFAULT_NAME_ENGLISH))
            .andExpect(jsonPath("$.nameSinhala").value(DEFAULT_NAME_SINHALA))
            .andExpect(jsonPath("$.pawidiNo").value(DEFAULT_PAWIDI_NO))
            .andExpect(jsonPath("$.samaneraNo").value(DEFAULT_SAMANERA_NO))
            .andExpect(jsonPath("$.upasampadaNo").value(DEFAULT_UPASAMPADA_NO))
            .andExpect(jsonPath("$.pawidiDate").value(DEFAULT_PAWIDI_DATE.toString()))
            .andExpect(jsonPath("$.upasampadaDate").value(DEFAULT_UPASAMPADA_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMonkDetails() throws Exception {
        // Get the monkDetails
        restMonkDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMonkDetails() throws Exception {
        // Initialize the database
        monkDetailsRepository.saveAndFlush(monkDetails);

        int databaseSizeBeforeUpdate = monkDetailsRepository.findAll().size();

        // Update the monkDetails
        MonkDetails updatedMonkDetails = monkDetailsRepository.findById(monkDetails.getId()).get();
        // Disconnect from session so that the updates on updatedMonkDetails are not directly saved in db
        em.detach(updatedMonkDetails);
        updatedMonkDetails
            .nameEnglish(UPDATED_NAME_ENGLISH)
            .nameSinhala(UPDATED_NAME_SINHALA)
            .pawidiNo(UPDATED_PAWIDI_NO)
            .samaneraNo(UPDATED_SAMANERA_NO)
            .upasampadaNo(UPDATED_UPASAMPADA_NO)
            .pawidiDate(UPDATED_PAWIDI_DATE)
            .upasampadaDate(UPDATED_UPASAMPADA_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        MonkDetailsDTO monkDetailsDTO = monkDetailsMapper.toDto(updatedMonkDetails);

        restMonkDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, monkDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monkDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeUpdate);
        MonkDetails testMonkDetails = monkDetailsList.get(monkDetailsList.size() - 1);
        assertThat(testMonkDetails.getNameEnglish()).isEqualTo(UPDATED_NAME_ENGLISH);
        assertThat(testMonkDetails.getNameSinhala()).isEqualTo(UPDATED_NAME_SINHALA);
        assertThat(testMonkDetails.getPawidiNo()).isEqualTo(UPDATED_PAWIDI_NO);
        assertThat(testMonkDetails.getSamaneraNo()).isEqualTo(UPDATED_SAMANERA_NO);
        assertThat(testMonkDetails.getUpasampadaNo()).isEqualTo(UPDATED_UPASAMPADA_NO);
        assertThat(testMonkDetails.getPawidiDate()).isEqualTo(UPDATED_PAWIDI_DATE);
        assertThat(testMonkDetails.getUpasampadaDate()).isEqualTo(UPDATED_UPASAMPADA_DATE);
        assertThat(testMonkDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMonkDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMonkDetails() throws Exception {
        int databaseSizeBeforeUpdate = monkDetailsRepository.findAll().size();
        monkDetails.setId(count.incrementAndGet());

        // Create the MonkDetails
        MonkDetailsDTO monkDetailsDTO = monkDetailsMapper.toDto(monkDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonkDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, monkDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monkDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMonkDetails() throws Exception {
        int databaseSizeBeforeUpdate = monkDetailsRepository.findAll().size();
        monkDetails.setId(count.incrementAndGet());

        // Create the MonkDetails
        MonkDetailsDTO monkDetailsDTO = monkDetailsMapper.toDto(monkDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonkDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monkDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMonkDetails() throws Exception {
        int databaseSizeBeforeUpdate = monkDetailsRepository.findAll().size();
        monkDetails.setId(count.incrementAndGet());

        // Create the MonkDetails
        MonkDetailsDTO monkDetailsDTO = monkDetailsMapper.toDto(monkDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonkDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monkDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMonkDetailsWithPatch() throws Exception {
        // Initialize the database
        monkDetailsRepository.saveAndFlush(monkDetails);

        int databaseSizeBeforeUpdate = monkDetailsRepository.findAll().size();

        // Update the monkDetails using partial update
        MonkDetails partialUpdatedMonkDetails = new MonkDetails();
        partialUpdatedMonkDetails.setId(monkDetails.getId());

        partialUpdatedMonkDetails
            .pawidiNo(UPDATED_PAWIDI_NO)
            .samaneraNo(UPDATED_SAMANERA_NO)
            .pawidiDate(UPDATED_PAWIDI_DATE)
            .upasampadaDate(UPDATED_UPASAMPADA_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restMonkDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonkDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonkDetails))
            )
            .andExpect(status().isOk());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeUpdate);
        MonkDetails testMonkDetails = monkDetailsList.get(monkDetailsList.size() - 1);
        assertThat(testMonkDetails.getNameEnglish()).isEqualTo(DEFAULT_NAME_ENGLISH);
        assertThat(testMonkDetails.getNameSinhala()).isEqualTo(DEFAULT_NAME_SINHALA);
        assertThat(testMonkDetails.getPawidiNo()).isEqualTo(UPDATED_PAWIDI_NO);
        assertThat(testMonkDetails.getSamaneraNo()).isEqualTo(UPDATED_SAMANERA_NO);
        assertThat(testMonkDetails.getUpasampadaNo()).isEqualTo(DEFAULT_UPASAMPADA_NO);
        assertThat(testMonkDetails.getPawidiDate()).isEqualTo(UPDATED_PAWIDI_DATE);
        assertThat(testMonkDetails.getUpasampadaDate()).isEqualTo(UPDATED_UPASAMPADA_DATE);
        assertThat(testMonkDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMonkDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMonkDetailsWithPatch() throws Exception {
        // Initialize the database
        monkDetailsRepository.saveAndFlush(monkDetails);

        int databaseSizeBeforeUpdate = monkDetailsRepository.findAll().size();

        // Update the monkDetails using partial update
        MonkDetails partialUpdatedMonkDetails = new MonkDetails();
        partialUpdatedMonkDetails.setId(monkDetails.getId());

        partialUpdatedMonkDetails
            .nameEnglish(UPDATED_NAME_ENGLISH)
            .nameSinhala(UPDATED_NAME_SINHALA)
            .pawidiNo(UPDATED_PAWIDI_NO)
            .samaneraNo(UPDATED_SAMANERA_NO)
            .upasampadaNo(UPDATED_UPASAMPADA_NO)
            .pawidiDate(UPDATED_PAWIDI_DATE)
            .upasampadaDate(UPDATED_UPASAMPADA_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restMonkDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonkDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonkDetails))
            )
            .andExpect(status().isOk());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeUpdate);
        MonkDetails testMonkDetails = monkDetailsList.get(monkDetailsList.size() - 1);
        assertThat(testMonkDetails.getNameEnglish()).isEqualTo(UPDATED_NAME_ENGLISH);
        assertThat(testMonkDetails.getNameSinhala()).isEqualTo(UPDATED_NAME_SINHALA);
        assertThat(testMonkDetails.getPawidiNo()).isEqualTo(UPDATED_PAWIDI_NO);
        assertThat(testMonkDetails.getSamaneraNo()).isEqualTo(UPDATED_SAMANERA_NO);
        assertThat(testMonkDetails.getUpasampadaNo()).isEqualTo(UPDATED_UPASAMPADA_NO);
        assertThat(testMonkDetails.getPawidiDate()).isEqualTo(UPDATED_PAWIDI_DATE);
        assertThat(testMonkDetails.getUpasampadaDate()).isEqualTo(UPDATED_UPASAMPADA_DATE);
        assertThat(testMonkDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMonkDetails.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMonkDetails() throws Exception {
        int databaseSizeBeforeUpdate = monkDetailsRepository.findAll().size();
        monkDetails.setId(count.incrementAndGet());

        // Create the MonkDetails
        MonkDetailsDTO monkDetailsDTO = monkDetailsMapper.toDto(monkDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonkDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, monkDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monkDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMonkDetails() throws Exception {
        int databaseSizeBeforeUpdate = monkDetailsRepository.findAll().size();
        monkDetails.setId(count.incrementAndGet());

        // Create the MonkDetails
        MonkDetailsDTO monkDetailsDTO = monkDetailsMapper.toDto(monkDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonkDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monkDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMonkDetails() throws Exception {
        int databaseSizeBeforeUpdate = monkDetailsRepository.findAll().size();
        monkDetails.setId(count.incrementAndGet());

        // Create the MonkDetails
        MonkDetailsDTO monkDetailsDTO = monkDetailsMapper.toDto(monkDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonkDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(monkDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonkDetails in the database
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMonkDetails() throws Exception {
        // Initialize the database
        monkDetailsRepository.saveAndFlush(monkDetails);

        int databaseSizeBeforeDelete = monkDetailsRepository.findAll().size();

        // Delete the monkDetails
        restMonkDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, monkDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MonkDetails> monkDetailsList = monkDetailsRepository.findAll();
        assertThat(monkDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
