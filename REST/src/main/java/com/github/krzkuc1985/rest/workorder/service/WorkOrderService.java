package com.github.krzkuc1985.rest.workorder.service;

import com.github.krzkuc1985.dto.workorder.WorkOrderRequest;
import com.github.krzkuc1985.dto.workorder.WorkOrderResponse;
import com.github.krzkuc1985.dto.inventory.InventoryResponse;
import com.github.krzkuc1985.rest.workorder.model.WorkOrder;

import java.util.List;

public interface WorkOrderService {

    WorkOrder findByIdOrThrowException(Long id);

    WorkOrderResponse findById(Long id);

    List<WorkOrderResponse> findAll();

    WorkOrderResponse create(WorkOrderRequest workOrderRequest);

    WorkOrderResponse update(Long id, WorkOrderRequest workOrderRequest);

    void delete(Long id);

    List<InventoryResponse> getInventories(Long id);

    InventoryResponse addInventory(Long id, Long itemId, Integer quantity);

}
