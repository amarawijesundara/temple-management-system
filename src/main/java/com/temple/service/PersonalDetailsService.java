package com.temple.service;

import com.temple.service.dto.PersonalDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.temple.domain.PersonalDetails}.
 */
public interface PersonalDetailsService {
    /**
     * Save a personalDetails.
     *
     * @param personalDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    PersonalDetailsDTO save(PersonalDetailsDTO personalDetailsDTO);

    /**
     * Partially updates a personalDetails.
     *
     * @param personalDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonalDetailsDTO> partialUpdate(PersonalDetailsDTO personalDetailsDTO);

    /**
     * Get all the personalDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonalDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" personalDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonalDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" personalDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
