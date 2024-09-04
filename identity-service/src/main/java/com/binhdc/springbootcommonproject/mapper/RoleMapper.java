package com.binhdc.springbootcommonproject.mapper;

import com.binhdc.springbootcommonproject.dto.request.RoleRequest;
import com.binhdc.springbootcommonproject.dto.response.RoleResponse;
import com.binhdc.springbootcommonproject.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface RoleMapper {
    // Khi map struct từ request -> Role, sẽ bỏ qua trường permission, chúng ta tự build và map
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
