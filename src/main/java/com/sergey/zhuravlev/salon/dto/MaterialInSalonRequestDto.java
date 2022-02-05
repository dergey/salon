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
public class MaterialInSalonRequestDto {

    @NotNull
    private Integer count;

    private Long materialId;

    private Long salonId;

}
