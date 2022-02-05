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
public class MaterialInSalonDto {

    private Long id;

    @NotNull
    private Integer count;

    private MaterialDto material;

    private SalonDto salon;

}
