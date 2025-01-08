package com.github.krzkuc1985.rest.role.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krzkuc1985.dto.permission.PermissionRequest;
import com.github.krzkuc1985.dto.permission.PermissionResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.config.JwtService;
import com.github.krzkuc1985.rest.config.SecurityConfig;
import com.github.krzkuc1985.rest.role.service.RoleService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @MockBean
    private RoleService roleService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RoleRequest roleRequest;
    private RoleResponse roleResponse;
    private PermissionResponse permissionResponse;
    private List<PermissionResponse> permissionResponses;
    private List<PermissionRequest> permissionRequests;

    @BeforeEach
    void setUp() {
        roleRequest = new RoleRequest("ADMIN");
        roleResponse = new RoleResponse(1L, "ADMIN");
        permissionResponses = Arrays.asList(
                new PermissionResponse("READ_PRIVILEGES", "PRIVILEGES"),
                new PermissionResponse("WRITE_PRIVILEGES", "PRIVILEGES")
        );
        permissionRequests = Arrays.asList(
                new PermissionRequest("READ_PRIVILEGES", "PRIVILEGES"),
                new PermissionRequest("WRITE_PRIVILEGES", "PRIVILEGES")
        );
    }

    private String asJsonString(final Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @WithMockUser(authorities = "VIEW_ROLE")
    @DisplayName("getAll should return 200 when roles are found")
    void getAll_ReturnsListOfRoles() throws Exception {
        when(roleService.findAll()).thenReturn(List.of(roleResponse));

        mockMvc.perform(get("/roles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(roleService, times(1)).findAll();
    }

    @Test
    @WithMockUser(authorities = "VIEW_ROLE")
    @DisplayName("getById should return 200 when role is found")
    void getById_ReturnsRole() throws Exception {
        when(roleService.findById(eq(1L))).thenReturn(roleResponse);

        mockMvc.perform(get("/roles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("ADMIN"));

        verify(roleService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "VIEW_ROLE")
    @DisplayName("getById should return 404 when role not found")
    void getById_RoleNotFound() throws Exception {
        when(roleService.findById(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/roles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(roleService, times(1)).findById(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "ADD_ROLE")
    @DisplayName("create should return 201 when role is created")
    void create_ValidRoleRequest() throws Exception {
        when(roleService.create(any(RoleRequest.class))).thenReturn(roleResponse);

        mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(roleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("ADMIN"));

        verify(roleService, times(1)).create(any(RoleRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADD_ROLE")
    @DisplayName("create should return 409 when role already exists")
    void create_RoleAlreadyExists() throws Exception {
        when(roleService.create(any(RoleRequest.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(roleRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.CONFLICT"));

        verify(roleService, times(1)).create(any(RoleRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_ROLE")
    @DisplayName("update should return 200 when role is updated")
    void update_ValidRoleRequest() throws Exception {
        when(roleService.update(eq(1L), any(RoleRequest.class))).thenReturn(roleResponse);

        mockMvc.perform(put("/roles/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(roleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("ADMIN"));

        verify(roleService, times(1)).update(eq(1L), any(RoleRequest.class));
    }

    @Test
    @WithMockUser(authorities = "EDIT_ROLE")
    @DisplayName("update should return 404 when role not found")
    void update_RoleNotFound() throws Exception {
        when(roleService.update(eq(1L), any(RoleRequest.class))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/roles/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(roleRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(roleService, times(1)).update(eq(1L), any(RoleRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DELETE_ROLE")
    @DisplayName("delete should return 204 when role is deleted")
    void delete_RoleExists() throws Exception {
        doNothing().when(roleService).delete(eq(1L));

        mockMvc.perform(delete("/roles/1"))
                .andExpect(status().isNoContent());

        verify(roleService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "DELETE_ROLE")
    @DisplayName("delete should return 404 when role not found")
    void delete_RoleNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(roleService).delete(eq(1L));

        mockMvc.perform(delete("/roles/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(roleService, times(1)).delete(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "VIEW_ROLE_PERMISSION")
    @DisplayName("getPermissions should return 200 when permissions are found for a role")
    void getPermissions_ReturnsPermissions() throws Exception {
        when(roleService.getPermissionFromRole(eq(1L))).thenReturn(permissionResponses);

        mockMvc.perform(get("/roles/1/permissions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("READ_PRIVILEGES"));

        verify(roleService, times(1)).getPermissionFromRole(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "VIEW_ROLE_PERMISSION")
    @DisplayName("getPermissions should return 404 when role not found")
    void getPermissions_RoleNotFound() throws Exception {
        when(roleService.getPermissionFromRole(eq(1L))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/roles/1/permissions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(roleService, times(1)).getPermissionFromRole(eq(1L));
    }

    @Test
    @WithMockUser(authorities = "ADD_ROLE_PERMISSION")
    @DisplayName("addPermissions should return 204 when permissions are added successfully")
    void addPermissions_ValidRequest() throws Exception {
        doNothing().when(roleService).addPermissionToRole(eq(1L), anyList());

        mockMvc.perform(post("/roles/1/permissions").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(permissionRequests)))
                .andExpect(status().isNoContent());

        verify(roleService, times(1)).addPermissionToRole(eq(1L), anyList());
    }

    @Test
    @WithMockUser(authorities = "DELETE_ROLE_PERMISSION")
    @DisplayName("removePermissions should return 204 when permissions are removed successfully")
    void removePermissions_ValidRequest() throws Exception {
        doNothing().when(roleService).removePermissionFromRole(eq(1L), anyList());

        mockMvc.perform(delete("/roles/1/permissions").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(permissionRequests)))
                .andExpect(status().isNoContent());

        verify(roleService, times(1)).removePermissionFromRole(eq(1L), anyList());
    }

    @Test
    @WithMockUser(authorities = "DELETE_ROLE_PERMISSION")
    @DisplayName("removePermissions should return 404 when role not found")
    void removePermissions_RoleNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(roleService).removePermissionFromRole(eq(1L), anyList());

        mockMvc.perform(delete("/roles/1/permissions").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(permissionRequests)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.NOT_FOUND"));

        verify(roleService, times(1)).removePermissionFromRole(eq(1L), anyList());
    }

    @Test
    @WithMockUser(authorities = "EDIT_ROLE")
    @DisplayName("update should return 409 when optimistic locking failure occurs")
    void update_ThrowsOptimisticLockingFailureException() throws Exception {
        when(roleService.update(eq(1L), any(RoleRequest.class))).thenThrow(ObjectOptimisticLockingFailureException.class);

        mockMvc.perform(put("/roles/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(roleRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("ERROR.GENERAL.OPTIMISTIC_LOCKING_FAILURE"));

        verify(roleService, times(1)).update(eq(1L), any(RoleRequest.class));
    }
}
