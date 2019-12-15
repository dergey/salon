package com.sergey.zhuravlev.salon.service.impl;

import com.sergey.zhuravlev.salon.service.MaterialInSalonService;
import com.sergey.zhuravlev.salon.domain.MaterialInSalon;
import com.sergey.zhuravlev.salon.repository.MaterialInSalonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MaterialInSalon}.
 */
@Service
@Transactional
public class MaterialInSalonServiceImpl implements MaterialInSalonService {

    private final Logger log = LoggerFactory.getLogger(MaterialInSalonServiceImpl.class);

    private final MaterialInSalonRepository materialInSalonRepository;

    public MaterialInSalonServiceImpl(MaterialInSalonRepository materialInSalonRepository) {
        this.materialInSalonRepository = materialInSalonRepository;
    }

    /**
     * Save a materialInSalon.
     *
     * @param materialInSalon the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MaterialInSalon save(MaterialInSalon materialInSalon) {
        log.debug("Request to save MaterialInSalon : {}", materialInSalon);
        return materialInSalonRepository.save(materialInSalon);
    }

    /**
     * Get all the materialInSalons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MaterialInSalon> findAll(Pageable pageable) {
        log.debug("Request to get all MaterialInSalons");
        return materialInSalonRepository.findAll(pageable);
    }


    /**
     * Get one materialInSalon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialInSalon> findOne(Long id) {
        log.debug("Request to get MaterialInSalon : {}", id);
        return materialInSalonRepository.findById(id);
    }

    /**
     * Delete the materialInSalon by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaterialInSalon : {}", id);
        materialInSalonRepository.deleteById(id);
    }
}
