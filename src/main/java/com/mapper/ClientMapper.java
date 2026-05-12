package com.mapper;

import com.dto.Client;
import com.dto.UserInfo;
import com.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    @Mapping(target = "address", source = "address")
    @Mapping(target = "role", source = "role")
    Client toDto(ClientEntity entity);

    @Mapping(target = "address", source = "address")
    @Mapping(target = "role", source = "role")
    ClientEntity toEntity(Client client);

    UserInfo toAllInfo(ClientEntity entity);

}
