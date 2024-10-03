package com.github.krzkuc1985.rest.permission.mapper;

import com.github.krzkuc1985.dto.permission.PermissionResponse;
import com.github.krzkuc1985.rest.permission.model.Permission;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {

    public PermissionResponse mapToResponse(Permission Permission) {
        return PermissionResponse.builder()
                .id(Permission.getId())
                .name(Permission.getName())
                .category(Permission.getCategory())
                .build();
    }

    public List<PermissionResponse> mapToResponse(Collection<Permission> permissions) {
        return permissions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

}
