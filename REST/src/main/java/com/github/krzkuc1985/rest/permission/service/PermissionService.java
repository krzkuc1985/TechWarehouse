package com.github.krzkuc1985.rest.permission.service;

import com.github.krzkuc1985.dto.permission.PermissionResponse;

import java.util.List;

public interface PermissionService {

    List<PermissionResponse> findAll();

}
