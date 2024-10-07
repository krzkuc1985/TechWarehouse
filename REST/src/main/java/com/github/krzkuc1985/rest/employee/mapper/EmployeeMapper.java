package com.github.krzkuc1985.rest.employee.mapper;

import com.github.krzkuc1985.dto.employee.EmployeeRequest;
import com.github.krzkuc1985.dto.employee.EmployeeResponse;
import com.github.krzkuc1985.rest.address.mapper.AddressMapper;
import com.github.krzkuc1985.rest.employee.model.Employee;
import com.github.krzkuc1985.rest.logindata.mapper.LoginDataMapper;
import com.github.krzkuc1985.rest.personaldata.mapper.PersonalDataMapper;
import com.github.krzkuc1985.rest.role.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final PersonalDataMapper personalDataMapper;
    private final AddressMapper addressDataMapper;
    private final LoginDataMapper loginDataMapper;
    private final RoleMapper roleMapper;

    public EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .personalDataResponse(personalDataMapper.mapToResponse(employee.getPersonalData()))
                .addressResponse(addressDataMapper.mapToResponse(employee.getAddress()))
                .loginDataResponse(loginDataMapper.mapToResponse(employee.getLoginData()))
                .roles(roleMapper.mapToResponse(employee.getRoles()))
                .build();
    }

    public List<EmployeeResponse> mapToResponse(Collection<Employee> employees) {
        return employees.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Employee mapToEntity(Employee employee, EmployeeRequest employeeRequest) {
        employee.setPersonalData(personalDataMapper.mapToEntity(employee.getPersonalData(), employeeRequest.getPersonalDataRequest()));
        return employee;
    }

    public Employee mapToEntity(EmployeeRequest employeeRequest) {
        return mapToEntity(new Employee(), employeeRequest);
    }
}
