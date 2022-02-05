package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.Specialization;
import com.sergey.zhuravlev.salon.dto.SpecializationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {
    SpecializationDto specializationToSpecializationDto(Specialization specialization);
}
