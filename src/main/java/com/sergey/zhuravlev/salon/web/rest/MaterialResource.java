package com.sergey.zhuravlev.salon.web.rest;

import com.sergey.zhuravlev.salon.domain.Material;
import com.sergey.zhuravlev.salon.service.MaterialService;
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
 * REST controller for managing {@link com.sergey.zhuravlev.salon.domain.Material}.
 */
@RestController
@RequestMapping("/api")
public class MaterialResource {

    private final Logger log = LoggerFactory.getLogger(MaterialResource.class);

    private static final String ENTITY_NAME = "material";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialService materialService;

    public MaterialResource(MaterialService materialService) {
        this.materialService = materialService;
    }

    /**
     * {@code POST  /materials} : Create a new material.
     *
     * @param material the material to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new material, or with status {@code 400 (Bad Request)} if the material has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materials")
    public ResponseEntity<Material> createMaterial(@Valid @RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to save Material : {}", material);
        if (material.getId() != null) {
            throw new BadRequestAlertException("A new material cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Material result = materialService.save(material);
        return ResponseEntity.created(new URI("/api/materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materials} : Updates an existing material.
     *
     * @param material the material to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated material,
     * or with status {@code 400 (Bad Request)} if the material is not valid,
     * or with status {@code 500 (Internal Server Error)} if the material couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materials")
    public ResponseEntity<Material> updateMaterial(@Valid @RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to update Material : {}", material);
        if (material.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Material result = materialService.save(material);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, material.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /materials} : get all the materials.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materials in body.
     */
    @GetMapping("/materials")
    public ResponseEntity<List<Material>> getAllMaterials(Pageable pageable) {
        log.debug("REST request to get a page of Materials");
        Page<Material> page = materialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /materials/:id} : get the "id" material.
     *
     * @param id the id of the material to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the material, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materials/{id}")
    public ResponseEntity<Material> getMaterial(@PathVariable Long id) {
        log.debug("REST request to get Material : {}", id);
        Optional<Material> material = materialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(material);
    }

    /**
     * {@code DELETE  /materials/:id} : delete the "id" material.
     *
     * @param id the id of the material to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materials/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        log.debug("REST request to delete Material : {}", id);
        materialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
