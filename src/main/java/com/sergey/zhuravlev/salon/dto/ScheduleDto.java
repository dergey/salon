package com.sergey.zhuravlev.salon.dto;

import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;

@ApiModel(description = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private Instant startDate;

    private Instant endDate;

    private Map<DayOfWeek, Collection<ServiceProvided>> schedule;

}
