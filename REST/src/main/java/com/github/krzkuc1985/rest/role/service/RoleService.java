package com.github.krzkuc1985.rest.role.service;

import com.github.krzkuc1985.dto.permission.PermissionRequest;
import com.github.krzkuc1985.dto.permission.PermissionResponse;
import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.role.model.Role;

import java.util.List;

public interface RoleService {

    Role findByIdOrThrowException(Long id);

    RoleResponse findById(Long id);

    List<RoleResponse> findAll();

    RoleResponse create(RoleRequest roleRequest);

    RoleResponse update(Long id, RoleRequest roleRequest);

    void delete(Long id);

    List<PermissionResponse> getPermissionFromRole(Long id);

    void addPermissionToRole(Long id, List<PermissionRequest> permissionRequest);

    void removePermissionFromRole(Long id, List<PermissionRequest> permissionRequests);

}
