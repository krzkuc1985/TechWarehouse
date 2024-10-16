package com.github.krzkuc1985.rest.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krzkuc1985.dto.address.AddressResponse;
import com.github.krzkuc1985.dto.employee.EmployeeRequest;
import com.github.krzkuc1985.dto.employee.EmployeeResponse;
import com.github.krzkuc1985.dto.logindata.LoginDataResponse;
import com.github.krzkuc1985.dto.personaldata.PersonalDataRequest;
import com.github.krzkuc1985.dto.personaldata.PersonalDataResponse;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.employee.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeRequest employeeRequest;
    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {
        PersonalDataRequest personalDataRequest = new PersonalDataRequest("John", "Doe", "+48123456789", "j.doe@example.com");
        PersonalDataResponse personalDataResponse = new PersonalDataResponse("John", "Doe", "+48123456789", "j.doe@example.com");
        AddressResponse addressResponse = new AddressResponse("USA", "New York", "Broadway", "17", "2", "09-001");
        LoginDataResponse loginDataResponse = new LoginDataResponse("john.doe");
        List<RoleResponse> roleResponses = List.of(new RoleResponse(1L, "ADMIN"));
        employeeRequest = new EmployeeRequest(personalDataRequest);
        employeeResponse =  new EmployeeResponse(1L, personalDataResponse, addressResponse ,loginDataResponse, roleResponses);
    }

    private String asJsonString(final Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @DisplayName("getAll should return 200 when employees are found")
    void getAll_ReturnsListOfEmployees() throws Exception {
        when(employeeService.findAll()).thenReturn(List.of(employeeResponse));

        mockMvc.perform(get("/employee").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(employeeService, times(1)).findAll();
    }

    @Test
    @DisplayName("getById should return 200 when employee is found")
    void getById_ReturnsEmployee() throws Exception {
        when(employeeService.findById(eq(1L))).thenReturn(employeeResponse);

        mockMvc.perform(get("/employee/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.PersonalData.firstName").value("John"));

        verify(employeeService, times(1)).findById(eq(1L));
    }

    @Test
    @DisplayName("getById should return 404 when employee not found")
    void getById_EmployeeNotFound() throws Exception {
        when(employeeService.findById(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(employeeService, times(1)).findById(eq(1L));
    }

    @Test
    @DisplayName("create should return 201 when employee is created")
    void create_ValidEmployeeRequest() throws Exception {
        when(employeeService.create(any(EmployeeRequest.class))).thenReturn(employeeResponse);

        mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.PersonalData.firstName").value("John"));

        verify(employeeService, times(1)).create(any(EmployeeRequest.class));
    }

    @Test
    @DisplayName("create should return 409 when employee already exists")
    void create_EmployeeAlreadyExists() throws Exception {
        when(employeeService.create(any(EmployeeRequest.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.CONFLICT"));

        verify(employeeService, times(1)).create(any(EmployeeRequest.class));
    }

    @Test
    @DisplayName("update should return 200 when employee is updated")
    void update_ValidEmployeeRequest() throws Exception {
        when(employeeService.update(eq(1L), any(EmployeeRequest.class))).thenReturn(employeeResponse);

        mockMvc.perform(put("/employee/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.PersonalData.firstName").value("John"));

        verify(employeeService, times(1)).update(eq(1L), any(EmployeeRequest.class));
    }

    @Test
    @DisplayName("update should return 404 when employee not found")
    void update_EmployeeNotFound() throws Exception {
        when(employeeService.update(eq(1L), any(EmployeeRequest.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/employee/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(employeeService, times(1)).update(eq(1L), any(EmployeeRequest.class));
    }

    @Test
    @DisplayName("delete should return 204 when employee is deleted")
    void delete_EmployeeExists() throws Exception {
        doNothing().when(employeeService).delete(eq(1L));

        mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).delete(eq(1L));
    }

    @Test
    @DisplayName("delete should return 404 when employee not found")
    void delete_EmployeeNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(employeeService).delete(eq(1L));

        mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(employeeService, times(1)).delete(eq(1L));
    }
}