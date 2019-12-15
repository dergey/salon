package com.sergey.zhuravlev.salon.repository;
import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceProvided entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceProvidedRepository extends JpaRepository<ServiceProvided, Long> {

}
