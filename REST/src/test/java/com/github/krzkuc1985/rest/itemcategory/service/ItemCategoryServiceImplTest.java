package com.github.krzkuc1985.rest.itemcategory.service;

import com.github.krzkuc1985.dto.itemcategory.ItemCategoryRequest;
import com.github.krzkuc1985.dto.itemcategory.ItemCategoryResponse;
import com.github.krzkuc1985.rest.itemcategory.mapper.ItemCategoryMapper;
import com.github.krzkuc1985.rest.itemcategory.model.ItemCategory;
import com.github.krzkuc1985.rest.itemcategory.repository.ItemCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCategoryServiceImplTest {

    @Mock
    private ItemCategoryRepository itemCategoryRepository;

    @Mock
    private ItemCategoryMapper itemCategoryMapper;

    @InjectMocks
    private ItemCategoryServiceImpl itemCategoryService;

    private ItemCategory itemCategory;
    private ItemCategoryRequest itemCategoryRequest;
    private ItemCategoryResponse itemCategoryResponse;

    @BeforeEach
    void setUp() {
        itemCategory = new ItemCategory("category");
        itemCategoryRequest = new ItemCategoryRequest("category");
        itemCategoryResponse = new ItemCategoryResponse(1L, "category");
    }

    @Test
    @DisplayName("findById should return ItemCategoryResponse when found")
    void findById_ReturnsItemCategoryResponse() {
        when(itemCategoryRepository.findById(eq(1L))).thenReturn(Optional.of(itemCategory));
        when(itemCategoryMapper.mapToResponse(itemCategory)).thenReturn(itemCategoryResponse);

        ItemCategoryResponse result = itemCategoryService.findById(1L);

        assertEquals(itemCategoryResponse, result);
        verify(itemCategoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when not found")
    void findById_ThrowsEntityNotFoundException() {
        when(itemCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemCategoryService.findById(1L));
        verify(itemCategoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findAll should return list of ItemCategoryResponses")
    void findAll_ReturnsListOfItemCategoryResponses() {
        List<ItemCategory> units = List.of(itemCategory);
        List<ItemCategoryResponse> responses = List.of(itemCategoryResponse);

        when(itemCategoryRepository.findAll()).thenReturn(units);
        when(itemCategoryMapper.mapToResponse(units)).thenReturn(responses);

        List<ItemCategoryResponse> result = itemCategoryService.findAll();

        assertEquals(responses, result);
        verify(itemCategoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("create should save and return ItemCategoryResponse")
    void create_SavesAndReturnsItemCategoryResponse() {
        when(itemCategoryRepository.save(any(ItemCategory.class))).thenReturn(itemCategory);
        when(itemCategoryMapper.mapToEntity(itemCategoryRequest)).thenReturn(itemCategory);
        when(itemCategoryMapper.mapToResponse(itemCategory)).thenReturn(itemCategoryResponse);

        ItemCategoryResponse result = itemCategoryService.create(itemCategoryRequest);

        assertEquals(itemCategoryResponse, result);
        verify(itemCategoryRepository, times(1)).save(itemCategory);
    }

    @Test
    @DisplayName("update should update and return ItemCategoryResponse")
    void update_UpdatesAndReturnsItemCategoryResponse() {
        when(itemCategoryRepository.findById(eq(1L))).thenReturn(Optional.of(itemCategory));
        when(itemCategoryRepository.save(any(ItemCategory.class))).thenReturn(itemCategory);
        when(itemCategoryMapper.mapToResponse(itemCategory)).thenReturn(itemCategoryResponse);

        ItemCategoryResponse result = itemCategoryService.update(1L, itemCategoryRequest);

        assertEquals(itemCategoryResponse, result);
        verify(itemCategoryRepository, times(1)).findById(1L);
        verify(itemCategoryRepository, times(1)).save(itemCategory);
    }

    @Test
    @DisplayName("update should throw EntityNotFoundException when not found")
    void update_ThrowsEntityNotFoundException() {
        when(itemCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemCategoryService.update(1L, itemCategoryRequest));
        verify(itemCategoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("delete should remove ItemCategory")
    void delete_RemovesItemCategory() {
        when(itemCategoryRepository.findById(eq(1L))).thenReturn(Optional.of(itemCategory));
        doNothing().when(itemCategoryRepository).delete(itemCategory);

        itemCategoryService.delete(1L);

        verify(itemCategoryRepository, times(1)).findById(1L);
        verify(itemCategoryRepository, times(1)).delete(itemCategory);
    }

    @Test
    @DisplayName("delete should throw EntityNotFoundException when not found")
    void delete_ThrowsEntityNotFoundException() {
        when(itemCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemCategoryService.delete(1L));
        verify(itemCategoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("update should throw ObjectOptimisticLockingFailureException on optimistic lock failure")
    void update_ThrowsOptimisticLockException() {
        when(itemCategoryRepository.findById(eq(1L))).thenReturn(Optional.of(itemCategory));
        when(itemCategoryRepository.save(any(ItemCategory.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> itemCategoryService.update(1L, itemCategoryRequest));
        verify(itemCategoryRepository, times(1)).findById(1L);
        verify(itemCategoryRepository, times(1)).save(itemCategory);
    }

}