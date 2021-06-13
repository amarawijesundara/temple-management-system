package com.temple.web.rest;

import com.temple.repository.EmergencyContactDetailsRepository;
import com.temple.service.EmergencyContactDetailsService;
import com.temple.service.dto.EmergencyContactDetailsDTO;
import com.temple.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.temple.domain.EmergencyContactDetails}.
 */
@RestController
@RequestMapping("/api")
public class EmergencyContactDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EmergencyContactDetailsResource.class);

    private static final String ENTITY_NAME = "emergencyContactDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmergencyContactDetailsService emergencyContactDetailsService;

    private final EmergencyContactDetailsRepository emergencyContactDetailsRepository;

    public EmergencyContactDetailsResource(
        EmergencyContactDetailsService emergencyContactDetailsService,
        EmergencyContactDetailsRepository emergencyContactDetailsRepository
    ) {
        this.emergencyContactDetailsService = emergencyContactDetailsService;
        this.emergencyContactDetailsRepository = emergencyContactDetailsRepository;
    }

    /**
     * {@code POST  /emergency-contact-details} : Create a new emergencyContactDetails.
     *
     * @param emergencyContactDetailsDTO the emergencyContactDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emergencyContactDetailsDTO, or with status {@code 400 (Bad Request)} if the emergencyContactDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emergency-contact-details")
    public ResponseEntity<EmergencyContactDetailsDTO> createEmergencyContactDetails(
        @RequestBody EmergencyContactDetailsDTO emergencyContactDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EmergencyContactDetails : {}", emergencyContactDetailsDTO);
        if (emergencyContactDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new emergencyContactDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmergencyContactDetailsDTO result = emergencyContactDetailsService.save(emergencyContactDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/emergency-contact-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emergency-contact-details/:id} : Updates an existing emergencyContactDetails.
     *
     * @param id the id of the emergencyContactDetailsDTO to save.
     * @param emergencyContactDetailsDTO the emergencyContactDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emergencyContactDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the emergencyContactDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emergencyContactDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emergency-contact-details/{id}")
    public ResponseEntity<EmergencyContactDetailsDTO> updateEmergencyContactDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmergencyContactDetailsDTO emergencyContactDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmergencyContactDetails : {}, {}", id, emergencyContactDetailsDTO);
        if (emergencyContactDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emergencyContactDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emergencyContactDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmergencyContactDetailsDTO result = emergencyContactDetailsService.save(emergencyContactDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emergencyContactDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emergency-contact-details/:id} : Partial updates given fields of an existing emergencyContactDetails, field will ignore if it is null
     *
     * @param id the id of the emergencyContactDetailsDTO to save.
     * @param emergencyContactDetailsDTO the emergencyContactDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emergencyContactDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the emergencyContactDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the emergencyContactDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the emergencyContactDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/emergency-contact-details/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EmergencyContactDetailsDTO> partialUpdateEmergencyContactDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmergencyContactDetailsDTO emergencyContactDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmergencyContactDetails partially : {}, {}", id, emergencyContactDetailsDTO);
        if (emergencyContactDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emergencyContactDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emergencyContactDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmergencyContactDetailsDTO> result = emergencyContactDetailsService.partialUpdate(emergencyContactDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emergencyContactDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /emergency-contact-details} : get all the emergencyContactDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emergencyContactDetails in body.
     */
    @GetMapping("/emergency-contact-details")
    public List<EmergencyContactDetailsDTO> getAllEmergencyContactDetails() {
        log.debug("REST request to get all EmergencyContactDetails");
        return emergencyContactDetailsService.findAll();
    }

    /**
     * {@code GET  /emergency-contact-details/:id} : get the "id" emergencyContactDetails.
     *
     * @param id the id of the emergencyContactDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emergencyContactDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emergency-contact-details/{id}")
    public ResponseEntity<EmergencyContactDetailsDTO> getEmergencyContactDetails(@PathVariable Long id) {
        log.debug("REST request to get EmergencyContactDetails : {}", id);
        Optional<EmergencyContactDetailsDTO> emergencyContactDetailsDTO = emergencyContactDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emergencyContactDetailsDTO);
    }

    /**
     * {@code DELETE  /emergency-contact-details/:id} : delete the "id" emergencyContactDetails.
     *
     * @param id the id of the emergencyContactDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emergency-contact-details/{id}")
    public ResponseEntity<Void> deleteEmergencyContactDetails(@PathVariable Long id) {
        log.debug("REST request to delete EmergencyContactDetails : {}", id);
        emergencyContactDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
