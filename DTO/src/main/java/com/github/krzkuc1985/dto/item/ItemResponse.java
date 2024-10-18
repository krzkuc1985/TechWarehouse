package com.github.krzkuc1985.dto.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.krzkuc1985.dto.AbstractResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponse extends AbstractResponse {

    @JsonProperty("name")
    @Schema(description = "Name of the item", example = "PLC Controller S7-1200")
    private String name;

    @JsonProperty("orderNumber")
    @Schema(description = "Order number of the item", example = "6ES7214-1AG40-0XB0")
    private String orderNumber;

    @JsonProperty("description")
    @Schema(description = "Description of the item", example = "PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC")
    private String description;

    @JsonProperty("quantity")
    @Schema(description = "Quantity of the item", example = "2")
    private Integer quantity;

    @JsonProperty("measurementUnitID")
    @Schema(description = "Measurement unit ID of the item", example = "1")
    private Long measurementUnitID;

    @JsonProperty("minQuantity")
    @Schema(description = "Minimum quantity of the item", example = "1")
    private Integer minQuantity;

    @JsonProperty("maxQuantity")
    @Schema(description = "Maximum quantity of the item", example = "3")
    private Integer maxQuantity;

    @JsonProperty("itemCategoryID")
    @Schema(description = "Category ID of the item", example = "1")
    private Long itemCategoryID;

    @JsonProperty("archive")
    @Schema(description = "Archive status of the item", example = "false")
    private Boolean archive;

    @Builder
    public ItemResponse(Long id, String name, String orderNumber, String description, Integer quantity, Long measurementUnitID, Integer minQuantity, Integer maxQuantity, Long itemCategoryID, Boolean archive) {
        super(id);
        this.name = name;
        this.orderNumber = orderNumber;
        this.description = description;
        this.quantity = quantity;
        this.measurementUnitID = measurementUnitID;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
        this.itemCategoryID = itemCategoryID;
        this.archive = archive;
    }

}
