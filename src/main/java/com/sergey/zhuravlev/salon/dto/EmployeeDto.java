package com.sergey.zhuravlev.salon.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@ApiModel(description = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Instant hireDate;

    @NotNull
    private BigDecimal salary;

    @NotNull
    private BigDecimal commissionPct;

    private EmployeeDto manager;

    private SalonDto salon;

}
