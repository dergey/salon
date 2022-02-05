package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.Country;
import com.sergey.zhuravlev.salon.dto.CountryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDto countryToCountryDto(Country country);
}
