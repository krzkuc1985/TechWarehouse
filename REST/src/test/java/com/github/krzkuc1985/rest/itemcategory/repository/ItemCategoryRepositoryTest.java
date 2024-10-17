package com.github.krzkuc1985.rest.itemcategory.repository;

import com.github.krzkuc1985.rest.itemcategory.model.ItemCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemCategoryRepositoryTest {

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    private ItemCategory itemCategory;

    @BeforeEach
    void setUp() {
        itemCategory = new ItemCategory("Automation_Test");
    }

    @Test
    @DisplayName("should save and retrieve ItemCategory")
    void saveAndFindById() {
        // when
        ItemCategory saved = itemCategoryRepository.save(itemCategory);
        Optional<ItemCategory> found = itemCategoryRepository.findById(saved.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals("Automation_Test", found.get().getName());
    }

    @Test
    @DisplayName("should delete ItemCategory")
    void deleteItemCategory() {
        // given
        ItemCategory saved = itemCategoryRepository.save(itemCategory);

        // when
        itemCategoryRepository.deleteById(saved.getId());
        Optional<ItemCategory> found = itemCategoryRepository.findById(saved.getId());

        // then
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("should update ItemCategory symbol")
    void updateItemCategorySymbol() {
        // given
        ItemCategory saved = itemCategoryRepository.save(itemCategory);

        // when
        saved.setName("Electrics_Test");
        itemCategoryRepository.save(saved);
        Optional<ItemCategory> updated = itemCategoryRepository.findById(saved.getId());

        // then
        assertTrue(updated.isPresent());
        assertEquals("Electrics_Test", updated.get().getName());
    }

    @Test
    @DisplayName("should return empty when ItemCategory not found")
    void findById_NotFound() {
        // when
        Optional<ItemCategory> found = itemCategoryRepository.findById(999L);

        // then
        assertTrue(found.isEmpty());
    }

}