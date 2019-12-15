package com.sergey.zhuravlev.salon.service.impl;

import com.sergey.zhuravlev.salon.service.UsedMaterialService;
import com.sergey.zhuravlev.salon.domain.UsedMaterial;
import com.sergey.zhuravlev.salon.repository.UsedMaterialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UsedMaterial}.
 */
@Service
@Transactional
public class UsedMaterialServiceImpl implements UsedMaterialService {

    private final Logger log = LoggerFactory.getLogger(UsedMaterialServiceImpl.class);

    private final UsedMaterialRepository usedMaterialRepository;

    public UsedMaterialServiceImpl(UsedMaterialRepository usedMaterialRepository) {
        this.usedMaterialRepository = usedMaterialRepository;
    }

    /**
     * Save a usedMaterial.
     *
     * @param usedMaterial the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UsedMaterial save(UsedMaterial usedMaterial) {
        log.debug("Request to save UsedMaterial : {}", usedMaterial);
        return usedMaterialRepository.save(usedMaterial);
    }

    /**
     * Get all the usedMaterials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UsedMaterial> findAll(Pageable pageable) {
        log.debug("Request to get all UsedMaterials");
        return usedMaterialRepository.findAll(pageable);
    }


    /**
     * Get one usedMaterial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsedMaterial> findOne(Long id) {
        log.debug("Request to get UsedMaterial : {}", id);
        return usedMaterialRepository.findById(id);
    }

    /**
     * Delete the usedMaterial by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UsedMaterial : {}", id);
        usedMaterialRepository.deleteById(id);
    }
}
