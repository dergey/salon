package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.domain.Material;
import com.sergey.zhuravlev.salon.domain.Order;
import com.sergey.zhuravlev.salon.domain.UsedMaterial;
import com.sergey.zhuravlev.salon.dto.UsedMaterialDto;
import com.sergey.zhuravlev.salon.dto.UsedMaterialRequestDto;
import com.sergey.zhuravlev.salon.exception.BadRequestAlertException;
import com.sergey.zhuravlev.salon.mapper.UsedMaterialMapper;
import com.sergey.zhuravlev.salon.service.EmployeeService;
import com.sergey.zhuravlev.salon.service.MaterialService;
import com.sergey.zhuravlev.salon.service.OrderService;
import com.sergey.zhuravlev.salon.service.UsedMaterialService;
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
@RequestMapping("/api/used-materials")
@RequiredArgsConstructor
public class UsedMaterialController {

    private static final String ENTITY_NAME = "usedMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderService orderService;
    private final MaterialService materialService;
    private final EmployeeService employeeService;
    private final UsedMaterialService usedMaterialService;

    private final UsedMaterialMapper usedMaterialMapper;

    @PostMapping
    public ResponseEntity<UsedMaterialDto> createUsedMaterial(@Valid @RequestBody UsedMaterialRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save UsedMaterial : {}", dto);
        Order order = orderService.findOne(dto.getOrderId())
            .orElseThrow(() -> new BadRequestAlertException("Order with given ID does not exist", "order", "entitynotexist"));
        Material material = materialService.findOne(dto.getMaterialId())
            .orElseThrow(() -> new BadRequestAlertException("Material with given ID does not exist", "material", "entitynotexist"));
        Employee employee = employeeService.findOne(dto.getEmployeeId())
            .orElseThrow(() -> new BadRequestAlertException("Employee with given ID does not exist", "employee", "entitynotexist"));
        UsedMaterial result = usedMaterialService.create(dto.getCount(), dto.getDecommission(), order, material, employee);
        return ResponseEntity.created(new URI("/api/used-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(usedMaterialMapper.usedMaterialToUsedMaterialDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsedMaterialDto> updateUsedMaterial(@PathVariable Long id,
                                                              @Valid @RequestBody UsedMaterialRequestDto dto) throws URISyntaxException {
        log.debug("REST request to update UsedMaterial : {}", dto);
        Order order = orderService.findOne(dto.getOrderId())
            .orElseThrow(() -> new BadRequestAlertException("Order with given ID does not exist", "order", "entitynotexist"));
        Material material = materialService.findOne(dto.getMaterialId())
            .orElseThrow(() -> new BadRequestAlertException("Material with given ID does not exist", "material", "entitynotexist"));
        Employee employee = employeeService.findOne(dto.getEmployeeId())
            .orElseThrow(() -> new BadRequestAlertException("Employee with given ID does not exist", "employee", "entitynotexist"));
        UsedMaterial result = usedMaterialService.update(id, dto.getCount(), dto.getDecommission(), order, material, employee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(usedMaterialMapper.usedMaterialToUsedMaterialDto(result));
    }

    @GetMapping
    public ResponseEntity<List<UsedMaterialDto>> getAllUsedMaterials(Pageable pageable) {
        log.debug("REST request to get a page of UsedMaterials");
        Page<UsedMaterial> page = usedMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(usedMaterialMapper::usedMaterialToUsedMaterialDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsedMaterialDto> getUsedMaterial(@PathVariable Long id) {
        log.debug("REST request to get UsedMaterial : {}", id);
        Optional<UsedMaterial> usedMaterial = usedMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usedMaterial.map(usedMaterialMapper::usedMaterialToUsedMaterialDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsedMaterial(@PathVariable Long id) {
        log.debug("REST request to delete UsedMaterial : {}", id);
        usedMaterialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
