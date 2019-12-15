package com.sergey.zhuravlev.salon.web.rest;

import com.sergey.zhuravlev.salon.domain.Specialization;
import com.sergey.zhuravlev.salon.service.SpecializationService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sergey.zhuravlev.salon.domain.Specialization}.
 */
@RestController
@RequestMapping("/api")
public class SpecializationResource {

    private final Logger log = LoggerFactory.getLogger(SpecializationResource.class);

    private static final String ENTITY_NAME = "specialization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecializationService specializationService;

    public SpecializationResource(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    /**
     * {@code POST  /specializations} : Create a new specialization.
     *
     * @param specialization the specialization to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialization, or with status {@code 400 (Bad Request)} if the specialization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specializations")
    public ResponseEntity<Specialization> createSpecialization(@RequestBody Specialization specialization) throws URISyntaxException {
        log.debug("REST request to save Specialization : {}", specialization);
        if (specialization.getId() != null) {
            throw new BadRequestAlertException("A new specialization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Specialization result = specializationService.save(specialization);
        return ResponseEntity.created(new URI("/api/specializations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specializations} : Updates an existing specialization.
     *
     * @param specialization the specialization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialization,
     * or with status {@code 400 (Bad Request)} if the specialization is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specializations")
    public ResponseEntity<Specialization> updateSpecialization(@RequestBody Specialization specialization) throws URISyntaxException {
        log.debug("REST request to update Specialization : {}", specialization);
        if (specialization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Specialization result = specializationService.save(specialization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, specialization.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /specializations} : get all the specializations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specializations in body.
     */
    @GetMapping("/specializations")
    public ResponseEntity<List<Specialization>> getAllSpecializations(Pageable pageable) {
        log.debug("REST request to get a page of Specializations");
        Page<Specialization> page = specializationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specializations/:id} : get the "id" specialization.
     *
     * @param id the id of the specialization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specializations/{id}")
    public ResponseEntity<Specialization> getSpecialization(@PathVariable Long id) {
        log.debug("REST request to get Specialization : {}", id);
        Optional<Specialization> specialization = specializationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialization);
    }

    /**
     * {@code DELETE  /specializations/:id} : delete the "id" specialization.
     *
     * @param id the id of the specialization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specializations/{id}")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable Long id) {
        log.debug("REST request to delete Specialization : {}", id);
        specializationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
