package com.temple.service.mapper;

import com.temple.domain.*;
import com.temple.service.dto.GuardianDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GuardianDetails} and its DTO {@link GuardianDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonalDetailsMapper.class })
public interface GuardianDetailsMapper extends EntityMapper<GuardianDetailsDTO, GuardianDetails> {
    @Mapping(target = "personalDetails", source = "personalDetails", qualifiedByName = "id")
    GuardianDetailsDTO toDto(GuardianDetails s);
}
