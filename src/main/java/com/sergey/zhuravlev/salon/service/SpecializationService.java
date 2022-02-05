package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.domain.Specialization;
import com.sergey.zhuravlev.salon.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    @Transactional
    public Specialization create(String note, Employee employee, com.sergey.zhuravlev.salon.domain.Service service) {
        Specialization specialization = new Specialization(null, note, employee, service);
        log.debug("Request to save Specialization : {}", specialization);
        return specializationRepository.save(specialization);
    }

    @Transactional
    public Specialization update(Long id, String note, Employee employee,
                                 com.sergey.zhuravlev.salon.domain.Service service) {
        Specialization specialization = specializationRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(id, "Specialization"));
        specialization.setNote(note);
        specialization.setEmployee(employee);
        specialization.setService(service);
        log.debug("Request to save Specialization : {}", specialization);
        return specializationRepository.save(specialization);
    }

    @Transactional(readOnly = true)
    public Page<Specialization> findAll(Pageable pageable) {
        log.debug("Request to get all Specializations");
        return specializationRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Specialization> findOne(Long id) {
        log.debug("Request to get Specialization : {}", id);
        return specializationRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Specialization : {}", id);
        specializationRepository.deleteById(id);
    }
}
