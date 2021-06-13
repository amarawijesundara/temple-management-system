package com.temple.web.rest;

import com.temple.repository.GuardianDetailsRepository;
import com.temple.service.GuardianDetailsService;
import com.temple.service.dto.GuardianDetailsDTO;
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
 * REST controller for managing {@link com.temple.domain.GuardianDetails}.
 */
@RestController
@RequestMapping("/api")
public class GuardianDetailsResource {

    private final Logger log = LoggerFactory.getLogger(GuardianDetailsResource.class);

    private static final String ENTITY_NAME = "guardianDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuardianDetailsService guardianDetailsService;

    private final GuardianDetailsRepository guardianDetailsRepository;

    public GuardianDetailsResource(GuardianDetailsService guardianDetailsService, GuardianDetailsRepository guardianDetailsRepository) {
        this.guardianDetailsService = guardianDetailsService;
        this.guardianDetailsRepository = guardianDetailsRepository;
    }

    /**
     * {@code POST  /guardian-details} : Create a new guardianDetails.
     *
     * @param guardianDetailsDTO the guardianDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guardianDetailsDTO, or with status {@code 400 (Bad Request)} if the guardianDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/guardian-details")
    public ResponseEntity<GuardianDetailsDTO> createGuardianDetails(@RequestBody GuardianDetailsDTO guardianDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save GuardianDetails : {}", guardianDetailsDTO);
        if (guardianDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new guardianDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuardianDetailsDTO result = guardianDetailsService.save(guardianDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/guardian-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /guardian-details/:id} : Updates an existing guardianDetails.
     *
     * @param id the id of the guardianDetailsDTO to save.
     * @param guardianDetailsDTO the guardianDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guardianDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the guardianDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guardianDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/guardian-details/{id}")
    public ResponseEntity<GuardianDetailsDTO> updateGuardianDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GuardianDetailsDTO guardianDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GuardianDetails : {}, {}", id, guardianDetailsDTO);
        if (guardianDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guardianDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guardianDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GuardianDetailsDTO result = guardianDetailsService.save(guardianDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guardianDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /guardian-details/:id} : Partial updates given fields of an existing guardianDetails, field will ignore if it is null
     *
     * @param id the id of the guardianDetailsDTO to save.
     * @param guardianDetailsDTO the guardianDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guardianDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the guardianDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the guardianDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the guardianDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/guardian-details/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GuardianDetailsDTO> partialUpdateGuardianDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GuardianDetailsDTO guardianDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GuardianDetails partially : {}, {}", id, guardianDetailsDTO);
        if (guardianDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guardianDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guardianDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GuardianDetailsDTO> result = guardianDetailsService.partialUpdate(guardianDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guardianDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /guardian-details} : get all the guardianDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guardianDetails in body.
     */
    @GetMapping("/guardian-details")
    public List<GuardianDetailsDTO> getAllGuardianDetails() {
        log.debug("REST request to get all GuardianDetails");
        return guardianDetailsService.findAll();
    }

    /**
     * {@code GET  /guardian-details/:id} : get the "id" guardianDetails.
     *
     * @param id the id of the guardianDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guardianDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/guardian-details/{id}")
    public ResponseEntity<GuardianDetailsDTO> getGuardianDetails(@PathVariable Long id) {
        log.debug("REST request to get GuardianDetails : {}", id);
        Optional<GuardianDetailsDTO> guardianDetailsDTO = guardianDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guardianDetailsDTO);
    }

    /**
     * {@code DELETE  /guardian-details/:id} : delete the "id" guardianDetails.
     *
     * @param id the id of the guardianDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/guardian-details/{id}")
    public ResponseEntity<Void> deleteGuardianDetails(@PathVariable Long id) {
        log.debug("REST request to delete GuardianDetails : {}", id);
        guardianDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
