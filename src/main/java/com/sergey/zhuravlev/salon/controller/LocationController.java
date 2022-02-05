package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Country;
import com.sergey.zhuravlev.salon.domain.Location;
import com.sergey.zhuravlev.salon.dto.LocationDto;
import com.sergey.zhuravlev.salon.dto.LocationRequestDto;
import com.sergey.zhuravlev.salon.exception.BadRequestAlertException;
import com.sergey.zhuravlev.salon.mapper.LocationMapper;
import com.sergey.zhuravlev.salon.service.CountryService;
import com.sergey.zhuravlev.salon.service.LocationService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private static final String ENTITY_NAME = "location";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryService countryService;
    private final LocationService locationService;

    private final LocationMapper locationMapper;

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save Location : {}", dto);
        Country country = countryService.findOne(dto.getCountryCode())
            .orElseThrow(() -> new BadRequestAlertException("Country with given CODE does not exist", "country", "entitynotexist"));
        Location result = locationService.create(dto.getAddress(), dto.getPostalCode(), dto.getCity(),
            dto.getStateProvince(), country);
        return ResponseEntity.created(new URI("/api/locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(locationMapper.locationToLocationDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable Long id,
                                                      @Valid @RequestBody LocationRequestDto dto) throws URISyntaxException {
        log.debug("REST request to update Location : {}", dto);
        Country country = countryService.findOne(dto.getCountryCode())
            .orElseThrow(() -> new BadRequestAlertException("Country with given CODE does not exist", "country", "entitynotexist"));
        Location result = locationService.update(id, dto.getAddress(), dto.getPostalCode(), dto.getCity(),
            dto.getStateProvince(), country);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(locationMapper.locationToLocationDto(result));
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations(Pageable pageable) {
        log.debug("REST request to get a page of Locations");
        Page<Location> page = locationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(locationMapper::locationToLocationDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable Long id) {
        log.debug("REST request to get Location : {}", id);
        Optional<Location> location = locationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(location.map(locationMapper::locationToLocationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        log.debug("REST request to delete Location : {}", id);
        locationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
