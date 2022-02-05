package com.sergey.zhuravlev.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

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

    private Long managerId;

    private Long salonId;

}
