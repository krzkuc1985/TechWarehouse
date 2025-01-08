package com.github.krzkuc1985.rest.workordertype.controller;

import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeRequest;
import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeResponse;
import com.github.krzkuc1985.rest.workordertype.service.WorkOrderTypeService;
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
@RequestMapping(value = "/work-order-types", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Work Order Type", description = "API for managing work order types")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class WorkOrderTypeController {

    private final WorkOrderTypeService service;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_WORK_ORDER_TYPE')")
    @Operation(summary = "Get all work order types", description = "Returns a list of all work order types.<br>Requires authority: VIEW_WORK_ORDER_TYPE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work order types returned successfully"),
    })
    public ResponseEntity<List<WorkOrderTypeResponse>> getAll() {
        List<WorkOrderTypeResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_WORK_ORDER_TYPE')")
    @Operation(summary = "Get work order type by ID", description = "Returns a specific work order type by ID.<br>Requires authority: VIEW_WORK_ORDER_TYPE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work order type returned successfully"),
            @ApiResponse(responseCode = "404", description = "Work order type not found", content = @Content),
    })
    public ResponseEntity<WorkOrderTypeResponse> getById(
            @Parameter(description = "ID of the work order type to retrieve", required = true)
            @PathVariable Long id) {
        WorkOrderTypeResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_WORK_ORDER_TYPE')")
    @Operation(summary = "Create a new work order type", description = "Creates and returns a new work order type.<br>Requires authority: ADD_WORK_ORDER_TYPE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work order type created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Work order type already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<WorkOrderTypeResponse> create(
            @RequestBody(description = "Request body containing the details of the new work order type", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody WorkOrderTypeRequest measurementUnitRequest) {
        WorkOrderTypeResponse response = service.create(measurementUnitRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_WORK_ORDER_TYPE')")
    @Operation(summary = "Update work order type", description = "Updates an existing work order type by ID.<br>Requires authority: EDIT_WORK_ORDER_TYPE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work order type updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Work order type not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Work order type already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<WorkOrderTypeResponse> update(
            @Parameter(description = "ID of the work order type to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the update details of the work order type", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody WorkOrderTypeRequest measurementUnitRequest) {
        WorkOrderTypeResponse response = service.update(id, measurementUnitRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_WORK_ORDER_TYPE')")
    @Operation(summary = "Delete work order type", description = "Deletes an existing work order type by ID.<br>Requires authority: DELETE_WORK_ORDER_TYPE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Work order type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Work order type not found", content = @Content),
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the work order type to delete", required = true)
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
