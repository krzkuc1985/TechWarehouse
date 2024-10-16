package com.github.krzkuc1985.rest.role.service;

import com.github.krzkuc1985.dto.permission.PermissionRequest;
import com.github.krzkuc1985.dto.permission.PermissionResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.permission.mapper.PermissionMapper;
import com.github.krzkuc1985.rest.permission.model.Permission;
import com.github.krzkuc1985.rest.permission.repository.PermissionRepository;
import com.github.krzkuc1985.rest.role.mapper.RoleMapper;
import com.github.krzkuc1985.rest.role.model.Role;
import com.github.krzkuc1985.rest.role.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private PermissionMapper permissionMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private RoleRequest roleRequest;
    private RoleResponse roleResponse;
    private Permission permission;
    private PermissionRequest permissionRequest;
    private PermissionResponse permissionResponse;

    @BeforeEach
    void setUp() {
        roleRequest = new RoleRequest("ADMIN");
        roleResponse = new RoleResponse(1L, "ADMIN");
        permission = new Permission("EDIT_PERMISSION", "PERMISSION");
        role = new Role("ADMIN", Set.of(permission));
        permissionRequest = new PermissionRequest("VIEW_PERMISSION", "PERMISSION");
        permissionResponse = new PermissionResponse("VIEW_PERMISSION", "PERMISSION");
    }

    private void setUpRoleWithPermissions(Set<Permission> permissions) {
        role.setPermissions(new HashSet<>(permissions));
    }

    @Test
    @DisplayName("findById should return RoleResponse when found")
    void findById_ReturnsRoleResponse() {
        when(roleRepository.findById(eq(1L))).thenReturn(Optional.of(role));
        when(roleMapper.mapToResponse(role)).thenReturn(roleResponse);

        RoleResponse result = roleService.findById(1L);

        assertEquals(roleResponse, result);
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when not found")
    void findById_ThrowsEntityNotFoundException() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.findById(1L));
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findAll should return list of RoleResponses")
    void findAll_ReturnsListOfRoleResponses() {
        List<Role> roles = List.of(role);
        List<RoleResponse> responses = List.of(roleResponse);

        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.mapToResponse(roles)).thenReturn(responses);

        List<RoleResponse> result = roleService.findAll();

        assertEquals(responses, result);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("create should save and return RoleResponse")
    void create_SavesAndReturnsRoleResponse() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(roleMapper.mapToEntity(roleRequest)).thenReturn(role);
        when(roleMapper.mapToResponse(role)).thenReturn(roleResponse);

        RoleResponse result = roleService.create(roleRequest);

        assertEquals(roleResponse, result);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    @DisplayName("update should update and return RoleResponse")
    void update_UpdatesAndReturnsRoleResponse() {
        when(roleRepository.findById(eq(1L))).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(roleMapper.mapToResponse(role)).thenReturn(roleResponse);

        RoleResponse result = roleService.update(1L, roleRequest);

        assertEquals(roleResponse, result);
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    @DisplayName("update should throw EntityNotFoundException when not found")
    void update_ThrowsEntityNotFoundException() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.update(1L, roleRequest));
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("delete should remove Role")
    void delete_RemovesRole() {
        when(roleRepository.findById(eq(1L))).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).delete(role);

        roleService.delete(1L);

        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).delete(role);
    }

    @Test
    @DisplayName("delete should throw EntityNotFoundException when not found")
    void delete_ThrowsEntityNotFoundException() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.delete(1L));
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("update should throw ObjectOptimisticLockingFailureException on optimistic lock failure")
    void update_ThrowsOptimisticLockException() {
        when(roleRepository.findById(eq(1L))).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> roleService.update(1L, roleRequest));
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    @DisplayName("getPermissionFromRole should return list of PermissionResponses")
    void getPermissionFromRole_ReturnsPermissionResponses() {
        role.setPermissions(Set.of(permission));
        List<PermissionResponse> responses = List.of(permissionResponse);

        when(roleRepository.findById(eq(1L))).thenReturn(Optional.of(role));
        when(permissionMapper.mapToResponse(role.getPermissions())).thenReturn(responses);

        List<PermissionResponse> result = roleService.getPermissionFromRole(1L);

        assertEquals(responses, result);
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("addPermissionToRole should add permissions to role")
    void addPermissionToRole_AddsPermissions() {
        when(roleRepository.findById(eq(1L))).thenReturn(Optional.of(role));
        when(permissionRepository.findByName(permissionRequest.getName())).thenReturn(Optional.of(permission));

        setUpRoleWithPermissions(role.getPermissions());

        roleService.addPermissionToRole(1L, List.of(permissionRequest));

        assertTrue(role.getPermissions().contains(permission));
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    @DisplayName("addPermissionToRole should throw EntityNotFoundException when permission not found")
    void addPermissionToRole_ThrowsExceptionWhenPermissionNotFound() {
        when(roleRepository.findById(eq(1L))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.addPermissionToRole(1L, List.of(permissionRequest)));
        verify(roleRepository, times(1)).findById(1L);
    }


    @Test
    @DisplayName("removePermissionFromRole should remove permissions from role")
    void removePermissionFromRole_RemovesPermissions() {
        role.setPermissions(Set.of(permission));

        when(roleRepository.findById(eq(1L))).thenReturn(Optional.of(role));
        when(permissionRepository.findByName(permissionRequest.getName())).thenReturn(Optional.of(permission));

        setUpRoleWithPermissions(role.getPermissions());

        roleService.removePermissionFromRole(1L, List.of(permissionRequest));

        assertFalse(role.getPermissions().contains(permission));
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    @DisplayName("removePermissionFromRole should throw EntityNotFoundException when permission not found")
    void removePermissionFromRole_ThrowsEntityNotFoundException() {
        when(roleRepository.findById(eq(1L))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.removePermissionFromRole(1L, List.of(permissionRequest)));
        verify(roleRepository, times(1)).findById(1L);
    }
}