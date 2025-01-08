package com.github.krzkuc1985.rest.authentication.controller;

import com.github.krzkuc1985.dto.authentication.AuthenticationRequest;
import com.github.krzkuc1985.dto.authentication.AuthenticationResponse;
import com.github.krzkuc1985.rest.authentication.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "API for authentication")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
})
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @SecurityRequirements(value = {})
    @Operation(summary = "Login", description = "Login to the application", security = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)))
    })
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody(description = "Request body containing the details of the login", required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

}
