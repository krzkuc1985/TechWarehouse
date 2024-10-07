package com.github.krzkuc1985.rest.employee.service;

import com.github.krzkuc1985.dto.employee.EmployeeRequest;
import com.github.krzkuc1985.dto.employee.EmployeeResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.rest.employee.mapper.EmployeeMapper;
import com.github.krzkuc1985.rest.employee.model.Employee;
import com.github.krzkuc1985.rest.employee.repository.EmployeeRepository;
import com.github.krzkuc1985.rest.role.model.Role;
import com.github.krzkuc1985.rest.role.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final RoleRepository roleRepository;
    private final EmployeeMapper mapper;

    @Override
    public Employee findByIdOrThrowException(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public EmployeeResponse findById(Long id) {
        return mapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<EmployeeResponse> findAll() {
        return mapper.mapToResponse(repository.findAll());
    }

    @Override
    @Transactional
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        return mapper.mapToResponse(repository.save(mapper.mapToEntity(employeeRequest)));
    }

    @Override
    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        Employee employee = findByIdOrThrowException(id);
        employee.getPersonalData().setFirstName(employeeRequest.getPersonalDataRequest().getFirstName());
        employee.getPersonalData().setLastName(employeeRequest.getPersonalDataRequest().getLastName());
        return mapper.mapToResponse(repository.save(employee));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Employee employee = findByIdOrThrowException(id);
        repository.delete(employee);
    }

    @Override
    @Transactional
    public void addRoleToEmployee(Long id, List<RoleRequest> roleRequest) {
        Employee employee = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<Role> roles = roleRequest.stream()
                .map(roleName -> roleRepository.findByName(roleName.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName.getName())))
                .collect(Collectors.toSet());

        employee.getRoles().addAll(roles);
        repository.save(employee);
    }

    @Override
    @Transactional
    public void removeRoleFromEmployee(Long id, List<RoleRequest> roleRequest) {
        Employee employee = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<Role> roles = roleRequest.stream()
                .map(roleName -> roleRepository.findByName(roleName.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName.getName())))
                .collect(Collectors.toSet());

        employee.getRoles().removeAll(roles);
        repository.save(employee);
    }
}
