package com.github.krzkuc1985.dto.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {

    @JsonProperty("accessToken")
    @Schema(description = "Access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1")
    private String accessToken;

    @JsonProperty("refreshToken")
    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1")
    private String refreshToken;

}
