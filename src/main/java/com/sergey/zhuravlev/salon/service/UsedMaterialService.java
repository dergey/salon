package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.domain.Material;
import com.sergey.zhuravlev.salon.domain.Order;
import com.sergey.zhuravlev.salon.domain.UsedMaterial;
import com.sergey.zhuravlev.salon.repository.UsedMaterialRepository;
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
public class UsedMaterialService {

    private final UsedMaterialRepository usedMaterialRepository;

    @Transactional
    public UsedMaterial create(Integer count, Boolean decommission, Order order, Material material,
                               Employee employee) {
        UsedMaterial usedMaterial = new UsedMaterial(null, count, decommission, order, material, employee);
        log.debug("Request to save UsedMaterial : {}", usedMaterial);
        return usedMaterialRepository.save(usedMaterial);
    }

    @Transactional
    public UsedMaterial update(Long id, Integer count, Boolean decommission, Order order, Material material,
                               Employee employee) {
        UsedMaterial usedMaterial = usedMaterialRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(id, "UsedMaterial"));
        usedMaterial.setCount(count);
        usedMaterial.setDecommission(decommission);
        usedMaterial.setOrder(order);
        usedMaterial.setMaterial(material);
        usedMaterial.setEmployee(employee);
        log.debug("Request to save UsedMaterial : {}", usedMaterial);
        return usedMaterialRepository.save(usedMaterial);
    }

    @Transactional(readOnly = true)
    public Page<UsedMaterial> findAll(Pageable pageable) {
        log.debug("Request to get all UsedMaterials");
        return usedMaterialRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<UsedMaterial> findOne(Long id) {
        log.debug("Request to get UsedMaterial : {}", id);
        return usedMaterialRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete UsedMaterial : {}", id);
        usedMaterialRepository.deleteById(id);
    }
}
