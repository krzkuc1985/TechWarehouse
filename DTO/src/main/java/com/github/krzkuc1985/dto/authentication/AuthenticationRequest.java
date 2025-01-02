package com.github.krzkuc1985.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Login is mandatory")
    @Schema(description = "Login", example = "rbruk")
    private String login;

    @NotBlank(message = "Password is mandatory")
    @Schema(description = "Password", example = "aB64Dr")
    private String password;

}
