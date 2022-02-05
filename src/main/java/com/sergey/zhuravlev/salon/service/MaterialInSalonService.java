package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Material;
import com.sergey.zhuravlev.salon.domain.MaterialInSalon;
import com.sergey.zhuravlev.salon.domain.Salon;
import com.sergey.zhuravlev.salon.repository.MaterialInSalonRepository;
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
public class MaterialInSalonService {

    private final MaterialInSalonRepository materialInSalonRepository;

    @Transactional
    public MaterialInSalon create(Integer count, Material material, Salon salon) {
        MaterialInSalon materialInSalon = new MaterialInSalon(null, count, material, salon);
        log.debug("Request to save MaterialInSalon : {}", materialInSalon);
        return materialInSalonRepository.save(materialInSalon);
    }
    @Transactional
    public MaterialInSalon update(Long id, Integer count, Material material, Salon salon) {
        MaterialInSalon materialInSalon = materialInSalonRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(id, "MaterialInSalon"));
        materialInSalon.setCount(count);
        materialInSalon.setMaterial(material);
        materialInSalon.setSalon(salon);
        log.debug("Request to save MaterialInSalon : {}", materialInSalon);
        return materialInSalonRepository.save(materialInSalon);
    }

    @Transactional(readOnly = true)
    public Page<MaterialInSalon> findAll(Pageable pageable) {
        log.debug("Request to get all MaterialInSalons");
        return materialInSalonRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<MaterialInSalon> findOne(Long id) {
        log.debug("Request to get MaterialInSalon : {}", id);
        return materialInSalonRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete MaterialInSalon : {}", id);
        materialInSalonRepository.deleteById(id);
    }
}
