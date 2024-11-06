package com.github.krzkuc1985.rest.workorder.controller;

import com.github.krzkuc1985.dto.workorder.WorkOrderRequest;
import com.github.krzkuc1985.dto.workorder.WorkOrderResponse;
import com.github.krzkuc1985.dto.inventory.InventoryResponse;
import com.github.krzkuc1985.rest.workorder.service.WorkOrderService;
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
@RequestMapping(value = "/work-orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Work Order", description = "API for managing work orders")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class WorkOrderController {

    private final WorkOrderService service;

    @GetMapping
    @Operation(summary = "Get all work orders", description = "Returns a list of all work orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work orders returned successfully"),
    })
    public ResponseEntity<List<WorkOrderResponse>> getAll() {
        List<WorkOrderResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get work order by ID", description = "Returns a specific work order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work order returned successfully"),
            @ApiResponse(responseCode = "404", description = "Work order not found", content = @Content),
    })
    public ResponseEntity<WorkOrderResponse> getById(
            @Parameter(description = "ID of the work order to retrieve", required = true)
            @PathVariable Long id) {
        WorkOrderResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Create a new work order", description = "Creates and returns a new work order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Work order already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<WorkOrderResponse> create(
            @RequestBody(description = "Request body containing the details of the new work order", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody WorkOrderRequest measurementUnitRequest) {
        WorkOrderResponse response = service.create(measurementUnitRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update work order", description = "Updates a existing work order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Work order not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Work order already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<WorkOrderResponse> update(
            @Parameter(description = "ID of the work order to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the updated details of the work order", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody WorkOrderRequest measurementUnitRequest) {
        WorkOrderResponse response = service.update(id, measurementUnitRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete work order", description = "Deletes a work order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Work order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Work order not found", content = @Content),
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the work order to delete", required = true)
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    @Operation(summary = "Get items", description = "Returns a list of items for a specific work order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of items returned successfully"),
            @ApiResponse(responseCode = "404", description = "Work order not found", content = @Content),
    })
    public ResponseEntity<List<InventoryResponse>> getInventories(
            @Parameter(description = "ID of the work order to retrieve items", required = true)
            @PathVariable Long id) {
        List<InventoryResponse> responses = service.getInventories(id);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/{id}/items")
    @Operation(summary = "Add item", description = "Adds a new item to a specific work order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work order item added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Work order not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Work order item already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<InventoryResponse> addInventory(
            @Parameter(description = "ID of the work order to add item", required = true)
            @PathVariable Long id,
            @Parameter(description = "ID of the item to add", required = true)
            @RequestParam Long itemId,
            @Parameter(description = "Quantity of the item to add", required = true)
            @RequestParam Integer quantity) {
        InventoryResponse response = service.addInventory(id, itemId, quantity);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }
}
