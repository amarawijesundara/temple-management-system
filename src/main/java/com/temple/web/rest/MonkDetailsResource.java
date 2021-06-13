package com.temple.web.rest;

import com.temple.repository.MonkDetailsRepository;
import com.temple.service.MonkDetailsService;
import com.temple.service.dto.MonkDetailsDTO;
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
 * REST controller for managing {@link com.temple.domain.MonkDetails}.
 */
@RestController
@RequestMapping("/api")
public class MonkDetailsResource {

    private final Logger log = LoggerFactory.getLogger(MonkDetailsResource.class);

    private static final String ENTITY_NAME = "monkDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonkDetailsService monkDetailsService;

    private final MonkDetailsRepository monkDetailsRepository;

    public MonkDetailsResource(MonkDetailsService monkDetailsService, MonkDetailsRepository monkDetailsRepository) {
        this.monkDetailsService = monkDetailsService;
        this.monkDetailsRepository = monkDetailsRepository;
    }

    /**
     * {@code POST  /monk-details} : Create a new monkDetails.
     *
     * @param monkDetailsDTO the monkDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monkDetailsDTO, or with status {@code 400 (Bad Request)} if the monkDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monk-details")
    public ResponseEntity<MonkDetailsDTO> createMonkDetails(@RequestBody MonkDetailsDTO monkDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save MonkDetails : {}", monkDetailsDTO);
        if (monkDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new monkDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonkDetailsDTO result = monkDetailsService.save(monkDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/monk-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monk-details/:id} : Updates an existing monkDetails.
     *
     * @param id the id of the monkDetailsDTO to save.
     * @param monkDetailsDTO the monkDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monkDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the monkDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monkDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monk-details/{id}")
    public ResponseEntity<MonkDetailsDTO> updateMonkDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MonkDetailsDTO monkDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MonkDetails : {}, {}", id, monkDetailsDTO);
        if (monkDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monkDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monkDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MonkDetailsDTO result = monkDetailsService.save(monkDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monkDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /monk-details/:id} : Partial updates given fields of an existing monkDetails, field will ignore if it is null
     *
     * @param id the id of the monkDetailsDTO to save.
     * @param monkDetailsDTO the monkDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monkDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the monkDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the monkDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the monkDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/monk-details/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MonkDetailsDTO> partialUpdateMonkDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MonkDetailsDTO monkDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MonkDetails partially : {}, {}", id, monkDetailsDTO);
        if (monkDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monkDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monkDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MonkDetailsDTO> result = monkDetailsService.partialUpdate(monkDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monkDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /monk-details} : get all the monkDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monkDetails in body.
     */
    @GetMapping("/monk-details")
    public List<MonkDetailsDTO> getAllMonkDetails() {
        log.debug("REST request to get all MonkDetails");
        return monkDetailsService.findAll();
    }

    /**
     * {@code GET  /monk-details/:id} : get the "id" monkDetails.
     *
     * @param id the id of the monkDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monkDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monk-details/{id}")
    public ResponseEntity<MonkDetailsDTO> getMonkDetails(@PathVariable Long id) {
        log.debug("REST request to get MonkDetails : {}", id);
        Optional<MonkDetailsDTO> monkDetailsDTO = monkDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monkDetailsDTO);
    }

    /**
     * {@code DELETE  /monk-details/:id} : delete the "id" monkDetails.
     *
     * @param id the id of the monkDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monk-details/{id}")
    public ResponseEntity<Void> deleteMonkDetails(@PathVariable Long id) {
        log.debug("REST request to delete MonkDetails : {}", id);
        monkDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
