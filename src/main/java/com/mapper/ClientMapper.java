package com.mapper;

import com.dto.Client;
import com.dto.UserInfo;
import com.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    Client toDto(ClientEntity entity);

    ClientEntity toEntity(Client client);

    UserInfo toAllInfo(ClientEntity entity);

}
