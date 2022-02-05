package com.sergey.zhuravlev.salon.mapper;

import com.sergey.zhuravlev.salon.domain.Employee;
import com.sergey.zhuravlev.salon.dto.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto employeeToEmployeeDto(Employee employee);
}
