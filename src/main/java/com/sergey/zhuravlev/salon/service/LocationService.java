package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Country;
import com.sergey.zhuravlev.salon.domain.Location;
import com.sergey.zhuravlev.salon.repository.LocationRepository;
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
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public Location create(String address, String postalCode, String city, String stateProvince, Country country) {
        Location location = new Location(null, address, postalCode, city, stateProvince, country);
        log.debug("Request to save Location : {}", location);
        return locationRepository.save(location);
    }

    @Transactional
    public Location update(Long id, String address, String postalCode, String city, String stateProvince, Country country) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Location"));
        location.setAddress(address);
        location.setPostalCode(postalCode);
        location.setCity(city);
        location.setStateProvince(stateProvince);
        location.setCountry(country);
        log.debug("Request to save Location : {}", location);
        return locationRepository.save(location);
    }

    @Transactional(readOnly = true)
    public Page<Location> findAll(Pageable pageable) {
        log.debug("Request to get all Locations");
        return locationRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Location> findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
    }
}
