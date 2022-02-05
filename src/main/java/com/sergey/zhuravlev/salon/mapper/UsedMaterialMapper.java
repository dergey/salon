package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.UsedMaterial;
import com.sergey.zhuravlev.salon.dto.UsedMaterialDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsedMaterialMapper {
    UsedMaterialDto usedMaterialToUsedMaterialDto(UsedMaterial usedMaterial);
}
