package com.github.krzkuc1985.rest.location.controller;

import com.github.krzkuc1985.dto.location.LocationRequest;
import com.github.krzkuc1985.dto.location.LocationResponse;
import com.github.krzkuc1985.rest.location.service.LocationService;
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
@RequestMapping(value = "/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Location", description = "API for managing locations")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class LocationController {

    private final LocationService service;

    @GetMapping
    @Operation(summary = "Get all locations", description = "Returns a list of all locations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of locations returned successfully"),
    })
    public ResponseEntity<List<LocationResponse>> getAll() {
        List<LocationResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get location by ID", description = "Returns a specific location by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location returned successfully"),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content),
    })
    public ResponseEntity<LocationResponse> getById(
            @Parameter(description = "ID of the location to retrieve", required = true)
            @PathVariable Long id) {
        LocationResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Create a new location", description = "Creates and returns a new location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Location already exists", content = @Content),
    })
    public ResponseEntity<LocationResponse> create(
            @RequestBody(description = "Request body containing the details of the new location", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody LocationRequest measurementUnitRequest) {
        LocationResponse response = service.create(measurementUnitRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update location", description = "Updates an existing location by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Location already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<LocationResponse> update(
            @Parameter(description = "ID of the location to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the updated details of the location", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody LocationRequest measurementUnitRequest) {
        LocationResponse response = service.update(id, measurementUnitRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete location", description = "Deletes an existing location by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content),
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the location to delete", required = true)
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
