package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.Location;
import com.sergey.zhuravlev.salon.dto.LocationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDto locationToLocationDto(Location location);
}
