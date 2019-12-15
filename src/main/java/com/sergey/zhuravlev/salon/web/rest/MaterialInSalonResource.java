package com.sergey.zhuravlev.salon.web.rest;

import com.sergey.zhuravlev.salon.domain.MaterialInSalon;
import com.sergey.zhuravlev.salon.service.MaterialInSalonService;
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
 * REST controller for managing {@link com.sergey.zhuravlev.salon.domain.MaterialInSalon}.
 */
@RestController
@RequestMapping("/api")
public class MaterialInSalonResource {

    private final Logger log = LoggerFactory.getLogger(MaterialInSalonResource.class);

    private static final String ENTITY_NAME = "materialInSalon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialInSalonService materialInSalonService;

    public MaterialInSalonResource(MaterialInSalonService materialInSalonService) {
        this.materialInSalonService = materialInSalonService;
    }

    /**
     * {@code POST  /material-in-salons} : Create a new materialInSalon.
     *
     * @param materialInSalon the materialInSalon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialInSalon, or with status {@code 400 (Bad Request)} if the materialInSalon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/material-in-salons")
    public ResponseEntity<MaterialInSalon> createMaterialInSalon(@Valid @RequestBody MaterialInSalon materialInSalon) throws URISyntaxException {
        log.debug("REST request to save MaterialInSalon : {}", materialInSalon);
        if (materialInSalon.getId() != null) {
            throw new BadRequestAlertException("A new materialInSalon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialInSalon result = materialInSalonService.save(materialInSalon);
        return ResponseEntity.created(new URI("/api/material-in-salons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-in-salons} : Updates an existing materialInSalon.
     *
     * @param materialInSalon the materialInSalon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialInSalon,
     * or with status {@code 400 (Bad Request)} if the materialInSalon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialInSalon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/material-in-salons")
    public ResponseEntity<MaterialInSalon> updateMaterialInSalon(@Valid @RequestBody MaterialInSalon materialInSalon) throws URISyntaxException {
        log.debug("REST request to update MaterialInSalon : {}", materialInSalon);
        if (materialInSalon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaterialInSalon result = materialInSalonService.save(materialInSalon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materialInSalon.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /material-in-salons} : get all the materialInSalons.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialInSalons in body.
     */
    @GetMapping("/material-in-salons")
    public ResponseEntity<List<MaterialInSalon>> getAllMaterialInSalons(Pageable pageable) {
        log.debug("REST request to get a page of MaterialInSalons");
        Page<MaterialInSalon> page = materialInSalonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /material-in-salons/:id} : get the "id" materialInSalon.
     *
     * @param id the id of the materialInSalon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialInSalon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/material-in-salons/{id}")
    public ResponseEntity<MaterialInSalon> getMaterialInSalon(@PathVariable Long id) {
        log.debug("REST request to get MaterialInSalon : {}", id);
        Optional<MaterialInSalon> materialInSalon = materialInSalonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialInSalon);
    }

    /**
     * {@code DELETE  /material-in-salons/:id} : delete the "id" materialInSalon.
     *
     * @param id the id of the materialInSalon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/material-in-salons/{id}")
    public ResponseEntity<Void> deleteMaterialInSalon(@PathVariable Long id) {
        log.debug("REST request to delete MaterialInSalon : {}", id);
        materialInSalonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
