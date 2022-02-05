package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.domain.Order;
import com.sergey.zhuravlev.salon.domain.Service;
import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import com.sergey.zhuravlev.salon.dto.ServiceProvidedDto;
import com.sergey.zhuravlev.salon.dto.ServiceProvidedRequestDto;
import com.sergey.zhuravlev.salon.exception.BadRequestAlertException;
import com.sergey.zhuravlev.salon.mapper.ServiceProvidedMapper;
import com.sergey.zhuravlev.salon.service.EmployeeService;
import com.sergey.zhuravlev.salon.service.OrderService;
import com.sergey.zhuravlev.salon.service.ServiceProvidedService;
import com.sergey.zhuravlev.salon.service.ServiceService;
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
@RequestMapping("/api/service-provideds")
@RequiredArgsConstructor
public class ServiceProvidedController {

    private static final String ENTITY_NAME = "serviceProvided";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderService orderService;
    private final EmployeeService employeeService;
    private final ServiceService serviceService;
    private final ServiceProvidedService serviceProvidedService;

    private final ServiceProvidedMapper serviceProvidedMapper;

    @PostMapping
    public ResponseEntity<ServiceProvidedDto> createServiceProvided(@RequestBody ServiceProvidedRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save ServiceProvided : {}", dto);
        Order order = orderService.findOne(dto.getOrderId())
            .orElseThrow(() -> new BadRequestAlertException("Order with given ID does not exist", "order", "entitynotexist"));
        Employee employee = employeeService.findOne(dto.getEmployeeId())
            .orElseThrow(() -> new BadRequestAlertException("Employee with given ID does not exist", "employee", "entitynotexist"));
        Service service = serviceService.findOne(dto.getServiceId())
            .orElseThrow(() -> new BadRequestAlertException("Service with given ID does not exist", "service", "entitynotexist"));
        ServiceProvided result = serviceProvidedService.create(dto.getStartDate(), dto.getEndDate(), dto.getNote(), order, employee, service);
        return ResponseEntity.created(new URI("/api/service-provideds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(serviceProvidedMapper.serviceProvidedToServiceProvidedDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceProvidedDto> updateServiceProvided(@PathVariable Long id,
                                                                 @RequestBody ServiceProvidedRequestDto dto) throws URISyntaxException {
        log.debug("REST request to update ServiceProvided : {}", dto);
        Order order = orderService.findOne(dto.getOrderId())
            .orElseThrow(() -> new BadRequestAlertException("Order with given ID does not exist", "order", "entitynotexist"));
        Employee employee = employeeService.findOne(dto.getEmployeeId())
            .orElseThrow(() -> new BadRequestAlertException("Employee with given ID does not exist", "employee", "entitynotexist"));
        Service service = serviceService.findOne(dto.getServiceId())
            .orElseThrow(() -> new BadRequestAlertException("Service with given ID does not exist", "service", "entitynotexist"));
        ServiceProvided result = serviceProvidedService.update(id, dto.getStartDate(), dto.getEndDate(), dto.getNote(), order, employee, service);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(serviceProvidedMapper.serviceProvidedToServiceProvidedDto(result));
    }

    @GetMapping
    public ResponseEntity<List<ServiceProvidedDto>> getAllServiceProvideds(Pageable pageable) {
        log.debug("REST request to get a page of ServiceProvideds");
        Page<ServiceProvided> page = serviceProvidedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(serviceProvidedMapper::serviceProvidedToServiceProvidedDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceProvidedDto> getServiceProvided(@PathVariable Long id) {
        log.debug("REST request to get ServiceProvided : {}", id);
        Optional<ServiceProvided> serviceProvided = serviceProvidedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceProvided.map(serviceProvidedMapper::serviceProvidedToServiceProvidedDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceProvided(@PathVariable Long id) {
        log.debug("REST request to delete ServiceProvided : {}", id);
        serviceProvidedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
