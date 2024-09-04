package com.binhdc.springbootcommonproject.service.impl;

//import com.binhdc.springbootcommonproject.constant.PredefinedRole;
import com.binhdc.springbootcommonproject.constant.PredefinedRole;
import com.binhdc.springbootcommonproject.dto.request.UserCreationRequest;
import com.binhdc.springbootcommonproject.dto.request.UserUpdateRequest;
import com.binhdc.springbootcommonproject.dto.response.RoleResponse;
import com.binhdc.springbootcommonproject.dto.response.UserResponse;
import com.binhdc.springbootcommonproject.entity.Role;
import com.binhdc.springbootcommonproject.entity.User;
import com.binhdc.springbootcommonproject.exception.AppException;
import com.binhdc.springbootcommonproject.exception.ErrorCode;
import com.binhdc.springbootcommonproject.mapper.RoleMapper;
import com.binhdc.springbootcommonproject.mapper.UserMapper;
import com.binhdc.springbootcommonproject.repository.RoleRepository;
import com.binhdc.springbootcommonproject.repository.UserRepository;
import com.binhdc.springbootcommonproject.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
//@EnableMethodSecurity // bật tính năng method security
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

//        HashSet<String> roles = new HashSet<>();
////        roles.add(Role.USER.name());
////        user.setRoles(roles);
//
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
//
        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles().stream().map(roleMapper::toRoleResponse).collect(Collectors.toSet()))
                .dob(user.getDob())
                .build();
//        return userMapper.toUserResponse(user);
    }

    /* PreAuthorize: đây là annotaion dùng để phân quyền cho hàm này.
    Spring security sẽ check trước khi vào hàm này để chắc chắn rằng
    chỉ có ADMIN mới có thể dùng được hàm này, chúng ta hoàn có thể đặt ở lớp controller hoặc lớp service
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}", authentication.getName());
        authentication.getAuthorities().forEach(authority -> log.info(authority.getAuthority()));

        List<User> users = userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();
        if (!users.isEmpty()) {
//            users.forEach(user -> result.add(userMapper.toUserResponse(user)));
            users.forEach(user ->
                    result.add(UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
//                .roles(roles)
                    .dob(user.getDob())
                    .build()));
        }
        return result;
    }

    @Override
    public List<UserResponse> getUsersFromOtherService() {
        return List.of();
    }

    @Override
    public UserResponse getUser(Long userId) {
        UserResponse result = null;
        Optional<User> userOp = userRepository.findById(userId);
        if (userOp.isPresent()) {
            User user = userOp.get();
            result = UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
//                .roles(roles)
                    .dob(user.getDob())
                    .build();
//            result = userMapper.toUserResponse(user);
        }
        return result;
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Set<Role> roles = user.getRoles();
        Set<RoleResponse> rolesReponse = roles.stream().map(roleMapper::toRoleResponse).collect(Collectors.toSet());
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(rolesReponse)
                .dob(user.getDob())
                .build();
//        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }
}
