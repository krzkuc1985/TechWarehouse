package com.github.krzkuc1985.rest.permission.service;

import com.github.krzkuc1985.dto.permission.PermissionResponse;
import com.github.krzkuc1985.rest.permission.mapper.PermissionMapper;
import com.github.krzkuc1985.rest.permission.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public List<PermissionResponse> findAll() {
        return permissionMapper.mapToResponse(permissionRepository.findAll());
    }

}
