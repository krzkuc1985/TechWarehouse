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
        ItemCategory savedItemCategory = itemCategoryRepository.save(itemCategory);
        Optional<ItemCategory> foundUnit = itemCategoryRepository.findById(savedItemCategory.getId());

        // then
        assertTrue(foundUnit.isPresent());
        assertEquals("Automation_Test", foundUnit.get().getName());
    }

    @Test
    @DisplayName("should delete ItemCategory")
    void deleteItemCategory() {
        // given
        ItemCategory savedItemCategory = itemCategoryRepository.save(itemCategory);

        // when
        itemCategoryRepository.deleteById(savedItemCategory.getId());
        Optional<ItemCategory> foundUnit = itemCategoryRepository.findById(savedItemCategory.getId());

        // then
        assertFalse(foundUnit.isPresent());
    }

    @Test
    @DisplayName("should update ItemCategory symbol")
    void updateItemCategorySymbol() {
        // given
        ItemCategory savedItemCategory = itemCategoryRepository.save(itemCategory);

        // when
        savedItemCategory.setName("Electrics_Test");
        itemCategoryRepository.save(savedItemCategory);
        Optional<ItemCategory> updatedUnit = itemCategoryRepository.findById(savedItemCategory.getId());

        // then
        assertTrue(updatedUnit.isPresent());
        assertEquals("Electrics_Test", updatedUnit.get().getName());
    }

    @Test
    @DisplayName("should return empty when ItemCategory not found")
    void findById_NotFound() {
        // when
        Optional<ItemCategory> foundUnit = itemCategoryRepository.findById(999L);

        // then
        assertTrue(foundUnit.isEmpty());
    }

}