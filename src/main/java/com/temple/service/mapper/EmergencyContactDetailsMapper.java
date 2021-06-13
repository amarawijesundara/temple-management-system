package com.temple.service.mapper;

import com.temple.domain.*;
import com.temple.service.dto.EmergencyContactDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmergencyContactDetails} and its DTO {@link EmergencyContactDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonalDetailsMapper.class })
public interface EmergencyContactDetailsMapper extends EntityMapper<EmergencyContactDetailsDTO, EmergencyContactDetails> {
    @Mapping(target = "personalDetails", source = "personalDetails", qualifiedByName = "id")
    EmergencyContactDetailsDTO toDto(EmergencyContactDetails s);
}
