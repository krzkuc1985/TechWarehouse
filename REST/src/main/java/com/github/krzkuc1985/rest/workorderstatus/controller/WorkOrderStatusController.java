package com.github.krzkuc1985.rest.workorderstatus.controller;

import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusRequest;
import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusResponse;
import com.github.krzkuc1985.rest.workorderstatus.service.WorkOrderStatusService;
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
@RequestMapping(value = "/work-order-statuses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Work Order Status", description = "API for managing work order statuses")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class WorkOrderStatusController {

    private final WorkOrderStatusService service;

    @GetMapping
    @Operation(summary = "Get all work order statuses", description = "Returns a list of all work order statuses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work order statuses returned successfully"),
    })
    public ResponseEntity<List<WorkOrderStatusResponse>> getAll() {
        List<WorkOrderStatusResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get work order status by ID", description = "Returns a specific work order status by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work order status returned successfully"),
            @ApiResponse(responseCode = "404", description = "Work order status not found", content = @Content),
    })
    public ResponseEntity<WorkOrderStatusResponse> getById(
            @Parameter(description = "ID of the work order status to retrieve", required = true)
            @PathVariable Long id) {
        WorkOrderStatusResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Create a new work order status", description = "Creates and returns a new work order status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work order status created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Work order status already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<WorkOrderStatusResponse> create(
            @RequestBody(description = "Request body containing the details of the new work order status", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody WorkOrderStatusRequest measurementUnitRequest) {
        WorkOrderStatusResponse response = service.create(measurementUnitRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update work order status", description = "Updates an existing work order status by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work order status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Work order status not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Work order status already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<WorkOrderStatusResponse> update(
            @Parameter(description = "ID of the work order status to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the updated details of the work order status", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody WorkOrderStatusRequest measurementUnitRequest) {
        WorkOrderStatusResponse response = service.update(id, measurementUnitRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete work order status", description = "Deletes an existing work order status by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Work order status deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Work order status not found", content = @Content),
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the work order status to delete", required = true)
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
