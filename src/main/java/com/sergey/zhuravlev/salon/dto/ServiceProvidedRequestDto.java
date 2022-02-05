package com.sergey.zhuravlev.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProvidedRequestDto {

    private Instant startDate;

    private Instant endDate;

    private String note;

    private Long orderId;

    private Long employeeId;

    private Long serviceId;

}
