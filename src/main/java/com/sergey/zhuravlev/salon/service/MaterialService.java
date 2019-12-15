package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Material;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Material}.
 */
public interface MaterialService {

    /**
     * Save a material.
     *
     * @param material the entity to save.
     * @return the persisted entity.
     */
    Material save(Material material);

    /**
     * Get all the materials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Material> findAll(Pageable pageable);


    /**
     * Get the "id" material.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Material> findOne(Long id);

    /**
     * Delete the "id" material.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
