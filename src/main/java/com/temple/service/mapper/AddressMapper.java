package com.temple.service.mapper;

import com.temple.domain.*;
import com.temple.service.dto.AddressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonalDetailsMapper.class })
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "personalDetails", source = "personalDetails", qualifiedByName = "id")
    AddressDTO toDto(Address s);
}
