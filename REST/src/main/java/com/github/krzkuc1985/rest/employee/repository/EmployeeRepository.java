package com.github.krzkuc1985.rest.employee.repository;

import com.github.krzkuc1985.rest.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByLoginDataLogin(String login);
    
}
