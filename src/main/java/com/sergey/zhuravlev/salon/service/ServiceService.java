package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Service}.
 */
public interface ServiceService {

    /**
     * Save a service.
     *
     * @param service the entity to save.
     * @return the persisted entity.
     */
    Service save(Service service);

    /**
     * Get all the services.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Service> findAll(Pageable pageable);


    /**
     * Get the "id" service.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Service> findOne(Long id);

    /**
     * Delete the "id" service.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
