package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.Service;
import com.sergey.zhuravlev.salon.dto.ServiceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDto serviceToServiceDto(Service service);
}
