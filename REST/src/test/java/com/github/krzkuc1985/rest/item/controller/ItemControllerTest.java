package com.github.krzkuc1985.rest.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krzkuc1985.dto.item.ItemRequest;
import com.github.krzkuc1985.dto.item.ItemResponse;
import com.github.krzkuc1985.rest.config.JwtService;
import com.github.krzkuc1985.rest.config.SecurityConfig;
import com.github.krzkuc1985.rest.item.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ItemRequest itemRequest;
    private ItemResponse itemResponse;

    @BeforeEach
    void setUp() {
        itemRequest = new ItemRequest("PLC Controller S7-1200", "6ES7214-1AG40-0XB0", "PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC", 2, 10L, 1, 3, 1L, false);
        itemResponse = new ItemResponse(1L, "PLC Controller S7-1200", "6ES7214-1AG40-0XB0", "PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC", 2, 10L, 1, 3, 1L, false);
    }

    private String asJsonString(final Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(authorities = "VIEW_ITEM")
    @DisplayName("getAll should return 200 when items are found")
    void getAll_ReturnsListOfItems() throws Exception {
        when(itemService.findAll()).thenReturn(List.of(itemResponse));

        mockMvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(itemService, times(1)).findAll();
    }

    @Test
    @WithMockUser(authorities = "VIEW_ITEM")
    @DisplayName("getById should return 200 when item is found")
    void getById_ReturnsItem() throws Exception {
        when(itemService.findById(eq(1L))).thenReturn(itemResponse);

        mockMvc.perform(get("/items/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("PLC Controller S7-1200"))
                .andExpect(jsonPath("$.orderNumber").value("6ES7214-1AG40-0XB0"))
                .andExpect(jsonPath("$.description").value("PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.measurementUnitID").value(10))
                .andExpect(jsonPath("$.minQuantity").value(1))
                .andExpect(jsonPath("$.maxQuantity").value(3))
                .andExpect(jsonPath("$.itemCategoryID").value(1))
                .andExpect(jsonPath("$.archive").value(false));

        verify(itemService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "VIEW_ITEM")
    @DisplayName("getById should return 404 when item not found")
    void getById_ItemNotFound() throws Exception {
        when(itemService.findById(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(itemService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "ADD_ITEM")
    @DisplayName("create should return 201 when item is created")
    void create_ValidItemRequest() throws Exception {
        when(itemService.create(any(ItemRequest.class))).thenReturn(itemResponse);

        mockMvc.perform(post("/items").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("PLC Controller S7-1200"))
                .andExpect(jsonPath("$.orderNumber").value("6ES7214-1AG40-0XB0"))
                .andExpect(jsonPath("$.description").value("PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.measurementUnitID").value(10))
                .andExpect(jsonPath("$.minQuantity").value(1))
                .andExpect(jsonPath("$.maxQuantity").value(3))
                .andExpect(jsonPath("$.itemCategoryID").value(1))
                .andExpect(jsonPath("$.archive").value(false));


        verify(itemService, times(1)).create(any(ItemRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADD_ITEM")
    @DisplayName("create should return 409 when item already exists")
    void create_ItemAlreadyExists() throws Exception {
        when(itemService.create(any(ItemRequest.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/items").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.CONFLICT"));

        verify(itemService, times(1)).create(any(ItemRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_ITEM")
    @DisplayName("update should return 200 when item is updated")
    void update_ValidItemRequest() throws Exception {
        when(itemService.update(eq(1L), any(ItemRequest.class))).thenReturn(itemResponse);

        mockMvc.perform(put("/items/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("PLC Controller S7-1200"))
                .andExpect(jsonPath("$.orderNumber").value("6ES7214-1AG40-0XB0"))
                .andExpect(jsonPath("$.description").value("PLC Controller S7-1200 with 14 digital inputs and 10 digital outputs. Power supply 24V DC"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.measurementUnitID").value(10))
                .andExpect(jsonPath("$.minQuantity").value(1))
                .andExpect(jsonPath("$.maxQuantity").value(3))
                .andExpect(jsonPath("$.itemCategoryID").value(1))
                .andExpect(jsonPath("$.archive").value(false));

        verify(itemService, times(1)).update(eq(1L), any(ItemRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_ITEM")
    @DisplayName("update should return 404 when item not found")
    void update_ItemNotFound() throws Exception {
        when(itemService.update(eq(1L), any(ItemRequest.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/items/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(itemService, times(1)).update(eq(1L), any(ItemRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_ITEM")
    @DisplayName("delete should return 204 when item is deleted")
    void delete_ItemExists() throws Exception {
        doNothing().when(itemService).delete(eq(1L));

        mockMvc.perform(delete("/items/1"))
                .andExpect(status().isNoContent());

        verify(itemService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "DELETE_ITEM")
    @DisplayName("delete should return 404 when item not found")
    void delete_ItemNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(itemService).delete(eq(1L));

        mockMvc.perform(delete("/items/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(itemService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "EDIT_ITEM")
    @DisplayName("update should return 409 when optimistic lock exception occurs")
    void update_ThrowsOptimisticLockException() throws Exception {
        when(itemService.update(eq(1L), any(ItemRequest.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        mockMvc.perform(put("/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.OPTIMISTIC_LOCKING_FAILURE"));

        verify(itemService, times(1)).update(eq(1L), any(ItemRequest.class));
    }

}