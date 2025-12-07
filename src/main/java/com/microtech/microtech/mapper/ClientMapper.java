package com.microtech.microtech.mapper;

import com.microtech.microtech.dto.request.auth.CreateClientRequest;
import com.microtech.microtech.dto.request.auth.UpdateClientRequest;
import com.microtech.microtech.dto.response.auth.ClientResponse;
import com.microtech.microtech.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(CreateClientRequest dto);
    ClientResponse toResponse(Client client);
    void updateEntityFromDto(UpdateClientRequest dto, @MappingTarget Client client);
}
