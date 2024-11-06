package com.github.krzkuc1985.rest.inventory.mapper;

import com.github.krzkuc1985.dto.inventory.InventoryResponse;
import com.github.krzkuc1985.rest.inventory.model.Inventory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryMapper {

    public InventoryResponse mapToResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .workOrderID(inventory.getWorkOrder().getId())
                .itemID(inventory.getItem().getId())
                .quantity(inventory.getQuantity())
                .date(inventory.getDate())
                .build();
    }

    public List<InventoryResponse> mapToResponse(List<Inventory> inventories) {
        return inventories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
