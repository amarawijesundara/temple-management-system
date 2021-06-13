package com.temple.service.impl;

import com.temple.domain.PersonalDetails;
import com.temple.repository.PersonalDetailsRepository;
import com.temple.service.PersonalDetailsService;
import com.temple.service.dto.PersonalDetailsDTO;
import com.temple.service.mapper.PersonalDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonalDetails}.
 */
@Service
@Transactional
public class PersonalDetailsServiceImpl implements PersonalDetailsService {

    private final Logger log = LoggerFactory.getLogger(PersonalDetailsServiceImpl.class);

    private final PersonalDetailsRepository personalDetailsRepository;

    private final PersonalDetailsMapper personalDetailsMapper;

    public PersonalDetailsServiceImpl(PersonalDetailsRepository personalDetailsRepository, PersonalDetailsMapper personalDetailsMapper) {
        this.personalDetailsRepository = personalDetailsRepository;
        this.personalDetailsMapper = personalDetailsMapper;
    }

    @Override
    public PersonalDetailsDTO save(PersonalDetailsDTO personalDetailsDTO) {
        log.debug("Request to save PersonalDetails : {}", personalDetailsDTO);
        PersonalDetails personalDetails = personalDetailsMapper.toEntity(personalDetailsDTO);
        personalDetails = personalDetailsRepository.save(personalDetails);
        return personalDetailsMapper.toDto(personalDetails);
    }

    @Override
    public Optional<PersonalDetailsDTO> partialUpdate(PersonalDetailsDTO personalDetailsDTO) {
        log.debug("Request to partially update PersonalDetails : {}", personalDetailsDTO);

        return personalDetailsRepository
            .findById(personalDetailsDTO.getId())
            .map(
                existingPersonalDetails -> {
                    personalDetailsMapper.partialUpdate(existingPersonalDetails, personalDetailsDTO);
                    return existingPersonalDetails;
                }
            )
            .map(personalDetailsRepository::save)
            .map(personalDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonalDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonalDetails");
        return personalDetailsRepository.findAll(pageable).map(personalDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalDetailsDTO> findOne(Long id) {
        log.debug("Request to get PersonalDetails : {}", id);
        return personalDetailsRepository.findById(id).map(personalDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonalDetails : {}", id);
        personalDetailsRepository.deleteById(id);
    }
}
