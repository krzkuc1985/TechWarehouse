package com.github.krzkuc1985.rest.itemcategory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krzkuc1985.dto.itemcategory.ItemCategoryRequest;
import com.github.krzkuc1985.dto.itemcategory.ItemCategoryResponse;
import com.github.krzkuc1985.rest.config.JwtService;
import com.github.krzkuc1985.rest.config.SecurityConfig;
import com.github.krzkuc1985.rest.itemcategory.service.ItemCategoryService;
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
@WebMvcTest(ItemCategoryController.class)
class ItemCategoryControllerTest {

    @MockBean
    private ItemCategoryService itemCategoryService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ItemCategoryRequest itemCategoryRequest;
    private ItemCategoryResponse itemCategoryResponse;

    @BeforeEach
    void setUp() {
        itemCategoryRequest = new ItemCategoryRequest("Automation_Test");
        itemCategoryResponse = new ItemCategoryResponse(1L, "Automation_Test");
    }

    private String asJsonString(final Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(authorities = "VIEW_ITEM_CATEGORY")
    @DisplayName("getAll should return 200 when item categories are found")
    void getAll_ReturnsListOfItemCategorys() throws Exception {
        when(itemCategoryService.findAll()).thenReturn(List.of(itemCategoryResponse));

        mockMvc.perform(get("/item-categories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(itemCategoryService, times(1)).findAll();
    }

    @Test
    @WithMockUser(authorities = "VIEW_ITEM_CATEGORY")
    @DisplayName("getById should return 200 when item categorie is found")
    void getById_ReturnsItemCategory() throws Exception {
        when(itemCategoryService.findById(eq(1L))).thenReturn(itemCategoryResponse);

        mockMvc.perform(get("/item-categories/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Automation_Test"));

        verify(itemCategoryService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "VIEW_ITEM_CATEGORY")
    @DisplayName("getById should return 404 when item categorie not found")
    void getById_ItemCategoryNotFound() throws Exception {
        when(itemCategoryService.findById(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/item-categories/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(itemCategoryService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "ADD_ITEM_CATEGORY")
    @DisplayName("create should return 201 when item categorie is created")
    void create_ValidItemCategoryRequest() throws Exception {
        when(itemCategoryService.create(any(ItemCategoryRequest.class))).thenReturn(itemCategoryResponse);

        mockMvc.perform(post("/item-categories").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemCategoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Automation_Test"));

        verify(itemCategoryService, times(1)).create(any(ItemCategoryRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADD_ITEM_CATEGORY")
    @DisplayName("create should return 409 when item categorie already exists")
    void create_ItemCategoryAlreadyExists() throws Exception {
        when(itemCategoryService.create(any(ItemCategoryRequest.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/item-categories").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemCategoryRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.CONFLICT"));

        verify(itemCategoryService, times(1)).create(any(ItemCategoryRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_ITEM_CATEGORY")
    @DisplayName("update should return 200 when item categorie is updated")
    void update_ValidItemCategoryRequest() throws Exception {
        when(itemCategoryService.update(eq(1L), any(ItemCategoryRequest.class))).thenReturn(itemCategoryResponse);

        mockMvc.perform(put("/item-categories/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemCategoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Automation_Test"));

        verify(itemCategoryService, times(1)).update(eq(1L), any(ItemCategoryRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_ITEM_CATEGORY")
    @DisplayName("update should return 404 when item categorie not found")
    void update_ItemCategoryNotFound() throws Exception {
        when(itemCategoryService.update(eq(1L), any(ItemCategoryRequest.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/item-categories/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemCategoryRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(itemCategoryService, times(1)).update(eq(1L), any(ItemCategoryRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_ITEM_CATEGORY")
    @DisplayName("delete should return 204 when item categorie is deleted")
    void delete_ItemCategoryExists() throws Exception {
        doNothing().when(itemCategoryService).delete(eq(1L));

        mockMvc.perform(delete("/item-categories/1"))
                .andExpect(status().isNoContent());

        verify(itemCategoryService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "DELETE_ITEM_CATEGORY")
    @DisplayName("delete should return 404 when item categorie not found")
    void delete_ItemCategoryNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(itemCategoryService).delete(eq(1L));

        mockMvc.perform(delete("/item-categories/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(itemCategoryService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "EDIT_ITEM_CATEGORY")
    @DisplayName("update should return 409 when optimistic lock exception occurs")
    void update_ThrowsOptimisticLockException() throws Exception {
        when(itemCategoryService.update(eq(1L), any(ItemCategoryRequest.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        mockMvc.perform(put("/item-categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemCategoryRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.OPTIMISTIC_LOCKING_FAILURE"));

        verify(itemCategoryService, times(1)).update(eq(1L), any(ItemCategoryRequest.class));
    }

}