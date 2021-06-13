package com.temple.service;

import com.temple.service.dto.EmergencyContactDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.temple.domain.EmergencyContactDetails}.
 */
public interface EmergencyContactDetailsService {
    /**
     * Save a emergencyContactDetails.
     *
     * @param emergencyContactDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    EmergencyContactDetailsDTO save(EmergencyContactDetailsDTO emergencyContactDetailsDTO);

    /**
     * Partially updates a emergencyContactDetails.
     *
     * @param emergencyContactDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmergencyContactDetailsDTO> partialUpdate(EmergencyContactDetailsDTO emergencyContactDetailsDTO);

    /**
     * Get all the emergencyContactDetails.
     *
     * @return the list of entities.
     */
    List<EmergencyContactDetailsDTO> findAll();

    /**
     * Get the "id" emergencyContactDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmergencyContactDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" emergencyContactDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
