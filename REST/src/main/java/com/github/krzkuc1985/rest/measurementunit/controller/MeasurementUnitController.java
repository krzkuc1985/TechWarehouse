package com.github.krzkuc1985.rest.measurementunit.controller;

import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitRequest;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitResponse;
import com.github.krzkuc1985.rest.measurementunit.service.MeasurementUnitService;
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
@RequestMapping(value = "/measurement-units", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Measurement Unit", description = "API for managing measurement units")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class MeasurementUnitController {

    private final MeasurementUnitService service;

    @GetMapping
    @Operation(summary = "Get all measurement units", description = "Returns a list of all measurement units")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of measurement units returned successfully"),
    })
    public ResponseEntity<List<MeasurementUnitResponse>> getAll() {
        List<MeasurementUnitResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get measurement unit by ID", description = "Returns a specific measurement unit by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Measurement unit returned successfully"),
            @ApiResponse(responseCode = "404", description = "Measurement unit not found", content = @Content),
    })
    public ResponseEntity<MeasurementUnitResponse> getById(
            @Parameter(description = "ID of the measurement unit to retrieve", required = true)
            @PathVariable Long id) {
        MeasurementUnitResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Create a new measurement unit", description = "Creates and returns a new measurement unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Measurement unit created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Measurement unit already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<MeasurementUnitResponse> create(
            @RequestBody(description = "Request body containing the details of the new measurement unit", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody MeasurementUnitRequest request) {
        MeasurementUnitResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a measurement unit", description = "Updates an existing measurement unit by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Measurement unit updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Measurement unit not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Measurement unit already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<MeasurementUnitResponse> update(
            @Parameter(description = "ID of the measurement unit to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the updated details of the measurement unit", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody MeasurementUnitRequest request) {
        MeasurementUnitResponse response = service.update(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a measurement unit", description = "Deletes a measurement unit by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Measurement unit deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Measurement unit not found", content = @Content),
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the measurement unit to delete", required = true)
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
