package com.binhdc.springbootcommonproject.controller;

import com.binhdc.springbootcommonproject.dto.response.ApiResponse;
import com.binhdc.springbootcommonproject.dto.request.UserCreationRequest;
import com.binhdc.springbootcommonproject.dto.request.UserUpdateRequest;
import com.binhdc.springbootcommonproject.dto.response.UserResponse;
import com.binhdc.springbootcommonproject.exception.ErrorCode;
import com.binhdc.springbootcommonproject.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@EnableMethodSecurity //Annotation bật phân quyền trong class này
public class UserController {

    private final UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
//        if (bindingResult.hasErrors()) {
//            ApiResponse apiResponse = new ApiResponse();
//
//            apiResponse.setCode(ErrorCode.INVALID_DOB.getCode());
//            apiResponse.setMessage(ErrorCode.INVALID_DOB.getMessage());
//
//            return apiResponse;
//        } else {
            return ApiResponse.<UserResponse>builder()
                    .message("Success")
                    .result(userService.createUser(request))
                    .build();
//        }
    }

    /* PreAuthorize: đây là annotaion dùng để phân quyền cho hàm này.
    Spring security sẽ check trước khi vào hàm này để chắc chắn rằng
    chỉ có ADMIN mới có thể dùng được hàm này, chúng ta hoàn có thể đặt ở lớp controller hoặc lớp service
     */
    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasAuthority('APPROVE_POST')")
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .message("Success")
                .result(userService.getUsers())
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasAuthority('APPROVE_POST')")
    @GetMapping("/get-all-user-other-service")
    List<UserResponse> getUsersFromOtherService() {
        return userService.getUsers();
    }

    /* PostAuthorize: đây là annotaion dùng để phân quyền cho hàm này.
    Spring security sẽ check sau khi vào hàm này thực hiện,
    chỉ khi thỏa màn điều kiện của PostAuthorize thì mới trả về kết quả cho client.

    => returnObject.result.username == authentication.name: chỉ lấy được thông tin của chính mình.
     */
    @PostAuthorize("returnObject.result.username == authentication.name")
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Long userId) {
        log.info("In method get user by id");
        return ApiResponse.<UserResponse>builder()
                .message("Success")
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }
}
