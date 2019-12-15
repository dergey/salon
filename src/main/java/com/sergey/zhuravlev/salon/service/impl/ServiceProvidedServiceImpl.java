package com.sergey.zhuravlev.salon.service.impl;

import com.sergey.zhuravlev.salon.service.ServiceProvidedService;
import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import com.sergey.zhuravlev.salon.repository.ServiceProvidedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceProvided}.
 */
@Service
@Transactional
public class ServiceProvidedServiceImpl implements ServiceProvidedService {

    private final Logger log = LoggerFactory.getLogger(ServiceProvidedServiceImpl.class);

    private final ServiceProvidedRepository serviceProvidedRepository;

    public ServiceProvidedServiceImpl(ServiceProvidedRepository serviceProvidedRepository) {
        this.serviceProvidedRepository = serviceProvidedRepository;
    }

    /**
     * Save a serviceProvided.
     *
     * @param serviceProvided the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceProvided save(ServiceProvided serviceProvided) {
        log.debug("Request to save ServiceProvided : {}", serviceProvided);
        return serviceProvidedRepository.save(serviceProvided);
    }

    /**
     * Get all the serviceProvideds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceProvided> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceProvideds");
        return serviceProvidedRepository.findAll(pageable);
    }


    /**
     * Get one serviceProvided by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceProvided> findOne(Long id) {
        log.debug("Request to get ServiceProvided : {}", id);
        return serviceProvidedRepository.findById(id);
    }

    /**
     * Delete the serviceProvided by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceProvided : {}", id);
        serviceProvidedRepository.deleteById(id);
    }
}
