package com.github.krzkuc1985.rest.employee.service;

import com.github.krzkuc1985.dto.address.AddressRequest;
import com.github.krzkuc1985.dto.address.AddressResponse;
import com.github.krzkuc1985.dto.employee.EmployeeRequest;
import com.github.krzkuc1985.dto.employee.EmployeeResponse;
import com.github.krzkuc1985.dto.logindata.LoginDataRequest;
import com.github.krzkuc1985.dto.logindata.LoginDataResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee findByIdOrThrowException(Long id);

    EmployeeResponse findById(Long id);

    List<EmployeeResponse> findAll();

    EmployeeResponse create(EmployeeRequest employeeRequest);

    EmployeeResponse update(Long id, EmployeeRequest employeeRequest);

    void delete(Long id);

    AddressResponse getEmployeeAddress(Long id);

    AddressResponse updateEmployeeAddress(Long id, AddressRequest addressRequest);

    LoginDataResponse updateEmployeeLoginData(Long id, LoginDataRequest loginDataRequest);

    List<RoleResponse> getEmployeeRoles(Long id);

    void addRoleToEmployee(Long id, List<RoleRequest> roleRequests);

    void removeRoleFromEmployee(Long id, List<RoleRequest> roleRequests);

}
