package com.sergey.zhuravlev.salon.service.dto;

import com.sergey.zhuravlev.salon.domain.ServiceProvided;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;

public class ScheduleDTO {

    private Instant startDate;

    private Instant endDate;

    private Map<DayOfWeek, Collection<ServiceProvided>> schedule;

    public ScheduleDTO() {
    }

    public ScheduleDTO(Instant startDate, Instant endDate, Map<DayOfWeek, Collection<ServiceProvided>> schedule) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.schedule = schedule;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Map<DayOfWeek, Collection<ServiceProvided>> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<DayOfWeek, Collection<ServiceProvided>> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
            "startDate=" + startDate +
            ", endDate=" + endDate +
            ", schedule=" + schedule +
            '}';
    }
}
