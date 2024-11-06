package com.github.krzkuc1985.rest.inventory.repository;

import com.github.krzkuc1985.rest.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByWorkOrderId(Long workOrderId);

    List<Inventory> findByItemId(Long itemId);

}
