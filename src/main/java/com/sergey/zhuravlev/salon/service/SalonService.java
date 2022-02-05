package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Location;
import com.sergey.zhuravlev.salon.domain.Salon;
import com.sergey.zhuravlev.salon.repository.SalonRepository;
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
public class SalonService {

    private final SalonRepository salonRepository;

    @Transactional
    public Salon create(String title, Location location) {
        Salon salon = new Salon(null, title, location);
        log.debug("Request to create Salon : {}", salon);
        return salonRepository.save(salon);
    }

    @Transactional
    public Salon update(Long id, String title, Location location) {
        Salon salon = salonRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Salon"));
        salon.setTitle(title);
        salon.setLocation(location);
        log.debug("Request to update Salon : {}", salon);
        return salon;
    }

    @Transactional(readOnly = true)
    public Page<Salon> findAll(Pageable pageable) {
        log.debug("Request to get all Salons");
        return salonRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Salon> findOne(Long id) {
        log.debug("Request to get Salon : {}", id);
        return salonRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Salon : {}", id);
        salonRepository.deleteById(id);
    }
}
