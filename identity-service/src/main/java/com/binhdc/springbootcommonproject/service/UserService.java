package com.binhdc.springbootcommonproject.service;

import com.binhdc.springbootcommonproject.dto.request.UserCreationRequest;
import com.binhdc.springbootcommonproject.dto.request.UserUpdateRequest;
import com.binhdc.springbootcommonproject.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);

    List<UserResponse> getUsers();

    UserResponse getUser(Long userId);

    UserResponse getMyInfo();

    void deleteUser(String userId);

    UserResponse updateUser(Long userId, UserUpdateRequest request);

    List<UserResponse> getUsersFromOtherService();
}
