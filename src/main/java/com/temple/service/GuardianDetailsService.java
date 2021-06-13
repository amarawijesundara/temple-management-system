package com.temple.service;

import com.temple.service.dto.GuardianDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.temple.domain.GuardianDetails}.
 */
public interface GuardianDetailsService {
    /**
     * Save a guardianDetails.
     *
     * @param guardianDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    GuardianDetailsDTO save(GuardianDetailsDTO guardianDetailsDTO);

    /**
     * Partially updates a guardianDetails.
     *
     * @param guardianDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GuardianDetailsDTO> partialUpdate(GuardianDetailsDTO guardianDetailsDTO);

    /**
     * Get all the guardianDetails.
     *
     * @return the list of entities.
     */
    List<GuardianDetailsDTO> findAll();

    /**
     * Get the "id" guardianDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GuardianDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" guardianDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
