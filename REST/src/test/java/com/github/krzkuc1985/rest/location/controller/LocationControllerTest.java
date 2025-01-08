package com.github.krzkuc1985.rest.location.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krzkuc1985.dto.location.LocationRequest;
import com.github.krzkuc1985.dto.location.LocationResponse;
import com.github.krzkuc1985.rest.config.JwtService;
import com.github.krzkuc1985.rest.config.SecurityConfig;
import com.github.krzkuc1985.rest.location.service.LocationService;
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
@WebMvcTest(LocationController.class)
class LocationControllerTest {

    @MockBean
    private LocationService locationService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private LocationRequest locationRequest;
    private LocationResponse locationResponse;

    @BeforeEach
    void setUp() {
        locationRequest = new LocationRequest("A", "1");
        locationResponse = new LocationResponse(1L, "A", "1");
    }

    private String asJsonString(final Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(authorities = "VIEW_LOCATION")
    @DisplayName("getAll should return 200 when locations are found")
    void getAll_ReturnsListOfLocations() throws Exception {
        when(locationService.findAll()).thenReturn(List.of(locationResponse));

        mockMvc.perform(get("/locations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(locationService, times(1)).findAll();
    }

    @Test
    @WithMockUser(authorities = "VIEW_LOCATION")
    @DisplayName("getById should return 200 when location is found")
    void getById_ReturnsLocation() throws Exception {
        when(locationService.findById(eq(1L))).thenReturn(locationResponse);

        mockMvc.perform(get("/locations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rack").value("A"))
                .andExpect(jsonPath("$.shelf").value("1"));

        verify(locationService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "VIEW_LOCATION")
    @DisplayName("getById should return 404 when location not found")
    void getById_LocationNotFound() throws Exception {
        when(locationService.findById(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/locations/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(locationService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "ADD_LOCATION")
    @DisplayName("create should return 201 when location is created")
    void create_ValidLocationRequest() throws Exception {
        when(locationService.create(any(LocationRequest.class))).thenReturn(locationResponse);

        mockMvc.perform(post("/locations").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rack").value("A"))
                .andExpect(jsonPath("$.shelf").value("1"));

        verify(locationService, times(1)).create(any(LocationRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADD_LOCATION")
    @DisplayName("create should return 409 when location already exists")
    void create_LocationAlreadyExists() throws Exception {
        when(locationService.create(any(LocationRequest.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/locations").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locationRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.CONFLICT"));

        verify(locationService, times(1)).create(any(LocationRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_LOCATION")
    @DisplayName("update should return 200 when location is updated")
    void update_ValidLocationRequest() throws Exception {
        when(locationService.update(eq(1L), any(LocationRequest.class))).thenReturn(locationResponse);

        mockMvc.perform(put("/locations/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rack").value("A"))
                .andExpect(jsonPath("$.shelf").value("1"));

        verify(locationService, times(1)).update(eq(1L), any(LocationRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_LOCATION")
    @DisplayName("update should return 404 when location not found")
    void update_LocationNotFound() throws Exception {
        when(locationService.update(eq(1L), any(LocationRequest.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/locations/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locationRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(locationService, times(1)).update(eq(1L), any(LocationRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_LOCATION")
    @DisplayName("delete should return 204 when location is deleted")
    void delete_LocationExists() throws Exception {
        doNothing().when(locationService).delete(eq(1L));

        mockMvc.perform(delete("/locations/1"))
                .andExpect(status().isNoContent());

        verify(locationService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "DELETE_LOCATION")
    @DisplayName("delete should return 404 when location not found")
    void delete_LocationNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(locationService).delete(eq(1L));

        mockMvc.perform(delete("/locations/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(locationService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "EDIT_LOCATION")
    @DisplayName("update should return 409 when optimistic lock exception occurs")
    void update_ThrowsOptimisticLockException() throws Exception {
        when(locationService.update(eq(1L), any(LocationRequest.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        mockMvc.perform(put("/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(locationRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.OPTIMISTIC_LOCKING_FAILURE"));

        verify(locationService, times(1)).update(eq(1L), any(LocationRequest.class));
    }

}
