package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.Material;
import com.sergey.zhuravlev.salon.dto.MaterialDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper {
    MaterialDto materialToMaterialDto(Material material);
}
