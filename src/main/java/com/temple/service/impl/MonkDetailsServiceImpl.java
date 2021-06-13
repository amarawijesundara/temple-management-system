package com.temple.service.impl;

import com.temple.domain.MonkDetails;
import com.temple.repository.MonkDetailsRepository;
import com.temple.service.MonkDetailsService;
import com.temple.service.dto.MonkDetailsDTO;
import com.temple.service.mapper.MonkDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MonkDetails}.
 */
@Service
@Transactional
public class MonkDetailsServiceImpl implements MonkDetailsService {

    private final Logger log = LoggerFactory.getLogger(MonkDetailsServiceImpl.class);

    private final MonkDetailsRepository monkDetailsRepository;

    private final MonkDetailsMapper monkDetailsMapper;

    public MonkDetailsServiceImpl(MonkDetailsRepository monkDetailsRepository, MonkDetailsMapper monkDetailsMapper) {
        this.monkDetailsRepository = monkDetailsRepository;
        this.monkDetailsMapper = monkDetailsMapper;
    }

    @Override
    public MonkDetailsDTO save(MonkDetailsDTO monkDetailsDTO) {
        log.debug("Request to save MonkDetails : {}", monkDetailsDTO);
        MonkDetails monkDetails = monkDetailsMapper.toEntity(monkDetailsDTO);
        monkDetails = monkDetailsRepository.save(monkDetails);
        return monkDetailsMapper.toDto(monkDetails);
    }

    @Override
    public Optional<MonkDetailsDTO> partialUpdate(MonkDetailsDTO monkDetailsDTO) {
        log.debug("Request to partially update MonkDetails : {}", monkDetailsDTO);

        return monkDetailsRepository
            .findById(monkDetailsDTO.getId())
            .map(
                existingMonkDetails -> {
                    monkDetailsMapper.partialUpdate(existingMonkDetails, monkDetailsDTO);
                    return existingMonkDetails;
                }
            )
            .map(monkDetailsRepository::save)
            .map(monkDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonkDetailsDTO> findAll() {
        log.debug("Request to get all MonkDetails");
        return monkDetailsRepository.findAll().stream().map(monkDetailsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MonkDetailsDTO> findOne(Long id) {
        log.debug("Request to get MonkDetails : {}", id);
        return monkDetailsRepository.findById(id).map(monkDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MonkDetails : {}", id);
        monkDetailsRepository.deleteById(id);
    }
}
