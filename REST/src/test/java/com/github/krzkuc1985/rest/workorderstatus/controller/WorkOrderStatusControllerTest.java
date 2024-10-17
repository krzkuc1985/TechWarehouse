package com.github.krzkuc1985.rest.workorderstatus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusRequest;
import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusResponse;
import com.github.krzkuc1985.rest.workorderstatus.service.WorkOrderStatusService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkOrderStatusController.class)
class WorkOrderStatusControllerTest {

    @MockBean
    private WorkOrderStatusService workOrderStatusService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private WorkOrderStatusRequest workOrderStatusRequest;
    private WorkOrderStatusResponse workOrderStatusResponse;

    @BeforeEach
    void setUp() {
        workOrderStatusRequest = new WorkOrderStatusRequest("New_Test");
        workOrderStatusResponse = new WorkOrderStatusResponse(1L, "New_Test");
    }

    private String asJsonString(final Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @DisplayName("getAll should return 200 when work order statuses are found")
    void getAll_ReturnsListOfWorkOrderStatuses() throws Exception {
        when(workOrderStatusService.findAll()).thenReturn(List.of(workOrderStatusResponse));

        mockMvc.perform(get("/work-order-statuses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(workOrderStatusService, times(1)).findAll();
    }

    @Test
    @DisplayName("getById should return 200 when work order status is found")
    void getById_ReturnsWorkOrderStatus() throws Exception {
        when(workOrderStatusService.findById(eq(1L))).thenReturn(workOrderStatusResponse);

        mockMvc.perform(get("/work-order-statuses/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("New_Test"));

        verify(workOrderStatusService, times(1)).findById(eq(1L));
    }

    @Test
    @DisplayName("getById should return 404 when work order status not found")
    void getById_WorkOrderStatusNotFound() throws Exception {
        when(workOrderStatusService.findById(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/work-order-statuses/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(workOrderStatusService, times(1)).findById(eq(1L));
    }

    @Test
    @DisplayName("create should return 201 when work order status is created")
    void create_ValidWorkOrderStatusRequest() throws Exception {
        when(workOrderStatusService.create(any(WorkOrderStatusRequest.class))).thenReturn(workOrderStatusResponse);

        mockMvc.perform(post("/work-order-statuses").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderStatusRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("New_Test"));

        verify(workOrderStatusService, times(1)).create(any(WorkOrderStatusRequest.class));
    }

    @Test
    @DisplayName("create should return 409 when work order status already exists")
    void create_WorkOrderStatusAlreadyExists() throws Exception {
        when(workOrderStatusService.create(any(WorkOrderStatusRequest.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/work-order-statuses").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderStatusRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.CONFLICT"));

        verify(workOrderStatusService, times(1)).create(any(WorkOrderStatusRequest.class));
    }

    @Test
    @DisplayName("update should return 200 when work order status is updated")
    void update_ValidWorkOrderStatusRequest() throws Exception {
        when(workOrderStatusService.update(eq(1L), any(WorkOrderStatusRequest.class))).thenReturn(workOrderStatusResponse);

        mockMvc.perform(put("/work-order-statuses/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderStatusRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("New_Test"));

        verify(workOrderStatusService, times(1)).update(eq(1L), any(WorkOrderStatusRequest.class));
    }

    @Test
    @DisplayName("update should return 404 when work order status not found")
    void update_WorkOrderStatusNotFound() throws Exception {
        when(workOrderStatusService.update(eq(1L), any(WorkOrderStatusRequest.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/work-order-statuses/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderStatusRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(workOrderStatusService, times(1)).update(eq(1L), any(WorkOrderStatusRequest.class));
    }

    @Test
    @DisplayName("delete should return 204 when work order status is deleted")
    void delete_WorkOrderStatusExists() throws Exception {
        doNothing().when(workOrderStatusService).delete(eq(1L));

        mockMvc.perform(delete("/work-order-statuses/1"))
                .andExpect(status().isNoContent());

        verify(workOrderStatusService, times(1)).delete(eq(1L));
    }

    @Test
    @DisplayName("delete should return 404 when work order status not found")
    void delete_WorkOrderStatusNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(workOrderStatusService).delete(eq(1L));

        mockMvc.perform(delete("/work-order-statuses/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(workOrderStatusService, times(1)).delete(eq(1L));
    }

    @Test
    @DisplayName("update should return 409 when optimistic lock exception occurs")
    void update_ThrowsOptimisticLockException() throws Exception {
        when(workOrderStatusService.update(eq(1L), any(WorkOrderStatusRequest.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        mockMvc.perform(put("/work-order-statuses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderStatusRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.OPTIMISTIC_LOCKING_FAILURE"));

        verify(workOrderStatusService, times(1)).update(eq(1L), any(WorkOrderStatusRequest.class));
    }

}
