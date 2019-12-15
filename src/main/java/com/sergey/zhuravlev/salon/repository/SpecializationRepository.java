package com.sergey.zhuravlev.salon.repository;
import com.sergey.zhuravlev.salon.domain.Specialization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Specialization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

}
