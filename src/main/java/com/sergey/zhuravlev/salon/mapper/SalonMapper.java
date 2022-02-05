package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.Salon;
import com.sergey.zhuravlev.salon.dto.SalonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalonMapper {
    SalonDto salonToSalonDto(Salon salon);
}
