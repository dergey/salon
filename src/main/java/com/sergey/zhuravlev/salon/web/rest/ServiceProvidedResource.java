package com.sergey.zhuravlev.salon.web.rest;

import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import com.sergey.zhuravlev.salon.service.ServiceProvidedService;
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
 * REST controller for managing {@link com.sergey.zhuravlev.salon.domain.ServiceProvided}.
 */
@RestController
@RequestMapping("/api")
public class ServiceProvidedResource {

    private final Logger log = LoggerFactory.getLogger(ServiceProvidedResource.class);

    private static final String ENTITY_NAME = "serviceProvided";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceProvidedService serviceProvidedService;

    public ServiceProvidedResource(ServiceProvidedService serviceProvidedService) {
        this.serviceProvidedService = serviceProvidedService;
    }

    /**
     * {@code POST  /service-provideds} : Create a new serviceProvided.
     *
     * @param serviceProvided the serviceProvided to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceProvided, or with status {@code 400 (Bad Request)} if the serviceProvided has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-provideds")
    public ResponseEntity<ServiceProvided> createServiceProvided(@RequestBody ServiceProvided serviceProvided) throws URISyntaxException {
        log.debug("REST request to save ServiceProvided : {}", serviceProvided);
        if (serviceProvided.getId() != null) {
            throw new BadRequestAlertException("A new serviceProvided cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceProvided result = serviceProvidedService.save(serviceProvided);
        return ResponseEntity.created(new URI("/api/service-provideds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-provideds} : Updates an existing serviceProvided.
     *
     * @param serviceProvided the serviceProvided to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceProvided,
     * or with status {@code 400 (Bad Request)} if the serviceProvided is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceProvided couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-provideds")
    public ResponseEntity<ServiceProvided> updateServiceProvided(@RequestBody ServiceProvided serviceProvided) throws URISyntaxException {
        log.debug("REST request to update ServiceProvided : {}", serviceProvided);
        if (serviceProvided.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceProvided result = serviceProvidedService.save(serviceProvided);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceProvided.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-provideds} : get all the serviceProvideds.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceProvideds in body.
     */
    @GetMapping("/service-provideds")
    public ResponseEntity<List<ServiceProvided>> getAllServiceProvideds(Pageable pageable) {
        log.debug("REST request to get a page of ServiceProvideds");
        Page<ServiceProvided> page = serviceProvidedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-provideds/:id} : get the "id" serviceProvided.
     *
     * @param id the id of the serviceProvided to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceProvided, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-provideds/{id}")
    public ResponseEntity<ServiceProvided> getServiceProvided(@PathVariable Long id) {
        log.debug("REST request to get ServiceProvided : {}", id);
        Optional<ServiceProvided> serviceProvided = serviceProvidedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceProvided);
    }

    /**
     * {@code DELETE  /service-provideds/:id} : delete the "id" serviceProvided.
     *
     * @param id the id of the serviceProvided to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-provideds/{id}")
    public ResponseEntity<Void> deleteServiceProvided(@PathVariable Long id) {
        log.debug("REST request to delete ServiceProvided : {}", id);
        serviceProvidedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
