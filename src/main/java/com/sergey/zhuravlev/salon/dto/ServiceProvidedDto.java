package com.sergey.zhuravlev.salon.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@ApiModel(description = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProvidedDto {

    private Long id;

    private Instant startDate;

    private Instant endDate;

    private String note;

    private OrderDto order;

    private EmployeeDto employee;

    private ServiceDto service;

}
