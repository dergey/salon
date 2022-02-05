package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.domain.Service;
import com.sergey.zhuravlev.salon.domain.Specialization;
import com.sergey.zhuravlev.salon.dto.SpecializationDto;
import com.sergey.zhuravlev.salon.dto.SpecializationRequestDto;
import com.sergey.zhuravlev.salon.exception.BadRequestAlertException;
import com.sergey.zhuravlev.salon.mapper.SpecializationMapper;
import com.sergey.zhuravlev.salon.service.EmployeeService;
import com.sergey.zhuravlev.salon.service.ServiceService;
import com.sergey.zhuravlev.salon.service.SpecializationService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private static final String ENTITY_NAME = "specialization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceService serviceService;
    private final EmployeeService employeeService;
    private final SpecializationService specializationService;

    private final SpecializationMapper specializationMapper;

    @PostMapping
    public ResponseEntity<SpecializationDto> createSpecialization(@RequestBody SpecializationRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save Specialization : {}", dto);
        Employee employee = employeeService.findOne(dto.getEmployeeId())
            .orElseThrow(() -> new BadRequestAlertException("Employee with given ID does not exist", "employee", "entitynotexist"));
        Service service = serviceService.findOne(dto.getServiceId())
            .orElseThrow(() -> new BadRequestAlertException("Service with given ID does not exist", "service", "entitynotexist"));
        Specialization result = specializationService.create(dto.getNote(), employee, service);
        return ResponseEntity.created(new URI("/api/specializations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(specializationMapper.specializationToSpecializationDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecializationDto> updateSpecialization(@PathVariable Long id,
                                                                  @RequestBody SpecializationRequestDto dto) throws URISyntaxException {
        log.debug("REST request to update Specialization : {}", dto);
        Employee employee = employeeService.findOne(dto.getEmployeeId())
            .orElseThrow(() -> new BadRequestAlertException("Employee with given ID does not exist", "employee", "entitynotexist"));
        Service service = serviceService.findOne(dto.getServiceId())
            .orElseThrow(() -> new BadRequestAlertException("Service with given ID does not exist", "service", "entitynotexist"));
        Specialization result = specializationService.update(id, dto.getNote(), employee, service);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(specializationMapper.specializationToSpecializationDto(result));
    }

    @GetMapping
    public ResponseEntity<List<SpecializationDto>> getAllSpecializations(Pageable pageable) {
        log.debug("REST request to get a page of Specializations");
        Page<Specialization> page = specializationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(specializationMapper::specializationToSpecializationDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecializationDto> getSpecialization(@PathVariable Long id) {
        log.debug("REST request to get Specialization : {}", id);
        Optional<Specialization> specialization = specializationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialization.map(specializationMapper::specializationToSpecializationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable Long id) {
        log.debug("REST request to delete Specialization : {}", id);
        specializationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
