package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Service;
import com.sergey.zhuravlev.salon.dto.ServiceDto;
import com.sergey.zhuravlev.salon.dto.ServiceRequestDto;
import com.sergey.zhuravlev.salon.mapper.ServiceMapper;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private static final String ENTITY_NAME = "service";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceService serviceService;

    private final ServiceMapper serviceMapper;

    @PostMapping
    public ResponseEntity<ServiceDto> createService(@Valid @RequestBody ServiceRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save Service : {}", dto);

        Service result = serviceService.create(dto.getTitle(), dto.getSex(), dto.getPrice());
        return ResponseEntity.created(new URI("/api/services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(serviceMapper.serviceToServiceDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDto> updateService(@PathVariable Long id,
                                                    @Valid @RequestBody ServiceRequestDto dto) throws URISyntaxException {
        log.debug("REST request to update Service : {}", dto);
        Service result = serviceService.update(id, dto.getTitle(), dto.getSex(), dto.getPrice());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(serviceMapper.serviceToServiceDto(result));
    }

    @GetMapping
    public ResponseEntity<List<ServiceDto>> getAllServices(Pageable pageable) {
        log.debug("REST request to get a page of Services");
        Page<Service> page = serviceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(serviceMapper::serviceToServiceDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> getService(@PathVariable Long id) {
        log.debug("REST request to get Service : {}", id);
        Optional<Service> service = serviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(service.map(serviceMapper::serviceToServiceDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        log.debug("REST request to delete Service : {}", id);
        serviceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
