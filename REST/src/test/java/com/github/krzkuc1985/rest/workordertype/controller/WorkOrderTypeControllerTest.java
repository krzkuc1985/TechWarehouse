package com.github.krzkuc1985.rest.workordertype.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeRequest;
import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeResponse;
import com.github.krzkuc1985.rest.config.JwtService;
import com.github.krzkuc1985.rest.config.SecurityConfig;
import com.github.krzkuc1985.rest.workordertype.service.WorkOrderTypeService;
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
@WebMvcTest(WorkOrderTypeController.class)
class WorkOrderTypeControllerTest {

    @MockBean
    private WorkOrderTypeService workOrderTypeService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private WorkOrderTypeRequest workOrderTypeRequest;
    private WorkOrderTypeResponse workOrderTypeResponse;

    @BeforeEach
    void setUp() {
        workOrderTypeRequest = new WorkOrderTypeRequest("Repair_Test");
        workOrderTypeResponse = new WorkOrderTypeResponse(1L, "Repair_Test");
    }

    private String asJsonString(final Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(authorities = "VIEW_WORK_ORDER_TYPE")
    @DisplayName("getAll should return 200 when work order types are found")
    void getAll_ReturnsListOfWorkOrderTypes() throws Exception {
        when(workOrderTypeService.findAll()).thenReturn(List.of(workOrderTypeResponse));

        mockMvc.perform(get("/work-order-types").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(workOrderTypeService, times(1)).findAll();
    }

    @Test
    @WithMockUser(authorities = "VIEW_WORK_ORDER_TYPE")
    @DisplayName("getById should return 200 when work order type is found")
    void getById_ReturnsWorkOrderType() throws Exception {
        when(workOrderTypeService.findById(eq(1L))).thenReturn(workOrderTypeResponse);

        mockMvc.perform(get("/work-order-types/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("Repair_Test"));

        verify(workOrderTypeService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "VIEW_WORK_ORDER_TYPE")
    @DisplayName("getById should return 404 when work order type not found")
    void getById_WorkOrderTypeNotFound() throws Exception {
        when(workOrderTypeService.findById(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/work-order-types/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(workOrderTypeService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "ADD_WORK_ORDER_TYPE")
    @DisplayName("create should return 201 when work order type is created")
    void create_ValidWorkOrderTypeRequest() throws Exception {
        when(workOrderTypeService.create(any(WorkOrderTypeRequest.class))).thenReturn(workOrderTypeResponse);

        mockMvc.perform(post("/work-order-types").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderTypeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("Repair_Test"));

        verify(workOrderTypeService, times(1)).create(any(WorkOrderTypeRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADD_WORK_ORDER_TYPE")
    @DisplayName("create should return 409 when work order type already exists")
    void create_WorkOrderTypeAlreadyExists() throws Exception {
        when(workOrderTypeService.create(any(WorkOrderTypeRequest.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/work-order-types").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderTypeRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.CONFLICT"));

        verify(workOrderTypeService, times(1)).create(any(WorkOrderTypeRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_WORK_ORDER_TYPE")
    @DisplayName("update should return 200 when work order type is updated")
    void update_ValidWorkOrderTypeRequest() throws Exception {
        when(workOrderTypeService.update(eq(1L), any(WorkOrderTypeRequest.class))).thenReturn(workOrderTypeResponse);

        mockMvc.perform(put("/work-order-types/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderTypeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("Repair_Test"));

        verify(workOrderTypeService, times(1)).update(eq(1L), any(WorkOrderTypeRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_WORK_ORDER_TYPE")
    @DisplayName("update should return 404 when work order type not found")
    void update_WorkOrderTypeNotFound() throws Exception {
        when(workOrderTypeService.update(eq(1L), any(WorkOrderTypeRequest.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/work-order-types/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderTypeRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(workOrderTypeService, times(1)).update(eq(1L), any(WorkOrderTypeRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_WORK_ORDER_TYPE")
    @DisplayName("delete should return 204 when work order type is deleted")
    void delete_WorkOrderTypeExists() throws Exception {
        doNothing().when(workOrderTypeService).delete(eq(1L));

        mockMvc.perform(delete("/work-order-types/1"))
                .andExpect(status().isNoContent());

        verify(workOrderTypeService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "DELETE_WORK_ORDER_TYPE")
    @DisplayName("delete should return 404 when work order type not found")
    void delete_WorkOrderTypeNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(workOrderTypeService).delete(eq(1L));

        mockMvc.perform(delete("/work-order-types/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(workOrderTypeService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "EDIT_WORK_ORDER_TYPE")
    @DisplayName("update should return 409 when optimistic lock exception occurs")
    void update_ThrowsOptimisticLockException() throws Exception {
        when(workOrderTypeService.update(eq(1L), any(WorkOrderTypeRequest.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        mockMvc.perform(put("/work-order-types/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workOrderTypeRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.OPTIMISTIC_LOCKING_FAILURE"));

        verify(workOrderTypeService, times(1)).update(eq(1L), any(WorkOrderTypeRequest.class));
    }

}
