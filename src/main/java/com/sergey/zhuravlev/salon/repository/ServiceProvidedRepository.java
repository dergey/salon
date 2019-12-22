package com.sergey.zhuravlev.salon.repository;
import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.domain.ServiceProvided;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;


/**
 * Spring Data  repository for the ServiceProvided entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceProvidedRepository extends JpaRepository<ServiceProvided, Long> {

    List<ServiceProvided> findAllByEmployee(Employee employee);
    List<ServiceProvided> findAllByEmployeeAndStartDateGreaterThanAndEndDateLessThan(Employee employee, Instant startPeriod, Instant endPeriod);

}
