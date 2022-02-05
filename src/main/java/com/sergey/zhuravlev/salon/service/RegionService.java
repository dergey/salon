package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Region;
import com.sergey.zhuravlev.salon.domain.enumeration.RegionStatus;
import com.sergey.zhuravlev.salon.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<Region> findAll() {
        log.debug("Request to get all Regions");
        return regionRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Optional<Region> findOne(Long id) {
        log.debug("Request to get Region : {}", id);
        return regionRepository.findById(id);
    }

    @Transactional
    public void activate(Long id) {
        log.debug("Request to activate Region : {}", id);
        Region region = regionRepository.getOne(id);
        region.setStatus(RegionStatus.ACTIVATED);
    }

    @Transactional
    public void deactivate(Long id) {
        log.debug("Request to deactivate Region : {}", id);
        Region region = regionRepository.getOne(id);
        region.setStatus(RegionStatus.DEACTIVATED);
    }
}
