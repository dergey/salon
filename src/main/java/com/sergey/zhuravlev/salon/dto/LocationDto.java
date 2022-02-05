package com.sergey.zhuravlev.salon.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private Long id;

    @NotNull
    private String address;

    private String postalCode;

    @NotNull
    private String city;

    private String stateProvince;

    private CountryDto country;

}
