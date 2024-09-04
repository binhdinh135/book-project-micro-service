package com.binhdc.springbootcommonproject.service;

import com.binhdc.springbootcommonproject.dto.request.RoleRequest;
import com.binhdc.springbootcommonproject.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    void delete(String role);
}
