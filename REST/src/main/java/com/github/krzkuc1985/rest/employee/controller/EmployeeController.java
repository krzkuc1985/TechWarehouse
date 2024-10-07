package com.github.krzkuc1985.rest.employee.controller;

import com.github.krzkuc1985.dto.employee.EmployeeRequest;
import com.github.krzkuc1985.dto.employee.EmployeeResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee", description = "API for managing employees")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    @Operation(summary = "Get all employees", description = "Returns a list of all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees returned successfully"),
    })
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        List<EmployeeResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Returns a specific employee by ID")
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
    @Operation(summary = "Create employee", description = "Creates a new employee")
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
    @Operation(summary = "Update employee", description = "Updates an existing employee")
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
    @Operation(summary = "Delete employee", description = "Deletes an existing employee by ID")
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

    @PostMapping("/{id}/roles")
    @Operation(summary = "Assign role to employee", description = "Assigns a role to an employee")
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
    @Operation(summary = "Remove role from employee", description = "Removes a role from an employee")
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
