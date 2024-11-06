package com.github.krzkuc1985.dto.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.krzkuc1985.dto.AbstractResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryResponse extends AbstractResponse {

    @JsonProperty("workOrderID")
    @Schema(description = "Work order ID", example = "1")
    private Long workOrderID;

    @JsonProperty("itemID")
    @Schema(description = "Item ID", example = "1")
    private Long itemID;

    @JsonProperty("quantity")
    @Schema(description = "Quantity of the item", example = "5")
    private Integer quantity;

    @JsonProperty("date")
    @Schema(description = "Date of adding/deleting items from work order", example = "2021-07-01T12:00:00Z")
    private Instant date;

    @Builder
    public InventoryResponse(Long id, Long workOrderID, Long itemID, Integer quantity, Instant date) {
        super(id);
        this.workOrderID = workOrderID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.date = date;
    }
}
