package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.domain.Salon;
import com.sergey.zhuravlev.salon.dto.EmployeeDto;
import com.sergey.zhuravlev.salon.dto.EmployeeRequestDto;
import com.sergey.zhuravlev.salon.dto.ScheduleDto;
import com.sergey.zhuravlev.salon.exception.BadRequestAlertException;
import com.sergey.zhuravlev.salon.mapper.EmployeeMapper;
import com.sergey.zhuravlev.salon.service.EmployeeService;
import com.sergey.zhuravlev.salon.service.SalonService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private static final String ENTITY_NAME = "employee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeService employeeService;
    private final SalonService salonService;

    private final EmployeeMapper employeeMapper;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", dto);
        Employee manager = null;
        if (dto.getManagerId() != null) {
            manager = employeeService.findOne(dto.getManagerId())
                    .orElseThrow(() -> new BadRequestAlertException("Manager with given ID does not exist", ENTITY_NAME, "entitynotexist"));
        }
        Salon salon = salonService.findOne(dto.getSalonId())
            .orElseThrow(() -> new BadRequestAlertException("Salon with given ID does not exist", "salon", "entitynotexist"));
        Employee result = employeeService.create(dto.getFirstName(),
            dto.getLastName(),
            dto.getEmail(),
            dto.getPhoneNumber(),
            dto.getHireDate(),
            dto.getSalary(),
            dto.getCommissionPct(),
            manager,
            salon);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(employeeMapper.employeeToEmployeeDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
                                                      @Valid @RequestBody EmployeeRequestDto dto) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", dto);
        Employee manager = employeeService.findOne(dto.getManagerId())
            .orElseThrow(() -> new BadRequestAlertException("Manager with given ID does not exist", ENTITY_NAME, "entitynotexist"));
        Salon salon = salonService.findOne(dto.getSalonId())
            .orElseThrow(() -> new BadRequestAlertException("Salon with given ID does not exist", "salon", "entitynotexist"));
        Employee result = employeeService.update(id,
            dto.getFirstName(),
            dto.getLastName(),
            dto.getEmail(),
            dto.getPhoneNumber(),
            dto.getHireDate(),
            dto.getSalary(),
            dto.getCommissionPct(),
            manager,
            salon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(employeeMapper.employeeToEmployeeDto(result));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(Pageable pageable) {
        log.debug("REST request to get a page of Employees");
        Page<Employee> page = employeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(employeeMapper::employeeToEmployeeDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        Optional<Employee> employee = employeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employee.map(employeeMapper::employeeToEmployeeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping(path = "/{id}/schedule")
    public ScheduleDto getEmployeeSchedule(@PathVariable Long id) {
        return employeeService.getEmployeeSchedule(id);
    }

}
