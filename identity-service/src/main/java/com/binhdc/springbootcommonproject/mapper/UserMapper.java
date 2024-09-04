package com.binhdc.springbootcommonproject.mapper;

import com.binhdc.springbootcommonproject.dto.request.UserCreationRequest;
import com.binhdc.springbootcommonproject.dto.request.UserUpdateRequest;
import com.binhdc.springbootcommonproject.dto.response.UserResponse;
import com.binhdc.springbootcommonproject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
