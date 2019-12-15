package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Specialization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Specialization}.
 */
public interface SpecializationService {

    /**
     * Save a specialization.
     *
     * @param specialization the entity to save.
     * @return the persisted entity.
     */
    Specialization save(Specialization specialization);

    /**
     * Get all the specializations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Specialization> findAll(Pageable pageable);


    /**
     * Get the "id" specialization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Specialization> findOne(Long id);

    /**
     * Delete the "id" specialization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
