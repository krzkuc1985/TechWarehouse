package com.github.krzkuc1985.rest.item.service;

import com.github.krzkuc1985.dto.item.ItemRequest;
import com.github.krzkuc1985.dto.item.ItemResponse;
import com.github.krzkuc1985.rest.item.mapper.ItemMapper;
import com.github.krzkuc1985.rest.item.model.Item;
import com.github.krzkuc1985.rest.item.repository.ItemRepository;
import com.github.krzkuc1985.rest.itemcategory.model.ItemCategory;
import com.github.krzkuc1985.rest.location.model.Location;
import com.github.krzkuc1985.rest.measurementunit.model.MeasurementUnit;
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
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemServiceImpl itemService;

    private Item item;
    private ItemRequest itemRequest;
    private ItemResponse itemResponse;

    @BeforeEach
    void setUp() {
        MeasurementUnit measurementUnit = new MeasurementUnit("pcs");
        Location location = new Location("A", "1");
        ItemCategory itemCategory = new ItemCategory("PLC Controllers");
        item = new Item("PLC Controller S7-1200", "6ES7214-1AG40-0XB0", "PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC", 2, measurementUnit, 1, 3, location, itemCategory, false);
        itemRequest = new ItemRequest("PLC Controller S7-1200", "6ES7214-1AG40-0XB0", "PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC", 2, 1L, 1, 3, 1L, false);
        itemResponse = new ItemResponse(1L, "PLC Controller S7-1200", "6ES7214-1AG40-0XB0", "PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC", 2, 1L, 1, 3, 1L, false);
    }

    @Test
    @DisplayName("findById should return ItemResponse when found")
    void findById_ReturnsItemResponse() {
        when(itemRepository.findById(eq(1L))).thenReturn(Optional.of(item));
        when(itemMapper.mapToResponse(item)).thenReturn(itemResponse);

        ItemResponse result = itemService.findById(1L);

        assertEquals(itemResponse, result);
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when not found")
    void findById_ThrowsEntityNotFoundException() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemService.findById(1L));
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findAll should return list of ItemResponses")
    void findAll_ReturnsListOfItemResponses() {
        List<Item> units = List.of(item);
        List<ItemResponse> responses = List.of(itemResponse);

        when(itemRepository.findAll()).thenReturn(units);
        when(itemMapper.mapToResponse(units)).thenReturn(responses);

        List<ItemResponse> result = itemService.findAll();

        assertEquals(responses, result);
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("create should save and return ItemResponse")
    void create_SavesAndReturnsItemResponse() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemMapper.mapToEntity(itemRequest)).thenReturn(item);
        when(itemMapper.mapToResponse(item)).thenReturn(itemResponse);

        ItemResponse result = itemService.create(itemRequest);

        assertEquals(itemResponse, result);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    @DisplayName("update should update and return ItemResponse")
    void update_UpdatesAndReturnsItemResponse() {
        when(itemRepository.findById(eq(1L))).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemMapper.mapToResponse(item)).thenReturn(itemResponse);

        ItemResponse result = itemService.update(1L, itemRequest);

        assertEquals(itemResponse, result);
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    @DisplayName("update should throw EntityNotFoundException when not found")
    void update_ThrowsEntityNotFoundException() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemService.update(1L, itemRequest));
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("delete should remove Item")
    void delete_RemovesItem() {
        when(itemRepository.findById(eq(1L))).thenReturn(Optional.of(item));
        doNothing().when(itemRepository).delete(item);

        itemService.delete(1L);

        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).delete(item);
    }

    @Test
    @DisplayName("delete should throw EntityNotFoundException when not found")
    void delete_ThrowsEntityNotFoundException() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> itemService.delete(1L));
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("update should throw ObjectOptimisticLockingFailureException on optimistic lock failure")
    void update_ThrowsOptimisticLockException() {
        when(itemRepository.findById(eq(1L))).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> itemService.update(1L, itemRequest));
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(item);
    }

}