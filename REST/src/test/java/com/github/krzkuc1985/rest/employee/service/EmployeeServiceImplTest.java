package com.github.krzkuc1985.rest.employee.service;

import com.github.krzkuc1985.dto.address.AddressRequest;
import com.github.krzkuc1985.dto.address.AddressResponse;
import com.github.krzkuc1985.dto.employee.EmployeeRequest;
import com.github.krzkuc1985.dto.employee.EmployeeResponse;
import com.github.krzkuc1985.dto.logindata.LoginDataRequest;
import com.github.krzkuc1985.dto.logindata.LoginDataResponse;
import com.github.krzkuc1985.dto.personaldata.PersonalDataRequest;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.address.mapper.AddressMapper;
import com.github.krzkuc1985.rest.employee.mapper.EmployeeMapper;
import com.github.krzkuc1985.rest.employee.model.Employee;
import com.github.krzkuc1985.rest.employee.repository.EmployeeRepository;
import com.github.krzkuc1985.rest.logindata.mapper.LoginDataMapper;
import com.github.krzkuc1985.rest.permission.model.Permission;
import com.github.krzkuc1985.rest.personaldata.PersonalData;
import com.github.krzkuc1985.rest.role.mapper.RoleMapper;
import com.github.krzkuc1985.rest.role.model.Role;
import com.github.krzkuc1985.rest.role.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private LoginDataMapper loginDataMapper;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeRequest employeeRequest;
    private EmployeeResponse employeeResponse;
    private Role role;
    private RoleRequest roleRequest;
    private RoleResponse roleResponse;
    private AddressRequest addressRequest;
    private AddressResponse addressResponse;
    private LoginDataRequest loginDataRequest;
    private LoginDataResponse loginDataResponse;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employeeRequest = new EmployeeRequest(new PersonalDataRequest("John", "Doe", "555888999", "example@email.com"));
        employeeResponse = new EmployeeResponse();
        role = new Role("ADMIN", Set.of(new Permission("EDIT_PERMISSION", "PERMISSION")));
        roleRequest = new RoleRequest("ADMIN");
        roleResponse = new RoleResponse(1L, "ADMIN");
        addressRequest = new AddressRequest("USA", "New York", "Broadway", "1", "", "10001");
        addressResponse = new AddressResponse("USA", "New York", "Broadway", "1", "", "10001");
        loginDataRequest = new LoginDataRequest("admin", "password123");
        loginDataResponse = new LoginDataResponse("admin");

        employee.setPersonalData(new PersonalData());
        employee.setRoles(new ArrayList<>(List.of(role)));
    }

    @Test
    @DisplayName("findById should return EmployeeResponse when found")
    void findById_ReturnsEmployeeResponse() {
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.of(employee));
        when(employeeMapper.mapToResponse(employee)).thenReturn(employeeResponse);

        EmployeeResponse result = employeeService.findById(1L);

        assertEquals(employeeResponse, result);
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when not found")
    void findById_ThrowsEntityNotFoundException() {
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.findById(1L));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findAll should return list of EmployeeResponses")
    void findAll_ReturnsListOfEmployeeResponses() {
        List<Employee> employees = List.of(employee);
        List<EmployeeResponse> responses = List.of(employeeResponse);

        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeMapper.mapToResponse(employees)).thenReturn(responses);

        List<EmployeeResponse> result = employeeService.findAll();

        assertEquals(responses, result);
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("create should save and return EmployeeResponse")
    void create_SavesAndReturnsEmployeeResponse() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.mapToEntity(employeeRequest)).thenReturn(employee);
        when(employeeMapper.mapToResponse(employee)).thenReturn(employeeResponse);

        EmployeeResponse result = employeeService.create(employeeRequest);

        assertEquals(employeeResponse, result);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("update should update and return EmployeeResponse")
    void update_UpdatesAndReturnsEmployeeResponse() {
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.mapToResponse(employee)).thenReturn(employeeResponse);

        EmployeeResponse result = employeeService.update(1L, employeeRequest);

        assertEquals(employeeResponse, result);
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("delete should remove Employee")
    void delete_RemovesEmployee() {
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.delete(1L);

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    @DisplayName("addRoleToEmployee should add roles to employee")
    void addRoleToEmployee_AddsRoles() {
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.of(employee));
        when(roleRepository.findByName(roleRequest.getName())).thenReturn(Optional.of(role));

        employeeService.addRoleToEmployee(1L, List.of(roleRequest));

        assertTrue(employee.getRoles().contains(role));
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("removeRoleFromEmployee should remove roles from employee")
    void removeRoleFromEmployee_RemovesRoles() {
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.of(employee));
        when(roleRepository.findByName(roleRequest.getName())).thenReturn(Optional.of(role));

        employeeService.removeRoleFromEmployee(1L, List.of(roleRequest));

        assertFalse(employee.getRoles().contains(role));
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("getEmployeeAddress should return address response when employee found")
    void getEmployeeAddress_ReturnsAddressResponse() {
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.of(employee));
        when(addressMapper.mapToResponse(employee.getAddress())).thenReturn(addressResponse);

        AddressResponse result = employeeService.getEmployeeAddress(1L);

        assertEquals(addressResponse, result);
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("updateEmployeeAddress should update and return address response")
    void updateEmployeeAddress_UpdatesAndReturnsAddressResponse() {
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(addressMapper.mapToResponse(employee.getAddress())).thenReturn(addressResponse);

        AddressResponse result = employeeService.updateEmployeeAddress(1L, addressRequest);

        assertEquals(addressResponse, result);
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("updateEmployeeLoginData should update and return login data response")
    void updateEmployeeLoginData_UpdatesAndReturnsLoginDataResponse() {
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(loginDataMapper.mapToResponse(employee.getLoginData())).thenReturn(loginDataResponse);

        LoginDataResponse result = employeeService.updateEmployeeLoginData(1L, loginDataRequest);

        assertEquals(loginDataResponse, result);
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("getEmployeeRoles should return list of role responses when employee found")
    void getEmployeeRoles_ReturnsRoleResponses() {
        List<Role> roles = employee.getRoles();
        List<RoleResponse> roleResponses = List.of(roleResponse);

        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.of(employee));
        when(roleMapper.mapToResponse(roles)).thenReturn(roleResponses);

        List<RoleResponse> result = employeeService.getEmployeeRoles(1L);

        assertEquals(roleResponses, result);
        verify(employeeRepository, times(1)).findById(1L);
    }
}