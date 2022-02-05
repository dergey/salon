package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Country;
import com.sergey.zhuravlev.salon.domain.Region;
import com.sergey.zhuravlev.salon.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional
    public Country create(String code, String name, Region region) {
        return countryRepository.save(new Country(code, name, region));
    }

    @Transactional
    public Country update(String code, String name, Region region) {
        Country country = countryRepository.findByCode(code).orElseThrow(() -> new ObjectNotFoundException(code, "Country"));
        country.setName(name);
        country.setRegion(region);
        return countryRepository.save(country);
    }

    @Transactional(readOnly = true)
    public List<Country> findAll() {
        return countryRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Optional<Country> findOne(String code) {
        return countryRepository.findByCode(code);
    }

    @Transactional
    public void delete(String code) {
        countryRepository.deleteByCode(code);
    }

}
