package com.github.krzkuc1985.rest.permission.controller;

import com.github.krzkuc1985.dto.permission.PermissionResponse;
import com.github.krzkuc1985.rest.permission.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/permission", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Permission", description = "API for managing permissions")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
})
public class PermissionController {

    private final PermissionService service;

    @GetMapping
    @Operation(summary = "Get all permissions", description = "Returns a list of all permissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of permissions returned successfully"),
    })
    public ResponseEntity<List<PermissionResponse>> getAll() {
        List<PermissionResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

}
