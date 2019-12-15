package com.sergey.zhuravlev.salon.web.rest;

import com.sergey.zhuravlev.salon.domain.Salon;
import com.sergey.zhuravlev.salon.service.SalonService;
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
 * REST controller for managing {@link com.sergey.zhuravlev.salon.domain.Salon}.
 */
@RestController
@RequestMapping("/api")
public class SalonResource {

    private final Logger log = LoggerFactory.getLogger(SalonResource.class);

    private static final String ENTITY_NAME = "salon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalonService salonService;

    public SalonResource(SalonService salonService) {
        this.salonService = salonService;
    }

    /**
     * {@code POST  /salons} : Create a new salon.
     *
     * @param salon the salon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salon, or with status {@code 400 (Bad Request)} if the salon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salons")
    public ResponseEntity<Salon> createSalon(@Valid @RequestBody Salon salon) throws URISyntaxException {
        log.debug("REST request to save Salon : {}", salon);
        if (salon.getId() != null) {
            throw new BadRequestAlertException("A new salon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Salon result = salonService.save(salon);
        return ResponseEntity.created(new URI("/api/salons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salons} : Updates an existing salon.
     *
     * @param salon the salon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salon,
     * or with status {@code 400 (Bad Request)} if the salon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salons")
    public ResponseEntity<Salon> updateSalon(@Valid @RequestBody Salon salon) throws URISyntaxException {
        log.debug("REST request to update Salon : {}", salon);
        if (salon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Salon result = salonService.save(salon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salon.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /salons} : get all the salons.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salons in body.
     */
    @GetMapping("/salons")
    public ResponseEntity<List<Salon>> getAllSalons(Pageable pageable) {
        log.debug("REST request to get a page of Salons");
        Page<Salon> page = salonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /salons/:id} : get the "id" salon.
     *
     * @param id the id of the salon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salons/{id}")
    public ResponseEntity<Salon> getSalon(@PathVariable Long id) {
        log.debug("REST request to get Salon : {}", id);
        Optional<Salon> salon = salonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salon);
    }

    /**
     * {@code DELETE  /salons/:id} : delete the "id" salon.
     *
     * @param id the id of the salon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salons/{id}")
    public ResponseEntity<Void> deleteSalon(@PathVariable Long id) {
        log.debug("REST request to delete Salon : {}", id);
        salonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
