package com.github.krzkuc1985.rest.measurementunit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitRequest;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitResponse;
import com.github.krzkuc1985.rest.measurementunit.service.MeasurementUnitService;
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

@WebMvcTest(MeasurementUnitController.class)
class MeasurementUnitControllerTest {

    @MockBean
    private MeasurementUnitService measurementUnitService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MeasurementUnitRequest measurementUnitRequest;
    private MeasurementUnitResponse measurementUnitResponse;

    @BeforeEach
    void setUp() {
        measurementUnitRequest = new MeasurementUnitRequest("kg");
        measurementUnitResponse = new MeasurementUnitResponse(1L, "kg");
    }

    private String asJsonString(final Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @DisplayName("getAll should return 200 when measurement units are found")
    void getAll_ReturnsListOfMeasurementUnits() throws Exception {
        when(measurementUnitService.findAll()).thenReturn(List.of(measurementUnitResponse));

        mockMvc.perform(get("/measurement-units").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(measurementUnitService, times(1)).findAll();
    }

    @Test
    @DisplayName("getById should return 200 when measurement unit is found")
    void getById_ReturnsMeasurementUnit() throws Exception {
        when(measurementUnitService.findById(eq(1L))).thenReturn(measurementUnitResponse);

        mockMvc.perform(get("/measurement-units/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.symbol").value("kg"));

        verify(measurementUnitService, times(1)).findById(eq(1L));
    }

    @Test
    @DisplayName("getById should return 404 when measurement unit not found")
    void getById_MeasurementUnitNotFound() throws Exception {
        when(measurementUnitService.findById(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/measurement-units/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(measurementUnitService, times(1)).findById(eq(1L));
    }

    @Test
    @DisplayName("create should return 201 when measurement unit is created")
    void create_ValidMeasurementUnitRequest() throws Exception {
        when(measurementUnitService.create(any(MeasurementUnitRequest.class))).thenReturn(measurementUnitResponse);

        mockMvc.perform(post("/measurement-units").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(measurementUnitRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.symbol").value("kg"));

        verify(measurementUnitService, times(1)).create(any(MeasurementUnitRequest.class));
    }

    @Test
    @DisplayName("create should return 409 when measurement unit already exists")
    void create_MeasurementUnitAlreadyExists() throws Exception {
        when(measurementUnitService.create(any(MeasurementUnitRequest.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/measurement-units").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(measurementUnitRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.CONFLICT"));

        verify(measurementUnitService, times(1)).create(any(MeasurementUnitRequest.class));
    }

    @Test
    @DisplayName("update should return 200 when measurement unit is updated")
    void update_ValidMeasurementUnitRequest() throws Exception {
        when(measurementUnitService.update(eq(1L), any(MeasurementUnitRequest.class))).thenReturn(measurementUnitResponse);

        mockMvc.perform(put("/measurement-units/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(measurementUnitRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.symbol").value("kg"));

        verify(measurementUnitService, times(1)).update(eq(1L), any(MeasurementUnitRequest.class));
    }

    @Test
    @DisplayName("update should return 404 when measurement unit not found")
    void update_MeasurementUnitNotFound() throws Exception {
        when(measurementUnitService.update(eq(1L), any(MeasurementUnitRequest.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/measurement-units/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(measurementUnitRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(measurementUnitService, times(1)).update(eq(1L), any(MeasurementUnitRequest.class));
    }

    @Test
    @DisplayName("delete should return 204 when measurement unit is deleted")
    void delete_MeasurementUnitExists() throws Exception {
        doNothing().when(measurementUnitService).delete(eq(1L));

        mockMvc.perform(delete("/measurement-units/1"))
                .andExpect(status().isNoContent());

        verify(measurementUnitService, times(1)).delete(eq(1L));
    }

    @Test
    @DisplayName("delete should return 404 when measurement unit not found")
    void delete_MeasurementUnitNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(measurementUnitService).delete(eq(1L));

        mockMvc.perform(delete("/measurement-units/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(measurementUnitService, times(1)).delete(eq(1L));
    }

    @Test
    @DisplayName("update should return 409 when optimistic lock exception occurs")
    void update_ThrowsOptimisticLockException() throws Exception {
        when(measurementUnitService.update(eq(1L), any(MeasurementUnitRequest.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        mockMvc.perform(put("/measurement-units/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(measurementUnitRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.OPTIMISTIC_LOCKING_FAILURE"));

        verify(measurementUnitService, times(1)).update(eq(1L), any(MeasurementUnitRequest.class));
    }

}