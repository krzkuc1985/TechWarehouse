package com.github.krzkuc1985.dto.logindata;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDataRequest {

    @NotBlank(message = "Login is mandatory")
    @Schema(description = "Login", example = "user")
    private String login;

    @NotBlank(message = "Password is mandatory")
    @Schema(description = "Password", example = "pass")
    private String password;

}
