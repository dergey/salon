package com.sergey.zhuravlev.salon.repository;
import com.sergey.zhuravlev.salon.domain.MaterialInSalon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MaterialInSalon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialInSalonRepository extends JpaRepository<MaterialInSalon, Long> {

}
