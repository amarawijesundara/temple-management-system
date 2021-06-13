package com.temple.service.impl;

import com.temple.domain.EmergencyContactDetails;
import com.temple.repository.EmergencyContactDetailsRepository;
import com.temple.service.EmergencyContactDetailsService;
import com.temple.service.dto.EmergencyContactDetailsDTO;
import com.temple.service.mapper.EmergencyContactDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmergencyContactDetails}.
 */
@Service
@Transactional
public class EmergencyContactDetailsServiceImpl implements EmergencyContactDetailsService {

    private final Logger log = LoggerFactory.getLogger(EmergencyContactDetailsServiceImpl.class);

    private final EmergencyContactDetailsRepository emergencyContactDetailsRepository;

    private final EmergencyContactDetailsMapper emergencyContactDetailsMapper;

    public EmergencyContactDetailsServiceImpl(
        EmergencyContactDetailsRepository emergencyContactDetailsRepository,
        EmergencyContactDetailsMapper emergencyContactDetailsMapper
    ) {
        this.emergencyContactDetailsRepository = emergencyContactDetailsRepository;
        this.emergencyContactDetailsMapper = emergencyContactDetailsMapper;
    }

    @Override
    public EmergencyContactDetailsDTO save(EmergencyContactDetailsDTO emergencyContactDetailsDTO) {
        log.debug("Request to save EmergencyContactDetails : {}", emergencyContactDetailsDTO);
        EmergencyContactDetails emergencyContactDetails = emergencyContactDetailsMapper.toEntity(emergencyContactDetailsDTO);
        emergencyContactDetails = emergencyContactDetailsRepository.save(emergencyContactDetails);
        return emergencyContactDetailsMapper.toDto(emergencyContactDetails);
    }

    @Override
    public Optional<EmergencyContactDetailsDTO> partialUpdate(EmergencyContactDetailsDTO emergencyContactDetailsDTO) {
        log.debug("Request to partially update EmergencyContactDetails : {}", emergencyContactDetailsDTO);

        return emergencyContactDetailsRepository
            .findById(emergencyContactDetailsDTO.getId())
            .map(
                existingEmergencyContactDetails -> {
                    emergencyContactDetailsMapper.partialUpdate(existingEmergencyContactDetails, emergencyContactDetailsDTO);
                    return existingEmergencyContactDetails;
                }
            )
            .map(emergencyContactDetailsRepository::save)
            .map(emergencyContactDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmergencyContactDetailsDTO> findAll() {
        log.debug("Request to get all EmergencyContactDetails");
        return emergencyContactDetailsRepository
            .findAll()
            .stream()
            .map(emergencyContactDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmergencyContactDetailsDTO> findOne(Long id) {
        log.debug("Request to get EmergencyContactDetails : {}", id);
        return emergencyContactDetailsRepository.findById(id).map(emergencyContactDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmergencyContactDetails : {}", id);
        emergencyContactDetailsRepository.deleteById(id);
    }
}
