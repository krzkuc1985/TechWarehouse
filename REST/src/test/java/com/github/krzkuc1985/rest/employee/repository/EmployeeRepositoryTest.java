package com.github.krzkuc1985.rest.employee.repository;

import com.github.krzkuc1985.rest.employee.model.Employee;
import com.github.krzkuc1985.rest.personaldata.PersonalData;
import com.github.krzkuc1985.rest.address.Address;
import com.github.krzkuc1985.rest.logindata.LoginData;
import com.github.krzkuc1985.rest.role.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        PersonalData personalData = new PersonalData("John", "Doe", "555888999", "example@email.com");
        Address address = new Address("USA", "New York", "Broadway", "1", "", "10001");
        LoginData loginData = new LoginData("admin", "password123");
        Role role = new Role("EMPLOYEE", new HashSet<>());

        employee = new Employee(personalData, address, loginData, new ArrayList<>(List.of(role)));
    }

    @Test
    @DisplayName("should save and retrieve Employee by ID")
    void saveAndFindById() {
        // when
        Employee savedEmployee = employeeRepository.save(employee);
        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getId());

        // then
        assertTrue(foundEmployee.isPresent());
        assertEquals("John", foundEmployee.get().getPersonalData().getFirstName());
    }

    @Test
    @DisplayName("should delete Employee")
    void deleteEmployee() {
        // given
        Employee savedEmployee = employeeRepository.save(employee);

        // when
        employeeRepository.deleteById(savedEmployee.getId());
        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getId());

        // then
        assertFalse(foundEmployee.isPresent());
    }

    @Test
    @DisplayName("should update Employee's address")
    void updateEmployeeAddress() {
        // given
        Employee savedEmployee = employeeRepository.save(employee);

        // when
        savedEmployee.getAddress().setStreet("New Street");
        employeeRepository.save(savedEmployee);
        Optional<Employee> updatedEmployee = employeeRepository.findById(savedEmployee.getId());

        // then
        assertTrue(updatedEmployee.isPresent());
        assertEquals("New Street", updatedEmployee.get().getAddress().getStreet());
    }

    @Test
    @DisplayName("should return empty when Employee not found by ID")
    void findById_NotFound() {
        // when
        Optional<Employee> foundEmployee = employeeRepository.findById(999L);

        // then
        assertTrue(foundEmployee.isEmpty());
    }
}
