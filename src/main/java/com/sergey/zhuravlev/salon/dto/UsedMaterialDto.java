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
public class UsedMaterialDto {

    private Long id;

    @NotNull
    private Integer count;

    private Boolean decommission;

    private OrderDto order;

    private MaterialDto material;

    private EmployeeDto employee;

}
