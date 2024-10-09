package com.github.krzkuc1985.rest.role.controller;

import com.github.krzkuc1985.dto.permission.PermissionRequest;
import com.github.krzkuc1985.dto.permission.PermissionResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.role.service.RoleService;
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
@RequestMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Role", description = "API for managing roles")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class RoleController {

    private final RoleService service;

    @GetMapping
    @Operation(summary = "Get all roles", description = "Returns a list of all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of roles returned successfully"),
    })
    public ResponseEntity<List<RoleResponse>> getAll() {
        List<RoleResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role by ID", description = "Returns a specific role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role returned successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content),
    })
    public ResponseEntity<RoleResponse> getById(
            @Parameter(description = "ID of the role to retrieve", required = true)
            @PathVariable Long id) {
        RoleResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Create a new role", description = "Creates and returns a new role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Role already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<RoleResponse> create(
            @RequestBody(description = "Request body containing the details of the new role", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody RoleRequest roleRequest) {
        RoleResponse response = service.create(roleRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a role", description = "Updates an existing role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Role already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<RoleResponse> update(
            @Parameter(description = "ID of the role to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the updated details of the role", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody RoleRequest roleRequest) {
        RoleResponse response = service.update(id, roleRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a role", description = "Deletes an existing role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the role to delete", required = true)
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/permissions")
    @Operation(summary = "Get permissions from a role", description = "Returns a list of permissions from an existing role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of permissions returned successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
    })
    public ResponseEntity<List<PermissionResponse>> getPermissions(
            @Parameter(description = "ID of the role to retrieve permissions from", required = true)
            @PathVariable Long id) {
        List<PermissionResponse> responses = service.getPermissionFromRole(id);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/{id}/permissions")
    @Operation(summary = "Add permissions to a role", description = "Adds permissions to an existing role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permissions added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid permission data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Permission already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<Void> addPermissions(
            @Parameter(description = "ID of the role to add permissions to", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the details of the permissions to add", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody List<PermissionRequest> permissionRequests) {
        service.addPermissionToRole(id, permissionRequests);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/permissions")
    @Operation(summary = "Remove permissions from a role", description = "Removes permissions from an existing role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permissions removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid permission data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Permission already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<Void> removePermissions(
            @Parameter(description = "ID of the role to remove permissions from", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the details of the permissions to remove", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody List<PermissionRequest> permissionRequests) {
        service.removePermissionFromRole(id, permissionRequests);
        return ResponseEntity.noContent().build();
    }

}
