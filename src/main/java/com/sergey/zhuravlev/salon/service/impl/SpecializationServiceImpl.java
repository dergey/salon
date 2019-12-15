package com.sergey.zhuravlev.salon.service.impl;

import com.sergey.zhuravlev.salon.service.SpecializationService;
import com.sergey.zhuravlev.salon.domain.Specialization;
import com.sergey.zhuravlev.salon.repository.SpecializationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Specialization}.
 */
@Service
@Transactional
public class SpecializationServiceImpl implements SpecializationService {

    private final Logger log = LoggerFactory.getLogger(SpecializationServiceImpl.class);

    private final SpecializationRepository specializationRepository;

    public SpecializationServiceImpl(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    /**
     * Save a specialization.
     *
     * @param specialization the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Specialization save(Specialization specialization) {
        log.debug("Request to save Specialization : {}", specialization);
        return specializationRepository.save(specialization);
    }

    /**
     * Get all the specializations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Specialization> findAll(Pageable pageable) {
        log.debug("Request to get all Specializations");
        return specializationRepository.findAll(pageable);
    }


    /**
     * Get one specialization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Specialization> findOne(Long id) {
        log.debug("Request to get Specialization : {}", id);
        return specializationRepository.findById(id);
    }

    /**
     * Delete the specialization by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Specialization : {}", id);
        specializationRepository.deleteById(id);
    }
}
