package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Salon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Salon}.
 */
public interface SalonService {

    /**
     * Save a salon.
     *
     * @param salon the entity to save.
     * @return the persisted entity.
     */
    Salon save(Salon salon);

    /**
     * Get all the salons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Salon> findAll(Pageable pageable);


    /**
     * Get the "id" salon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Salon> findOne(Long id);

    /**
     * Delete the "id" salon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
