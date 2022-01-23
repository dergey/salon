package com.sergey.zhuravlev.salon.service.impl;

import com.sergey.zhuravlev.salon.domain.enumeration.RegionStatus;
import com.sergey.zhuravlev.salon.service.RegionService;
import com.sergey.zhuravlev.salon.domain.Region;
import com.sergey.zhuravlev.salon.repository.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Region}.
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    private final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);

    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Region> findAll() {
        log.debug("Request to get all Regions");
        return regionRepository.findAll();
    }


    /**
     * Get one region by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Region> findOne(Long id) {
        log.debug("Request to get Region : {}", id);
        return regionRepository.findById(id);
    }

    @Override
    public void activate(Long id) {
        log.debug("Request to activate Region : {}", id);
        Region region = regionRepository.getOne(id);
        region.setStatus(RegionStatus.ACTIVATED);
    }

    @Override
    public void deactivate(Long id) {
        log.debug("Request to deactivate Region : {}", id);
        Region region = regionRepository.getOne(id);
        region.setStatus(RegionStatus.DEACTIVATED);
    }
}
