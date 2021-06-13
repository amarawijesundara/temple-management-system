package com.temple.service.impl;

import com.temple.domain.GuardianDetails;
import com.temple.repository.GuardianDetailsRepository;
import com.temple.service.GuardianDetailsService;
import com.temple.service.dto.GuardianDetailsDTO;
import com.temple.service.mapper.GuardianDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GuardianDetails}.
 */
@Service
@Transactional
public class GuardianDetailsServiceImpl implements GuardianDetailsService {

    private final Logger log = LoggerFactory.getLogger(GuardianDetailsServiceImpl.class);

    private final GuardianDetailsRepository guardianDetailsRepository;

    private final GuardianDetailsMapper guardianDetailsMapper;

    public GuardianDetailsServiceImpl(GuardianDetailsRepository guardianDetailsRepository, GuardianDetailsMapper guardianDetailsMapper) {
        this.guardianDetailsRepository = guardianDetailsRepository;
        this.guardianDetailsMapper = guardianDetailsMapper;
    }

    @Override
    public GuardianDetailsDTO save(GuardianDetailsDTO guardianDetailsDTO) {
        log.debug("Request to save GuardianDetails : {}", guardianDetailsDTO);
        GuardianDetails guardianDetails = guardianDetailsMapper.toEntity(guardianDetailsDTO);
        guardianDetails = guardianDetailsRepository.save(guardianDetails);
        return guardianDetailsMapper.toDto(guardianDetails);
    }

    @Override
    public Optional<GuardianDetailsDTO> partialUpdate(GuardianDetailsDTO guardianDetailsDTO) {
        log.debug("Request to partially update GuardianDetails : {}", guardianDetailsDTO);

        return guardianDetailsRepository
            .findById(guardianDetailsDTO.getId())
            .map(
                existingGuardianDetails -> {
                    guardianDetailsMapper.partialUpdate(existingGuardianDetails, guardianDetailsDTO);
                    return existingGuardianDetails;
                }
            )
            .map(guardianDetailsRepository::save)
            .map(guardianDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuardianDetailsDTO> findAll() {
        log.debug("Request to get all GuardianDetails");
        return guardianDetailsRepository
            .findAll()
            .stream()
            .map(guardianDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GuardianDetailsDTO> findOne(Long id) {
        log.debug("Request to get GuardianDetails : {}", id);
        return guardianDetailsRepository.findById(id).map(guardianDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GuardianDetails : {}", id);
        guardianDetailsRepository.deleteById(id);
    }
}
