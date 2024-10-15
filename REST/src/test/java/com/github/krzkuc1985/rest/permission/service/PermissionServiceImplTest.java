package com.github.krzkuc1985.rest.permission.service;

import com.github.krzkuc1985.dto.permission.PermissionResponse;
import com.github.krzkuc1985.rest.permission.mapper.PermissionMapper;
import com.github.krzkuc1985.rest.permission.model.Permission;
import com.github.krzkuc1985.rest.permission.repository.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionServiceImplTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionMapper permissionMapper;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    private Permission permission;
    private PermissionResponse permissionResponse;

    @BeforeEach
    void setUp() {
        permission = new Permission("VIEW_PERMISSION", "PERMISSION");
        permissionResponse = new PermissionResponse(1L, "VIEW_PERMISSION", "PERMISSION");
    }

    @Test
    @DisplayName("findAll should return list of PermissionResponses")
    void findAll_ReturnsListOfPermissionResponses() {
        List<Permission> permissions = List.of(permission);
        List<PermissionResponse> permissionResponses = List.of(permissionResponse);

        when(permissionRepository.findAll()).thenReturn(permissions);
        when(permissionMapper.mapToResponse(permissions)).thenReturn(permissionResponses);

        List<PermissionResponse> result = permissionService.findAll();

        assertEquals(permissionResponses, result);
        verify(permissionRepository, times(1)).findAll();
        verify(permissionMapper, times(1)).mapToResponse(permissions);
    }
}