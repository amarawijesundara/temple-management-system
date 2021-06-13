package com.temple.service.mapper;

import com.temple.domain.*;
import com.temple.service.dto.MonkDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MonkDetails} and its DTO {@link MonkDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MonkDetailsMapper extends EntityMapper<MonkDetailsDTO, MonkDetails> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MonkDetailsDTO toDtoId(MonkDetails monkDetails);
}
