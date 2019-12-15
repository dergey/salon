package com.sergey.zhuravlev.salon.service.impl;

import com.sergey.zhuravlev.salon.service.MaterialService;
import com.sergey.zhuravlev.salon.domain.Material;
import com.sergey.zhuravlev.salon.repository.MaterialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Material}.
 */
@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * Save a material.
     *
     * @param material the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Material save(Material material) {
        log.debug("Request to save Material : {}", material);
        return materialRepository.save(material);
    }

    /**
     * Get all the materials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Material> findAll(Pageable pageable) {
        log.debug("Request to get all Materials");
        return materialRepository.findAll(pageable);
    }


    /**
     * Get one material by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Material> findOne(Long id) {
        log.debug("Request to get Material : {}", id);
        return materialRepository.findById(id);
    }

    /**
     * Delete the material by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.deleteById(id);
    }
}
