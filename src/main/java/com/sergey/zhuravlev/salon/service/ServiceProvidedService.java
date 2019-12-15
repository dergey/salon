package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.ServiceProvided;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceProvided}.
 */
public interface ServiceProvidedService {

    /**
     * Save a serviceProvided.
     *
     * @param serviceProvided the entity to save.
     * @return the persisted entity.
     */
    ServiceProvided save(ServiceProvided serviceProvided);

    /**
     * Get all the serviceProvideds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceProvided> findAll(Pageable pageable);


    /**
     * Get the "id" serviceProvided.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceProvided> findOne(Long id);

    /**
     * Delete the "id" serviceProvided.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
