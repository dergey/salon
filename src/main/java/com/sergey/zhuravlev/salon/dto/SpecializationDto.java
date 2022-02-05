package com.sergey.zhuravlev.salon.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationDto {

    private Long id;

    private String note;

    private EmployeeDto employee;

    private ServiceDto service;

}
