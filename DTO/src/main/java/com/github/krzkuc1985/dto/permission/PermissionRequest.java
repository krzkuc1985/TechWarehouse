package com.github.krzkuc1985.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PermissionRequest {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name of the permission", example = "ADD_MEASUREMENT_UNIT")
    private String name;
}
