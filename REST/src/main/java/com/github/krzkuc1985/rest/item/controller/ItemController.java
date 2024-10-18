package com.github.krzkuc1985.rest.item.controller;

import com.github.krzkuc1985.dto.item.ItemRequest;
import com.github.krzkuc1985.dto.item.ItemResponse;
import com.github.krzkuc1985.rest.item.service.ItemService;
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
@RequestMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Item", description = "API for managing items")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class ItemController {

    private final ItemService service;

    @GetMapping
    @Operation(summary = "Get all items", description = "Returns a list of all items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of items returned successfully"),
    })
    public ResponseEntity<List<ItemResponse>> getAll() {
        List<ItemResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID", description = "Returns a specific item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item returned successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
    })
    public ResponseEntity<ItemResponse> getById(
            @Parameter(description = "ID of the item to retrieve", required = true)
            @PathVariable Long id) {
        ItemResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Create a new item", description = "Creates and returns a new item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<ItemResponse> create(
            @RequestBody(description = "Request body containing the details of the new item", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody ItemRequest itemRequest) {
        ItemResponse response = service.create(itemRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update item", description = "Updates a existing item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item already exists or optimistic lock conflict", content = @Content),
    })
    public ResponseEntity<ItemResponse> update(
            @Parameter(description = "ID of the item to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the details of the item to update", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody ItemRequest itemRequest) {
        ItemResponse response = service.update(id, itemRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete item", description = "Deletes a item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the item to delete", required = true)
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
