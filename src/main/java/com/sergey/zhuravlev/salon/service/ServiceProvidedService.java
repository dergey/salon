package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.domain.Order;
import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import com.sergey.zhuravlev.salon.repository.ServiceProvidedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceProvidedService {

    private final ServiceProvidedRepository serviceProvidedRepository;

    @Transactional
    public ServiceProvided create(Instant startDate, Instant endDate, String note, Order order, Employee employee,
                                  com.sergey.zhuravlev.salon.domain.Service service) {
        ServiceProvided serviceProvided = new ServiceProvided(null, startDate, endDate, note, order, employee, service);
        log.debug("Request to save ServiceProvided : {}", serviceProvided);
        return serviceProvidedRepository.save(serviceProvided);
    }

    @Transactional
    public ServiceProvided update(Long id, Instant startDate, Instant endDate, String note, Order order, Employee employee,
                                  com.sergey.zhuravlev.salon.domain.Service service) {
        ServiceProvided serviceProvided = serviceProvidedRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(id, "ServiceProvided"));
        serviceProvided.setStartDate(startDate);
        serviceProvided.setEndDate(endDate);
        serviceProvided.setNote(note);
        serviceProvided.setOrder(order);
        serviceProvided.setEmployee(employee);
        serviceProvided.setService(service);
        log.debug("Request to save ServiceProvided : {}", serviceProvided);
        return serviceProvidedRepository.save(serviceProvided);
    }


    @Transactional(readOnly = true)
    public Page<ServiceProvided> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceProvideds");
        return serviceProvidedRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<ServiceProvided> findOne(Long id) {
        log.debug("Request to get ServiceProvided : {}", id);
        return serviceProvidedRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete ServiceProvided : {}", id);
        serviceProvidedRepository.deleteById(id);
    }

}
