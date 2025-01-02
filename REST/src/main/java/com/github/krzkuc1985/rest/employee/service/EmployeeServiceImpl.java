package com.github.krzkuc1985.rest.employee.service;

import com.github.krzkuc1985.dto.address.AddressRequest;
import com.github.krzkuc1985.dto.address.AddressResponse;
import com.github.krzkuc1985.dto.employee.EmployeeRequest;
import com.github.krzkuc1985.dto.employee.EmployeeResponse;
import com.github.krzkuc1985.dto.logindata.LoginDataRequest;
import com.github.krzkuc1985.dto.logindata.LoginDataResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.address.mapper.AddressMapper;
import com.github.krzkuc1985.rest.employee.mapper.EmployeeMapper;
import com.github.krzkuc1985.rest.employee.model.Employee;
import com.github.krzkuc1985.rest.employee.repository.EmployeeRepository;
import com.github.krzkuc1985.rest.logindata.mapper.LoginDataMapper;
import com.github.krzkuc1985.rest.role.mapper.RoleMapper;
import com.github.krzkuc1985.rest.role.model.Role;
import com.github.krzkuc1985.rest.role.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeMapper employeeMapper;
    private final AddressMapper addressMapper;
    private final LoginDataMapper loginDataMapper;
    private final RoleMapper roleMapper;

    @Override
    public Employee findByIdOrThrowException(Long id) {
        return employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public EmployeeResponse findById(Long id) {
        return employeeMapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<EmployeeResponse> findAll() {
        return employeeMapper.mapToResponse(employeeRepository.findAll());
    }

    @Override
    @Transactional
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        return employeeMapper.mapToResponse(employeeRepository.save(employeeMapper.mapToEntity(employeeRequest)));
    }

    @Override
    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        Employee employee = findByIdOrThrowException(id);
        employeeMapper.mapToEntity(employee, employeeRequest);
        return employeeMapper.mapToResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Employee employee = findByIdOrThrowException(id);
        employeeRepository.delete(employee);
    }

    @Override
    public AddressResponse getEmployeeAddress(Long id) {
        Employee employee = findByIdOrThrowException(id);
        return addressMapper.mapToResponse(employee.getAddress());
    }

    @Override
    @Transactional
    public AddressResponse updateEmployeeAddress(Long id, AddressRequest addressRequest) {
        Employee employee = findByIdOrThrowException(id);
        employee.setAddress(addressMapper.mapToEntity(addressRequest));
        return addressMapper.mapToResponse(employeeRepository.save(employee).getAddress());
    }

    @Override
    @Transactional
    public LoginDataResponse updateEmployeeLoginData(Long id, LoginDataRequest loginDataRequest) {
        Employee employee = findByIdOrThrowException(id);
        employee.setLoginData(loginDataMapper.mapToEntity(loginDataRequest));
        return loginDataMapper.mapToResponse(employeeRepository.save(employee).getLoginData());
    }

    @Override
    public List<RoleResponse> getEmployeeRoles(Long id) {
        Employee employee = findByIdOrThrowException(id);
        return roleMapper.mapToResponse(employee.getRoles());
    }

    @Override
    @Transactional
    public void addRoleToEmployee(Long id, List<RoleRequest> roleRequests) {
        Employee employee = findByIdOrThrowException(id);
        Set<Role> roles = roleRequests.stream()
                .map(roleName -> roleRepository.findByName(roleName.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName.getName())))
                .collect(Collectors.toSet());

        employee.getRoles().addAll(roles);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void removeRoleFromEmployee(Long id, List<RoleRequest> roleRequests) {
        Employee employee = findByIdOrThrowException(id);
        Set<Role> roles = roleRequests.stream()
                .map(roleName -> roleRepository.findByName(roleName.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName.getName())))
                .collect(Collectors.toSet());

        employee.getRoles().removeAll(roles);
        employeeRepository.save(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByLoginDataLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
