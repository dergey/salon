package com.sergey.zhuravlev.salon.service.impl;

import com.sergey.zhuravlev.salon.service.ServiceService;
import com.sergey.zhuravlev.salon.domain.Service;
import com.sergey.zhuravlev.salon.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Service}.
 */
@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final Logger log = LoggerFactory.getLogger(ServiceServiceImpl.class);

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Save a service.
     *
     * @param service the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Service save(Service service) {
        log.debug("Request to save Service : {}", service);
        return serviceRepository.save(service);
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Service> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return serviceRepository.findAll(pageable);
    }


    /**
     * Get one service by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Service> findOne(Long id) {
        log.debug("Request to get Service : {}", id);
        return serviceRepository.findById(id);
    }

    /**
     * Delete the service by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Service : {}", id);
        serviceRepository.deleteById(id);
    }
}
