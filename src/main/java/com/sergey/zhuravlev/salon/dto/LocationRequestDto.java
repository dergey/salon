package com.sergey.zhuravlev.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestDto {

    @NotNull
    private String address;

    private String postalCode;

    @NotNull
    private String city;

    private String stateProvince;

    private String countryCode;

}
