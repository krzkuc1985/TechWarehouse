package com.github.krzkuc1985.rest.employee.service;

import com.github.krzkuc1985.dto.employee.EmployeeRequest;
import com.github.krzkuc1985.dto.employee.EmployeeResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.rest.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee findByIdOrThrowException(Long id);

    EmployeeResponse findById(Long id);

    List<EmployeeResponse> findAll();

    EmployeeResponse create(EmployeeRequest employeeRequest);

    EmployeeResponse update(Long id, EmployeeRequest employeeRequest);

    void delete(Long id);

    void addRoleToEmployee(Long id, List<RoleRequest> roleRequest);

    void removeRoleFromEmployee(Long id, List<RoleRequest> roleRequest);

}
