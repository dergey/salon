package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Country;
import com.sergey.zhuravlev.salon.domain.Region;
import com.sergey.zhuravlev.salon.dto.CountryCreateDto;
import com.sergey.zhuravlev.salon.dto.CountryDto;
import com.sergey.zhuravlev.salon.dto.CountryRequestDto;
import com.sergey.zhuravlev.salon.exception.BadRequestAlertException;
import com.sergey.zhuravlev.salon.mapper.CountryMapper;
import com.sergey.zhuravlev.salon.service.CountryService;
import com.sergey.zhuravlev.salon.service.RegionService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private static final String ENTITY_NAME = "country";

    private final CountryService countryService;
    private final RegionService regionService;

    private final CountryMapper countryMapper;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping
    public ResponseEntity<CountryDto> createCountry(@Valid @RequestBody CountryCreateDto dto) throws URISyntaxException {
        Region region = regionService.findOne(dto.getRegionId())
            .orElseThrow(() -> new BadRequestAlertException("Region with given ID does not exist", "region", "entitynotexist"));
        Country result = countryService.create(dto.getCode(), dto.getName(), region);
        return ResponseEntity.created(new URI("/api/countries/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getCode()))
            .body(countryMapper.countryToCountryDto(result));
    }

    @PutMapping("/{code}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable String code, @Valid @RequestBody CountryRequestDto dto) {
        Region region = regionService.findOne(dto.getRegionId()).orElse(null);
        Country result = countryService.update(code, dto.getName(), region);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, code))
            .body(countryMapper.countryToCountryDto(result));
    }

    @GetMapping
    public List<CountryDto> getAllCountries() {
        return countryService.findAll().stream().map(countryMapper::countryToCountryDto).collect(Collectors.toList());
    }

    @GetMapping("/{code}")
    public ResponseEntity<CountryDto> getCountry(@PathVariable String code) {
        log.debug("REST request to get Country : {}", code);
        Optional<Country> country = countryService.findOne(code);
        return ResponseUtil.wrapOrNotFound(country.map(countryMapper::countryToCountryDto));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String code) {
        log.debug("REST request to delete Country : {}", code);
        countryService.delete(code);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, code)).build();
    }
}
