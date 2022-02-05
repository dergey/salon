package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.Client;
import com.sergey.zhuravlev.salon.dto.ClientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto clientToClientDto(Client client);
}
