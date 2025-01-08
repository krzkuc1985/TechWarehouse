package com.github.krzkuc1985.rest.permission.controller;

import com.github.krzkuc1985.dto.permission.PermissionResponse;
import com.github.krzkuc1985.rest.config.JwtService;
import com.github.krzkuc1985.rest.config.SecurityConfig;
import com.github.krzkuc1985.rest.permission.service.PermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(PermissionController.class)
class PermissionControllerTest {

    @MockBean
    private PermissionService permissionService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    private PermissionResponse permissionResponse;

    @BeforeEach
    void setUp() {
        permissionResponse = new PermissionResponse(1L, "VIEW_PERMISSION", "PERMISSION");
    }

    @Test
    @WithMockUser(authorities = "VIEW_PERMISSION")
    @DisplayName("getAll should return 200 when permissions are found")
    void getAll_ReturnsListOfPermissions() throws Exception {
        when(permissionService.findAll()).thenReturn(List.of(permissionResponse));

        mockMvc.perform(get("/permissions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("VIEW_PERMISSION"))
                .andExpect(jsonPath("$[0].category").value("PERMISSION"));

        verify(permissionService, times(1)).findAll();
    }

}