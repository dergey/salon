package com.sergey.zhuravlev.salon.service;

import com.sergey.zhuravlev.salon.domain.Region;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Region}.
 */
public interface RegionService {

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    List<Region> findAll();


    /**
     * Get the "id" region.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Region> findOne(Long id);

    /**
     * Activate region.
     *
     * @param id the id of the entity.
     */
    void activate(Long id);

    /**
     * Deactivate region.
     *
     * @param id the id of the entity.
     */
    void deactivate(Long id);

}
