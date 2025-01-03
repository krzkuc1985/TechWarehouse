package com.github.krzkuc1985.dto.itemcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategoryRequest {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Item category", example = "Automation")
    private String name;
}
