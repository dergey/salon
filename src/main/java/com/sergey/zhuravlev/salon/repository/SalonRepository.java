package com.sergey.zhuravlev.salon.repository;
import com.sergey.zhuravlev.salon.domain.Salon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Salon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {

}
