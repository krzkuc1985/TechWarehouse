package com.github.krzkuc1985.rest.workorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krzkuc1985.dto.workorder.WorkOrderRequest;
import com.github.krzkuc1985.dto.workorder.WorkOrderResponse;
import com.github.krzkuc1985.rest.config.JwtService;
import com.github.krzkuc1985.rest.config.SecurityConfig;
import com.github.krzkuc1985.rest.workorder.service.WorkOrderService;
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

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(WorkOrderController.class)
class WorkOrderControllerTest {

    @MockBean
    private WorkOrderService workOrderService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private WorkOrderRequest workOrderRequest;
    private WorkOrderResponse workOrderResponse;

    @BeforeEach
    void setUp() {
        workOrderRequest = new WorkOrderRequest("Replacing a faulty sensor", Instant.parse("2022-01-10T11:00:00Z"), Instant.parse("2022-01-12T14:00:00Z"), 1L, 1L, 1L);
        workOrderResponse = new WorkOrderResponse(1L, "Replacing a faulty sensor", Instant.parse("2022-01-10T11:00:00Z"), Instant.parse("2022-01-12T14:00:00Z"), 1L, 1L, 1L);
    }

    private String asJsonString(final Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(authorities = "VIEW_WORK_ORDER")
    @DisplayName("getAll should return 200 when work orders are found")
    void getAll_ReturnsListOfWorkOrders() throws Exception {
        when(workOrderService.findAll()).thenReturn(List.of(workOrderResponse));

        mockMvc.perform(get("/work-orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(workOrderService, times(1)).findAll();
    }

    @Test
    @WithMockUser(authorities = "VIEW_WORK_ORDER")
    @DisplayName("getById should return 200 when work order is found")
    void getById_ReturnsWorkOrder() throws Exception {
        when(workOrderService.findById(eq(1L))).thenReturn(workOrderResponse);

        mockMvc.perform(get("/work-orders/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Replacing a faulty sensor"))
                .andExpect(jsonPath("$.startDate").value("2022-01-10T11:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2022-01-12T14:00:00Z"))
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.type").value(1))
                .andExpect(jsonPath("$.employee").value(1));

        verify(workOrderService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "VIEW_WORK_ORDER")
    @DisplayName("getById should return 404 when work order not found")
    void getById_WorkOrderNotFound() throws Exception {
        when(workOrderService.findById(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/work-orders/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(workOrderService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "ADD_WORK_ORDER")
    @DisplayName("create should return 201 when work order is created")
    void create_ValidWorkOrderRequest() throws Exception {
        when(workOrderService.create(any(WorkOrderRequest.class))).thenReturn(workOrderResponse);

        mockMvc.perform(post("/work-orders").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Replacing a faulty sensor"))
                .andExpect(jsonPath("$.startDate").value("2022-01-10T11:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2022-01-12T14:00:00Z"))
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.type").value(1))
                .andExpect(jsonPath("$.employee").value(1));

        verify(workOrderService, times(1)).create(any(WorkOrderRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADD_WORK_ORDER")
    @DisplayName("create should return 409 when work order already exists")
    void create_WorkOrderAlreadyExists() throws Exception {
        when(workOrderService.create(any(WorkOrderRequest.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/work-orders").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.CONFLICT"));

        verify(workOrderService, times(1)).create(any(WorkOrderRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_WORK_ORDER")
    @DisplayName("update should return 200 when work order is updated")
    void update_ValidWorkOrderRequest() throws Exception {
        when(workOrderService.update(eq(1L), any(WorkOrderRequest.class))).thenReturn(workOrderResponse);

        mockMvc.perform(put("/work-orders/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Replacing a faulty sensor"))
                .andExpect(jsonPath("$.startDate").value("2022-01-10T11:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2022-01-12T14:00:00Z"))
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.type").value(1))
                .andExpect(jsonPath("$.employee").value(1));

        verify(workOrderService, times(1)).update(eq(1L), any(WorkOrderRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_WORK_ORDER")
    @DisplayName("update should return 404 when work order not found")
    void update_WorkOrderNotFound() throws Exception {
        when(workOrderService.update(eq(1L), any(WorkOrderRequest.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/work-orders/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(workOrderService, times(1)).update(eq(1L), any(WorkOrderRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_WORK_ORDER")
    @DisplayName("delete should return 204 when work order is deleted")
    void delete_WorkOrderExists() throws Exception {
        doNothing().when(workOrderService).delete(eq(1L));

        mockMvc.perform(delete("/work-orders/1"))
                .andExpect(status().isNoContent());

        verify(workOrderService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "DELETE_WORK_ORDER")
    @DisplayName("delete should return 404 when work order not found")
    void delete_WorkOrderNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(workOrderService).delete(eq(1L));

        mockMvc.perform(delete("/work-orders/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(workOrderService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "EDIT_WORK_ORDER")
    @DisplayName("update should return 409 when optimistic lock exception occurs")
    void update_ThrowsOptimisticLockException() throws Exception {
        when(workOrderService.update(eq(1L), any(WorkOrderRequest.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        mockMvc.perform(put("/work-orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.OPTIMISTIC_LOCKING_FAILURE"));

        verify(workOrderService, times(1)).update(eq(1L), any(WorkOrderRequest.class));
    }

}
