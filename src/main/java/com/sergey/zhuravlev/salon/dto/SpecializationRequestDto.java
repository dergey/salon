package com.sergey.zhuravlev.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationRequestDto {

    private String note;

    private Long employeeId;

    private Long serviceId;

}
