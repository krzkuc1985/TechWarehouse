package com.github.krzkuc1985.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name of the item", example = "PLC Controller S7-1200")
    private String name;

    @NotBlank(message = "Order number is mandatory")
    @Schema(description = "Order number of the item", example = "6ES7214-1AG40-0XB0")
    private String orderNumber;

    @Schema(description = "Description of the item", example = "PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC")
    private String description;

    @NotNull(message = "Quantity is mandatory")
    @Schema(description = "Quantity of the item", example = "2")
    private Integer quantity;

    @NotNull(message = "Measurement unit ID is mandatory")
    @Schema(description = "Measurement unit ID of the item", example = "1")
    private Long measurementUnitID;

    @NotNull(message = "Minimum quantity is mandatory")
    @Schema(description = "Minimum quantity of the item", example = "1")
    private Integer minQuantity;

    @NotNull(message = "Maximum quantity is mandatory")
    @Schema(description = "Maximum quantity of the item", example = "3")
    private Integer maxQuantity;

    @NotNull(message = "Category ID is mandatory")
    @Schema(description = "Category ID of the item", example = "1")
    private Long itemCategoryID;

    @NotNull(message = "Archive status is mandatory")
    @Schema(description = "Archive status of the item", example = "false")
    private Boolean archive;

}
