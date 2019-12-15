package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.MaterialInSalon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MaterialInSalon}.
 */
public interface MaterialInSalonService {

    /**
     * Save a materialInSalon.
     *
     * @param materialInSalon the entity to save.
     * @return the persisted entity.
     */
    MaterialInSalon save(MaterialInSalon materialInSalon);

    /**
     * Get all the materialInSalons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MaterialInSalon> findAll(Pageable pageable);


    /**
     * Get the "id" materialInSalon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaterialInSalon> findOne(Long id);

    /**
     * Delete the "id" materialInSalon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
