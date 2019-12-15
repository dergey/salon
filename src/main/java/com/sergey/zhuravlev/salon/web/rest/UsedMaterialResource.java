package com.sergey.zhuravlev.salon.web.rest;

import com.sergey.zhuravlev.salon.domain.UsedMaterial;
import com.sergey.zhuravlev.salon.service.UsedMaterialService;
import com.sergey.zhuravlev.salon.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sergey.zhuravlev.salon.domain.UsedMaterial}.
 */
@RestController
@RequestMapping("/api")
public class UsedMaterialResource {

    private final Logger log = LoggerFactory.getLogger(UsedMaterialResource.class);

    private static final String ENTITY_NAME = "usedMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsedMaterialService usedMaterialService;

    public UsedMaterialResource(UsedMaterialService usedMaterialService) {
        this.usedMaterialService = usedMaterialService;
    }

    /**
     * {@code POST  /used-materials} : Create a new usedMaterial.
     *
     * @param usedMaterial the usedMaterial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usedMaterial, or with status {@code 400 (Bad Request)} if the usedMaterial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/used-materials")
    public ResponseEntity<UsedMaterial> createUsedMaterial(@Valid @RequestBody UsedMaterial usedMaterial) throws URISyntaxException {
        log.debug("REST request to save UsedMaterial : {}", usedMaterial);
        if (usedMaterial.getId() != null) {
            throw new BadRequestAlertException("A new usedMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsedMaterial result = usedMaterialService.save(usedMaterial);
        return ResponseEntity.created(new URI("/api/used-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /used-materials} : Updates an existing usedMaterial.
     *
     * @param usedMaterial the usedMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usedMaterial,
     * or with status {@code 400 (Bad Request)} if the usedMaterial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usedMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/used-materials")
    public ResponseEntity<UsedMaterial> updateUsedMaterial(@Valid @RequestBody UsedMaterial usedMaterial) throws URISyntaxException {
        log.debug("REST request to update UsedMaterial : {}", usedMaterial);
        if (usedMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsedMaterial result = usedMaterialService.save(usedMaterial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usedMaterial.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /used-materials} : get all the usedMaterials.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usedMaterials in body.
     */
    @GetMapping("/used-materials")
    public ResponseEntity<List<UsedMaterial>> getAllUsedMaterials(Pageable pageable) {
        log.debug("REST request to get a page of UsedMaterials");
        Page<UsedMaterial> page = usedMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /used-materials/:id} : get the "id" usedMaterial.
     *
     * @param id the id of the usedMaterial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usedMaterial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/used-materials/{id}")
    public ResponseEntity<UsedMaterial> getUsedMaterial(@PathVariable Long id) {
        log.debug("REST request to get UsedMaterial : {}", id);
        Optional<UsedMaterial> usedMaterial = usedMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usedMaterial);
    }

    /**
     * {@code DELETE  /used-materials/:id} : delete the "id" usedMaterial.
     *
     * @param id the id of the usedMaterial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/used-materials/{id}")
    public ResponseEntity<Void> deleteUsedMaterial(@PathVariable Long id) {
        log.debug("REST request to delete UsedMaterial : {}", id);
        usedMaterialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
