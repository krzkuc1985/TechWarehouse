package com.github.krzkuc1985.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name of the permission", example = "ADD_MEASUREMENT_UNIT")
    private String name;

    @NotBlank(message = "Category is mandatory")
    @Schema(description = "Category of the permission", example = "MEASUREMENT_UNIT")
    private String category;
}
