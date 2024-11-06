package com.github.krzkuc1985.rest.item.repository;

import com.github.krzkuc1985.rest.inventory.model.Inventory;
import com.github.krzkuc1985.rest.item.model.Item;
import com.github.krzkuc1985.rest.itemcategory.model.ItemCategory;
import com.github.krzkuc1985.rest.itemcategory.repository.ItemCategoryRepository;
import com.github.krzkuc1985.rest.location.model.Location;
import com.github.krzkuc1985.rest.location.repository.LocationRepository;
import com.github.krzkuc1985.rest.measurementunit.model.MeasurementUnit;
import com.github.krzkuc1985.rest.measurementunit.repository.MeasurementUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    private Item item;

    @BeforeEach
    void setUp() {
        MeasurementUnit measurementUnit = measurementUnitRepository.findById(10L).orElseThrow(() -> new RuntimeException("MeasurementUnit not found"));
        Location location = locationRepository.findById(1L).orElseThrow(() -> new RuntimeException("Location not found"));
        ItemCategory itemCategory = itemCategoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("ItemCategory not found"));
        List<Inventory> inventories = List.of();
        item = new Item("PLC Controller S7-1200", "6ES7214-1AG40-0XB0", "PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC", 2, measurementUnit, 10, 3, location, itemCategory, inventories, false);
    }

    @Test
    @DisplayName("should save and retrieve Item")
    void saveAndFindById() {
        // when
        Item saved = itemRepository.save(item);
        Optional<Item> found = itemRepository.findById(saved.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals("PLC Controller S7-1200", found.get().getName());
    }

    @Test
    @DisplayName("should delete Item")
    void deleteItem() {
        // given
        Item saved = itemRepository.save(item);

        // when
        itemRepository.deleteById(saved.getId());
        Optional<Item> found = itemRepository.findById(saved.getId());

        // then
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("should update Item")
    void updateItem() {
        // given
        Item saved = itemRepository.save(item);

        // when
        saved.setName("PLC Controller S7-1200_Test");
        itemRepository.save(saved);
        Optional<Item> updated = itemRepository.findById(saved.getId());

        // then
        assertTrue(updated.isPresent());
        assertEquals("PLC Controller S7-1200_Test", updated.get().getName());
    }

    @Test
    @DisplayName("should return empty when Item not found")
    void findById_NotFound() {
        // when
        Optional<Item> found = itemRepository.findById(999L);

        // then
        assertTrue(found.isEmpty());
    }

}