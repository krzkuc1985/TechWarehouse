package com.github.krzkuc1985.rest.employee.controller;

import com.github.krzkuc1985.dto.address.AddressRequest;
import com.github.krzkuc1985.dto.address.AddressResponse;
import com.github.krzkuc1985.dto.employee.EmployeeRequest;
import com.github.krzkuc1985.dto.employee.EmployeeResponse;
import com.github.krzkuc1985.dto.logindata.LoginDataRequest;
import com.github.krzkuc1985.dto.logindata.LoginDataResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee", description = "API for managing employees")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_EMPLOYEE')")
    @Operation(summary = "Get all employees", description = "Returns a list of all employees.<br>Requires authority: VIEW_EMPLOYEE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees returned successfully"),
    })
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        List<EmployeeResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_EMPLOYEE')")
    @Operation(summary = "Get employee by ID", description = "Returns a specific employee by ID.<br>Requires authority: VIEW_EMPLOYEE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee returned successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
    })
    public ResponseEntity<EmployeeResponse> getById(
            @Parameter(description = "ID of the employee to retrieve", required = true)
            @PathVariable Long id) {
        EmployeeResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_EMPLOYEE')")
    @Operation(summary = "Create employee", description = "Creates a new employee.<br>Requires authority: ADD_EMPLOYEE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid employee data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<EmployeeResponse> create(
            @RequestBody(description = "Request body containing the details of the new employee", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = service.create(employeeRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_EMPLOYEE')")
    @Operation(summary = "Update employee", description = "Updates an existing employee.<br>Requires authority: EDIT_EMPLOYEE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid employee data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<EmployeeResponse> update(
            @Parameter(description = "ID of the employee to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the updated details of the employee", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = service.update(id, employeeRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_EMPLOYEE')")
    @Operation(summary = "Delete employee", description = "Deletes an existing employee by ID.<br>Requires authority: DELETE_EMPLOYEE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the employee to delete", required = true)
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/address")
    @PreAuthorize("hasAuthority('VIEW_EMPLOYEE_ADDRESS')")
    @Operation(summary = "Get employee address", description = "Returns the address of an employee.<br>Requires authority: VIEW_EMPLOYEE_ADDRESS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee address returned successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
    })
    public ResponseEntity<AddressResponse> getAddress(
            @Parameter(description = "ID of the employee to retrieve address", required = true)
            @PathVariable Long id) {
        AddressResponse response = service.getEmployeeAddress(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/address")
    @PreAuthorize("hasAuthority('EDIT_EMPLOYEE_ADDRESS')")
    @Operation(summary = "Update employee address", description = "Updates the address of an existing employee.<br>Requires authority: EDIT_EMPLOYEE_ADDRESS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee address updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid address data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<AddressResponse> updateAddress(
            @Parameter(description = "ID of the employee to update address", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the updated details of the address", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody AddressRequest addressRequest) {
        AddressResponse addressResponse = service.updateEmployeeAddress(id, addressRequest);
        return ResponseEntity.ok().body(addressResponse);
    }

    @PutMapping("/{id}/login")
    @PreAuthorize("hasAuthority('EDIT_EMPLOYEE_LOGIN')")
    @Operation(summary = "Update employee login data", description = "Updates the login data of an existing employee.<br>Requires authority: EDIT_EMPLOYEE_LOGIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee login data updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid login data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<LoginDataResponse> updateLoginData(
            @Parameter(description = "ID of the employee to update login data", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the updated details of the login data", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody LoginDataRequest loginDataRequest) {
        LoginDataResponse loginDataResponse = service.updateEmployeeLoginData(id, loginDataRequest);
        return ResponseEntity.ok().body(loginDataResponse);
    }

    @GetMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('VIEW_EMPLOYEE_ROLE')")
    @Operation(summary = "Get employee roles", description = "Returns the roles assigned to an employee.<br>Requires authority: VIEW_EMPLOYEE_ROLE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee roles returned successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
    })
    public ResponseEntity<List<RoleResponse>> getRoles(
            @Parameter(description = "ID of the employee to retrieve roles", required = true)
            @PathVariable Long id) {
        List<RoleResponse> response = service.getEmployeeRoles(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('ADD_EMPLOYEE_ROLE')")
    @Operation(summary = "Assign role to employee", description = "Assigns a role to an employee.<br>Requires authority: ADD_EMPLOYEE_ROLE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Role already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<EmployeeResponse> assignRole(
            @Parameter(description = "ID of the employee to assign role", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the details of the role to assign", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody List<RoleRequest> roleRequest) {
        service.addRoleToEmployee(id, roleRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('DELETE_EMPLOYEE_ROLE')")
    @Operation(summary = "Remove role from employee", description = "Removes a role from an employee.<br>Requires authority: DELETE_EMPLOYEE_ROLE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Role already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<Void> removeRole(
            @Parameter(description = "ID of the employee to remove role", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the details of the role to remove", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody List<RoleRequest> roleRequest) {
        service.removeRoleFromEmployee(id, roleRequest);
        return ResponseEntity.noContent().build();
    }
}
