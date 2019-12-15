package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.UsedMaterial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link UsedMaterial}.
 */
public interface UsedMaterialService {

    /**
     * Save a usedMaterial.
     *
     * @param usedMaterial the entity to save.
     * @return the persisted entity.
     */
    UsedMaterial save(UsedMaterial usedMaterial);

    /**
     * Get all the usedMaterials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsedMaterial> findAll(Pageable pageable);


    /**
     * Get the "id" usedMaterial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsedMaterial> findOne(Long id);

    /**
     * Delete the "id" usedMaterial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
