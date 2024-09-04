package com.binhdc.springbootcommonproject.mapper;


import com.binhdc.springbootcommonproject.dto.request.PermissionRequest;
import com.binhdc.springbootcommonproject.dto.response.PermissionResponse;
import com.binhdc.springbootcommonproject.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}