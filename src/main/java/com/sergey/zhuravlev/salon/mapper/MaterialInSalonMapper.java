package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.MaterialInSalon;
import com.sergey.zhuravlev.salon.dto.MaterialInSalonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialInSalonMapper {
    MaterialInSalonDto materialInSalonToMaterialInSalonDto(MaterialInSalon materialInSalon);
}
