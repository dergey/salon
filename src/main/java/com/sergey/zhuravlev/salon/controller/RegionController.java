package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Region;
import com.sergey.zhuravlev.salon.security.AuthoritiesConstants;
import com.sergey.zhuravlev.salon.service.RegionService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegionController {

    private static final String ENTITY_NAME = "region";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegionService regionService;

    @GetMapping("/regions")
    public List<Region> getAllRegions() {
        log.debug("REST request to get all Regions");
        return regionService.findAll();
    }

    @GetMapping("/regions/{id}")
    public ResponseEntity<Region> getRegion(@PathVariable Long id) {
        log.debug("REST request to get Region : {}", id);
        Optional<Region> region = regionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(region);
    }

    @PostMapping("/regions/{id}/activate")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Region> activateRegion(@PathVariable Long id) {
        log.debug("REST request to activate Region : {}", id);
        Optional<Region> region = regionService.findOne(id);
        if (region.isPresent()) {
            regionService.activate(id);
            return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/regions/{id}/deactivate")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Region> deactivateRegion(@PathVariable Long id) {
        log.debug("REST request to deactivate Region : {}", id);
        Optional<Region> region = regionService.findOne(id);
        if (region.isPresent()) {
            regionService.deactivate(id);
            return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
