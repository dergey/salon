package com.sergey.zhuravlev.salon.repository;
import com.sergey.zhuravlev.salon.domain.Service;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Service entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

}
