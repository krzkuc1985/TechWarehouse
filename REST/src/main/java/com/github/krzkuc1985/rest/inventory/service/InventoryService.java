package com.github.krzkuc1985.rest.inventory.service;

import com.github.krzkuc1985.rest.item.model.Item;
import com.github.krzkuc1985.rest.workorder.model.WorkOrder;
import com.github.krzkuc1985.rest.inventory.model.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> findByWorkOrderId(Long workOrderId);

    List<Inventory> findByItemId(Long itemId);

    Inventory create(WorkOrder workOrder, Item item, Integer quantity);
}
