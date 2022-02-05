package com.sergey.zhuravlev.salon.repository;

import com.sergey.zhuravlev.salon.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Country entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    Optional<Country> findByCode(String code);

    void deleteByCode(String code);
}
