package com.binhdc.springbootcommonproject.service;

import com.binhdc.springbootcommonproject.dto.request.PermissionRequest;
import com.binhdc.springbootcommonproject.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionResponse> getAll();

    void delete(String permission);

    PermissionResponse create(PermissionRequest request);
}
