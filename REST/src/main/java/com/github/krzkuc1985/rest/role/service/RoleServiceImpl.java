package com.github.krzkuc1985.rest.role.service;

import com.github.krzkuc1985.dto.permission.PermissionRequest;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
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

    private final RoleRepository repository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper mapper;

    @Override
    public Role findByIdOrThrowException(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public RoleResponse findById(Long id) {
        return mapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<RoleResponse> findAll() {
        return mapper.mapToResponse(repository.findAll());
    }

    @Override
    @Transactional
    public RoleResponse create(RoleRequest roleRequest) {
        return mapper.mapToResponse(repository.save(mapper.mapToEntity(roleRequest)));
    }

    @Override
    @Transactional
    public RoleResponse update(Long id, RoleRequest roleRequest) {
        Role role = findByIdOrThrowException(id);
        role.setName(roleRequest.getName());
        return mapper.mapToResponse(repository.save(role));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Role role = findByIdOrThrowException(id);
        repository.delete(role);
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
        repository.save(role);
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
        repository.save(role);
    }
}
