package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.domain.Salon;
import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import com.sergey.zhuravlev.salon.dto.ScheduleDto;
import com.sergey.zhuravlev.salon.repository.EmployeeRepository;
import com.sergey.zhuravlev.salon.repository.ServiceProvidedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ServiceProvidedRepository serviceProvidedRepository;

    @Transactional
    public Employee create(String firstName, String lastName, String email, String phoneNumber, Instant hireDate,
                           BigDecimal salary, BigDecimal commissionPct, Employee manager, Salon salon) {
        Employee employee = new Employee(null, firstName, lastName, email, phoneNumber, hireDate, salary, commissionPct,
            manager, salon);
        log.debug("Request to create Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(Long id, String firstName, String lastName, String email, String phoneNumber, Instant hireDate,
                           BigDecimal salary, BigDecimal commissionPct, Employee manager, Salon salon) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Employee"));
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);
        employee.setHireDate(hireDate);
        employee.setSalary(salary);
        employee.setCommissionPct(commissionPct);
        employee.setManager(manager);
        employee.setSalon(salon);
        log.debug("Request to update Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

    @Transactional
    public ScheduleDto getEmployeeSchedule(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(id, "Employee"));
        LocalDate now = LocalDate.now();
        Instant startDatePeriod = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endDatePeriod = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atStartOfDay(ZoneId.systemDefault()).toInstant();
        List<ServiceProvided> servicesProvided = serviceProvidedRepository.findAllByEmployeeAndStartDateGreaterThanAndEndDateLessThan(
            employee, startDatePeriod, endDatePeriod);
        Map<DayOfWeek, Collection<ServiceProvided>> serviceProvidedMap = new HashMap<>();

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            Collection<ServiceProvided> daySchedule = servicesProvided.stream()
                .filter(sp -> sp.getStartDate().atZone(ZoneId.systemDefault()).getDayOfWeek() == dayOfWeek)
                .collect(Collectors.toList());
            serviceProvidedMap.put(dayOfWeek, daySchedule);
        }

        return new ScheduleDto(startDatePeriod, endDatePeriod, serviceProvidedMap);
    }

}
