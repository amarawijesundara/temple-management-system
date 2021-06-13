package com.temple.service;

import com.temple.service.dto.MonkDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.temple.domain.MonkDetails}.
 */
public interface MonkDetailsService {
    /**
     * Save a monkDetails.
     *
     * @param monkDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    MonkDetailsDTO save(MonkDetailsDTO monkDetailsDTO);

    /**
     * Partially updates a monkDetails.
     *
     * @param monkDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MonkDetailsDTO> partialUpdate(MonkDetailsDTO monkDetailsDTO);

    /**
     * Get all the monkDetails.
     *
     * @return the list of entities.
     */
    List<MonkDetailsDTO> findAll();

    /**
     * Get the "id" monkDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MonkDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" monkDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
