package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Country;
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
    public Salon create(String title, String address, String postalCode, String city, String stateProvince, Country country) {
        Salon salon = new Salon(null, title, new Location(null, address, postalCode, city, stateProvince, country));
        log.debug("Request to create Salon : {}", salon);
        return salonRepository.save(salon);
    }

    @Transactional
    public Salon update(Long id, String title, String address, String postalCode, String city, String stateProvince,
                        Country country) {
        Salon salon = salonRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Salon"));
        salon.setTitle(title);
        salon.getLocation().setAddress(address);
        salon.getLocation().setPostalCode(postalCode);
        salon.getLocation().setCity(city);
        salon.getLocation().setStateProvince(stateProvince);
        salon.getLocation().setCountry(country);
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
