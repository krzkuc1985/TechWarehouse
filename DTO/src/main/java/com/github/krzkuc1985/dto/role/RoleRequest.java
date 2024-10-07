package com.github.krzkuc1985.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RoleRequest {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name of the role", example = "ADMIN")
    private String name;

}
