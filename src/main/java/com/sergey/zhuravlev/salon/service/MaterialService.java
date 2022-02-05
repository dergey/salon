package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Material;
import com.sergey.zhuravlev.salon.domain.enumeration.Unit;
import com.sergey.zhuravlev.salon.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Transactional
    public Material create(String title, Unit unit, BigDecimal price) {
        Material material = new Material(null, title, unit, price);
        log.debug("Request to save Material : {}", material);
        return materialRepository.save(material);
    }

    @Transactional
    public Material update(Long id, String title, Unit unit, BigDecimal price) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Material"));
        material.setTitle(title);
        material.setUnit(unit);
        material.setPrice(price);
        log.debug("Request to save Material : {}", material);
        return materialRepository.save(material);
    }

    @Transactional(readOnly = true)
    public Page<Material> findAll(Pageable pageable) {
        log.debug("Request to get all Materials");
        return materialRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Material> findOne(Long id) {
        log.debug("Request to get Material : {}", id);
        return materialRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.deleteById(id);
    }
}
