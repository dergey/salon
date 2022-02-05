package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import com.sergey.zhuravlev.salon.dto.ServiceProvidedDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceProvidedMapper {
    ServiceProvidedDto serviceProvidedToServiceProvidedDto(ServiceProvided serviceProvided);
}
