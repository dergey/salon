package com.sergey.zhuravlev.salon.repository;
import com.sergey.zhuravlev.salon.domain.UsedMaterial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UsedMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsedMaterialRepository extends JpaRepository<UsedMaterial, Long> {

}
