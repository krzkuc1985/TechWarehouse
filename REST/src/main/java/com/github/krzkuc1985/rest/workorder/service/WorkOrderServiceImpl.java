package com.github.krzkuc1985.rest.workorder.service;

import com.github.krzkuc1985.dto.workorder.WorkOrderRequest;
import com.github.krzkuc1985.dto.workorder.WorkOrderResponse;
import com.github.krzkuc1985.dto.inventory.InventoryResponse;
import com.github.krzkuc1985.rest.item.model.Item;
import com.github.krzkuc1985.rest.item.service.ItemService;
import com.github.krzkuc1985.rest.workorder.mapper.WorkOrderMapper;
import com.github.krzkuc1985.rest.workorder.model.WorkOrder;
import com.github.krzkuc1985.rest.workorder.repository.WorkOrderRepository;
import com.github.krzkuc1985.rest.inventory.mapper.InventoryMapper;
import com.github.krzkuc1985.rest.inventory.service.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository repository;
    private final WorkOrderMapper mapper;
    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final InventoryMapper inventoryMapper;

    @Override
    public WorkOrder findByIdOrThrowException(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WorkOrderResponse findById(Long id) {
        return mapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<WorkOrderResponse> findAll() {
        return mapper.mapToResponse(repository.findAll());
    }

    @Override
    @Transactional
    public WorkOrderResponse create(WorkOrderRequest workOrderRequest) {
        return mapper.mapToResponse(repository.save(mapper.mapToEntity(workOrderRequest)));
    }

    @Override
    @Transactional
    public WorkOrderResponse update(Long id, WorkOrderRequest workOrderRequest) {
        WorkOrder workOrder = findByIdOrThrowException(id);
        workOrder.setDescription(workOrderRequest.getDescription());
        return mapper.mapToResponse(repository.save(workOrder));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        WorkOrder workOrder = findByIdOrThrowException(id);
        repository.delete(workOrder);
    }

    @Override
    public List<InventoryResponse> getInventories(Long id) {
        return inventoryMapper.mapToResponse(inventoryService.findByWorkOrderId(id));
    }

    @Override
    @Transactional
    public InventoryResponse addInventory(Long id, Long itemId, Integer quantity) {
        WorkOrder workOrder = findByIdOrThrowException(id);
        Item item = itemService.findByIdOrThrowException(itemId);
        itemService.changeQuantity(item, quantity);
        return inventoryMapper.mapToResponse(inventoryService.create(workOrder, item, quantity));
    }
}
