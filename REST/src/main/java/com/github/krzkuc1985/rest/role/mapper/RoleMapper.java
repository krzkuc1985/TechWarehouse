package com.github.krzkuc1985.rest.role.mapper;

import com.github.krzkuc1985.dto.role.RoleRequest;
import com.github.krzkuc1985.dto.role.RoleResponse;
import com.github.krzkuc1985.rest.permission.mapper.PermissionMapper;
import com.github.krzkuc1985.rest.role.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RoleMapper {

    private final PermissionMapper permissionMapper;

    public RoleResponse mapToResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(permissionMapper.mapToResponse(role.getPermissions()))
                .build();
    }

    public List<RoleResponse> mapToResponse(Collection<Role> roles) {
        return roles.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Role mapToEntity(Role role, RoleRequest roleRequest) {
        role.setName(roleRequest.getName());
        role.setPermissions(Set.of());
        return role;
    }

    public Role mapToEntity(RoleRequest roleRequest) {
        return mapToEntity(new Role(), roleRequest);
    }
}
