package com.sergey.zhuravlev.salon.controller;

import com.sergey.zhuravlev.salon.domain.Country;
import com.sergey.zhuravlev.salon.domain.Salon;
import com.sergey.zhuravlev.salon.dto.SalonDto;
import com.sergey.zhuravlev.salon.dto.SalonRequestDto;
import com.sergey.zhuravlev.salon.exception.BadRequestAlertException;
import com.sergey.zhuravlev.salon.mapper.SalonMapper;
import com.sergey.zhuravlev.salon.service.CountryService;
import com.sergey.zhuravlev.salon.service.SalonService;
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
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private static final String ENTITY_NAME = "salon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalonService salonService;
    private final CountryService countryService;

    private final SalonMapper salonMapper;

    @PostMapping
    public ResponseEntity<SalonDto> createSalon(@Valid @RequestBody SalonRequestDto dto) throws URISyntaxException {
        log.debug("REST request to save Salon : {}", dto);
        Country country = countryService.findOne(dto.getLocation().getCountryCode())
                .orElseThrow(() -> new BadRequestAlertException("Country with given CODE does not exist", "country", "entitynotexist"));
        Salon result = salonService.create(dto.getTitle(), dto.getLocation().getAddress(), dto.getLocation().getPostalCode(),
                dto.getLocation().getCity(), dto.getLocation().getStateProvince(), country);
        return ResponseEntity.created(new URI("/api/salons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(salonMapper.salonToSalonDto(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalonDto> updateSalon(@PathVariable Long id,
                                                @Valid @RequestBody SalonRequestDto dto) throws URISyntaxException {
        log.debug("REST request to update Salon : {}", dto);
        Country country = countryService.findOne(dto.getLocation().getCountryCode())
                .orElseThrow(() -> new BadRequestAlertException("Country with given CODE does not exist", "country", "entitynotexist"));
        Salon result = salonService.update(id, dto.getTitle(), dto.getLocation().getAddress(), dto.getLocation().getPostalCode(),
                dto.getLocation().getCity(), dto.getLocation().getStateProvince(), country);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(salonMapper.salonToSalonDto(result));
    }

    @GetMapping
    public ResponseEntity<List<SalonDto>> getAllSalons(Pageable pageable) {
        log.debug("REST request to get a page of Salons");
        Page<Salon> page = salonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.map(salonMapper::salonToSalonDto).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonDto> getSalon(@PathVariable Long id) {
        log.debug("REST request to get Salon : {}", id);
        Optional<Salon> salon = salonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salon.map(salonMapper::salonToSalonDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalon(@PathVariable Long id) {
        log.debug("REST request to delete Salon : {}", id);
        salonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
