package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Material;
import com.sergey.zhuravlev.salon.domain.MaterialInSalon;
import com.sergey.zhuravlev.salon.domain.Salon;
import com.sergey.zhuravlev.salon.dto.MaterialInSalonDto;
import com.sergey.zhuravlev.salon.dto.MaterialInSalonRequestDto;
import com.sergey.zhuravlev.salon.exception.BadRequestAlertException;
import com.sergey.zhuravlev.salon.mapper.MaterialInSalonMapper;
import com.sergey.zhuravlev.salon.service.MaterialInSalonService;
import com.sergey.zhuravlev.salon.service.MaterialService;
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
@RequestMapping("/api/material-in-salons")
@RequiredArgsConstructor
public class MaterialInSalonController {

    private static final String ENTITY_NAME = "materialInSalon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalonService salonService;
    private final MaterialService materialService;
    private final MaterialInSalonService materialInSalonService;

    private final MaterialInSalonMapper materialInSalonMapper;

    @PostMapping
    public ResponseEntity<MaterialInSalonDto> createMaterialInSalon(@Valid @RequestBody MaterialInSalonRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save MaterialInSalon : {}", dto);
        Material material = materialService.findOne(dto.getMaterialId())
            .orElseThrow(() -> new BadRequestAlertException("Material with given ID does not exist", "material", "entitynotexist"));
        Salon salon = salonService.findOne(dto.getSalonId())
            .orElseThrow(() -> new BadRequestAlertException("Salon with given ID does not exist", "salon", "entitynotexist"));
        MaterialInSalon result = materialInSalonService.create(dto.getCount(), material, salon);
        return ResponseEntity.created(new URI("/api/material-in-salons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(materialInSalonMapper.materialInSalonToMaterialInSalonDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialInSalonDto> updateMaterialInSalon(@PathVariable Long id,
                                                                    @Valid @RequestBody MaterialInSalonRequestDto dto) {
        log.debug("REST request to update MaterialInSalon : {}", dto);
        Material material = materialService.findOne(dto.getMaterialId())
            .orElseThrow(() -> new BadRequestAlertException("Material with given ID does not exist", "material", "entitynotexist"));
        Salon salon = salonService.findOne(dto.getSalonId())
            .orElseThrow(() -> new BadRequestAlertException("Salon with given ID does not exist", "salon", "entitynotexist"));
        MaterialInSalon result = materialInSalonService.update(id, dto.getCount(), material, salon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(materialInSalonMapper.materialInSalonToMaterialInSalonDto(result));
    }

    @GetMapping
    public ResponseEntity<List<MaterialInSalonDto>> getAllMaterialInSalons(Pageable pageable) {
        log.debug("REST request to get a page of MaterialInSalons");
        Page<MaterialInSalon> page = materialInSalonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(materialInSalonMapper::materialInSalonToMaterialInSalonDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialInSalonDto> getMaterialInSalon(@PathVariable Long id) {
        log.debug("REST request to get MaterialInSalon : {}", id);
        Optional<MaterialInSalon> materialInSalon = materialInSalonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialInSalon.map(materialInSalonMapper::materialInSalonToMaterialInSalonDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterialInSalon(@PathVariable Long id) {
        log.debug("REST request to delete MaterialInSalon : {}", id);
        materialInSalonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
