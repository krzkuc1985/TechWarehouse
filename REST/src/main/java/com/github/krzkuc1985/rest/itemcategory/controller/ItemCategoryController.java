package com.github.krzkuc1985.rest.itemcategory.controller;

import com.github.krzkuc1985.dto.itemcategory.ItemCategoryRequest;
import com.github.krzkuc1985.dto.itemcategory.ItemCategoryResponse;
import com.github.krzkuc1985.rest.itemcategory.service.ItemCategoryService;
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
@RequestMapping(value = "/item-categories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Item Category", description = "API for managing item categories")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class ItemCategoryController {

    private final ItemCategoryService service;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ITEM_CATEGORY')")
    @Operation(summary = "Get all item categories", description = "Returns a list of all item categories.<br>Requires authority: VIEW_ITEM_CATEGORY")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of item categories returned successfully"),
    })
    public ResponseEntity<List<ItemCategoryResponse>> getAll() {
        List<ItemCategoryResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_ITEM_CATEGORY')")
    @Operation(summary = "Get item category by ID", description = "Returns a specific item category by ID.<br>Requires authority: VIEW_ITEM_CATEGORY")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item category returned successfully"),
            @ApiResponse(responseCode = "404", description = "Item category not found", content = @Content),
    })
    public ResponseEntity<ItemCategoryResponse> getById(
            @Parameter(description = "ID of the item category to retrieve", required = true)
            @PathVariable Long id) {
        ItemCategoryResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_ITEM_CATEGORY')")
    @Operation(summary = "Create a new item category", description = "Creates and returns a new item category.<br>Requires authority: ADD_ITEM_CATEGORY")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item category already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<ItemCategoryResponse> create(
            @RequestBody(description = "Request body containing the details of the new item category", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody ItemCategoryRequest itemCategoryRequest) {
        ItemCategoryResponse response = service.create(itemCategoryRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_ITEM_CATEGORY')")
    @Operation(summary = "Update item category", description = "Updates an existing item category by ID.<br>Requires authority: EDIT_ITEM_CATEGORY")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item category not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item category already exists or optimistic lock conflict", content = @Content)
    })
    public ResponseEntity<ItemCategoryResponse> update(
            @Parameter(description = "ID of the item category to update", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Request body containing the updated details of the item category", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody ItemCategoryRequest measurementUnitRequest) {
        ItemCategoryResponse response = service.update(id, measurementUnitRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ITEM_CATEGORY')")
    @Operation(summary = "Delete item category", description = "Deletes a item category by ID.<br>Requires authority: DELETE_ITEM_CATEGORY")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item category not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the item category to delete", required = true)
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
