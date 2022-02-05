package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Material;
import com.sergey.zhuravlev.salon.dto.MaterialDto;
import com.sergey.zhuravlev.salon.dto.MaterialRequestDto;
import com.sergey.zhuravlev.salon.mapper.MaterialMapper;
import com.sergey.zhuravlev.salon.service.MaterialService;
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
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private static final String ENTITY_NAME = "material";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialService materialService;

    private final MaterialMapper materialMapper;

    @PostMapping
    public ResponseEntity<MaterialDto> createMaterial(@Valid @RequestBody MaterialRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save Material : {}", dto);
        Material result = materialService.create(dto.getTitle(), dto.getUnit(), dto.getPrice());
        return ResponseEntity.created(new URI("/api/materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(materialMapper.materialToMaterialDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDto> updateMaterial(@PathVariable Long id,
                                                      @Valid @RequestBody MaterialRequestDto dto) {
        log.debug("REST request to update Material : {}", dto);
        Material result = materialService.update(id, dto.getTitle(), dto.getUnit(), dto.getPrice());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(materialMapper.materialToMaterialDto(result));
    }

    @GetMapping
    public ResponseEntity<List<MaterialDto>> getAllMaterials(Pageable pageable) {
        log.debug("REST request to get a page of Materials");
        Page<Material> page = materialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(materialMapper::materialToMaterialDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDto> getMaterial(@PathVariable Long id) {
        log.debug("REST request to get Material : {}", id);
        Optional<Material> material = materialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(material.map(materialMapper::materialToMaterialDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        log.debug("REST request to delete Material : {}", id);
        materialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
