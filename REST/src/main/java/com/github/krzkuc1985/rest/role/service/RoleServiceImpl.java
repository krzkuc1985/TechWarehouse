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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    @Override
    public Role findByIdOrThrowException(Long id) {
        return roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public RoleResponse findById(Long id) {
        return roleMapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleMapper.mapToResponse(roleRepository.findAll());
    }

    @Override
    @Transactional
    public RoleResponse create(RoleRequest roleRequest) {
        return roleMapper.mapToResponse(roleRepository.save(roleMapper.mapToEntity(roleRequest)));
    }

    @Override
    @Transactional
    public RoleResponse update(Long id, RoleRequest roleRequest) {
        Role role = findByIdOrThrowException(id);
        role.setName(roleRequest.getName());
        return roleMapper.mapToResponse(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Role role = findByIdOrThrowException(id);
        roleRepository.delete(role);
    }

    @Override
    public List<PermissionResponse> getPermissionFromRole(Long id) {
        Role role = findByIdOrThrowException(id);
        return permissionMapper.mapToResponse(role.getPermissions());
    }

    @Override
    @Transactional
    public void addPermissionToRole(Long id, List<PermissionRequest> permissionRequests) {
        Role role = findByIdOrThrowException(id);
        Set<Permission> permissions = permissionRequests.stream()
                .map(authorityName -> permissionRepository.findByName(authorityName.getName())
                        .orElseThrow(() -> new RuntimeException("Authority not found: " + authorityName.getName())))
                .collect(Collectors.toSet());

        role.getPermissions().addAll(permissions);
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void removePermissionFromRole(Long id, List<PermissionRequest> permissionRequests) {
        Role role = findByIdOrThrowException(id);
        Set<Permission> permissions = permissionRequests.stream()
                .map(authorityName -> permissionRepository.findByName(authorityName.getName())
                        .orElseThrow(() -> new RuntimeException("Authority not found: " + authorityName.getName())))
                .collect(Collectors.toSet());

        role.getPermissions().removeAll(permissions);
        roleRepository.save(role);
    }
}
