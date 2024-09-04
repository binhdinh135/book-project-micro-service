package com.binhdc.springbootcommonproject.service.impl;

import com.binhdc.springbootcommonproject.dto.request.PermissionRequest;
import com.binhdc.springbootcommonproject.dto.response.PermissionResponse;
import com.binhdc.springbootcommonproject.entity.Permission;
import com.binhdc.springbootcommonproject.mapper.PermissionMapper;
import com.binhdc.springbootcommonproject.repository.PermissionRepository;
import com.binhdc.springbootcommonproject.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }

}
