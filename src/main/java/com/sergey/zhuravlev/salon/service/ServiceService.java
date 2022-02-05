package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Service;
import com.sergey.zhuravlev.salon.domain.enumeration.Sex;
import com.sergey.zhuravlev.salon.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Transactional
    public Service create(String title, Sex sex, BigDecimal price) {
        Service service = new Service(null, title, sex, price);
        log.debug("Request to save Service : {}", service);
        return serviceRepository.save(service);
    }

    @Transactional
    public Service update(Long id, String title, Sex sex, BigDecimal price) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Service"));
        service.setTitle(title);
        service.setSex(sex);
        service.setPrice(price);
        log.debug("Request to save Service : {}", service);
        return serviceRepository.save(service);
    }

    @Transactional(readOnly = true)
    public Page<Service> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return serviceRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Service> findOne(Long id) {
        log.debug("Request to get Service : {}", id);
        return serviceRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Service : {}", id);
        serviceRepository.deleteById(id);
    }
}
