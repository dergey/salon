package com.sergey.zhuravlev.salon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CountryCreateDto extends CountryRequestDto {

    @NotNull
    private String code;

}
