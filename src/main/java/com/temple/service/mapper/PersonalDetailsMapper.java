package com.temple.service.mapper;

import com.temple.domain.*;
import com.temple.service.dto.PersonalDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalDetails} and its DTO {@link PersonalDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { MonkDetailsMapper.class })
public interface PersonalDetailsMapper extends EntityMapper<PersonalDetailsDTO, PersonalDetails> {
    @Mapping(target = "monkDetails", source = "monkDetails", qualifiedByName = "id")
    PersonalDetailsDTO toDto(PersonalDetails s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalDetailsDTO toDtoId(PersonalDetails personalDetails);
}
