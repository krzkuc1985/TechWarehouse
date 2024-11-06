package com.github.krzkuc1985.rest.inventory.service;

import com.github.krzkuc1985.rest.item.model.Item;
import com.github.krzkuc1985.rest.workorder.model.WorkOrder;
import com.github.krzkuc1985.rest.inventory.model.Inventory;
import com.github.krzkuc1985.rest.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    @Override
    public List<Inventory> findByWorkOrderId(Long workOrderId) {
        return repository.findByWorkOrderId(workOrderId);
    }

    @Override
    public List<Inventory> findByItemId(Long itemId) {
        return repository.findByItemId(itemId);
    }

    @Override
    @Transactional
    public Inventory create(WorkOrder workOrder, Item item, Integer quantity) {
        if (workOrder == null || item == null) {
            throw new IllegalArgumentException();
        }
        Inventory inventory = new Inventory(workOrder, item, quantity, Instant.now());
        return repository.save(inventory);
    }
}
