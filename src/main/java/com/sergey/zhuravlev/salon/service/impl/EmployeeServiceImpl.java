package com.sergey.zhuravlev.salon.service.impl;

import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import com.sergey.zhuravlev.salon.repository.ServiceProvidedRepository;
import com.sergey.zhuravlev.salon.service.EmployeeService;
import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.repository.EmployeeRepository;
import com.sergey.zhuravlev.salon.service.dto.ScheduleDTO;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final ServiceProvidedRepository serviceProvidedRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ServiceProvidedRepository serviceProvidedRepository) {
        this.employeeRepository = employeeRepository;
        this.serviceProvidedRepository = serviceProvidedRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Employee save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }


    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

    @Override
    public ScheduleDTO getEmployeeSchedule(Long id) {
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

        return new ScheduleDTO(startDatePeriod, endDatePeriod, serviceProvidedMap);
    }

}
