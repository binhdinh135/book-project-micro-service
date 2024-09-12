package com.binhdc.profileservice.ProfileService.controller;

import com.binhdc.profileservice.ProfileService.dto.ApiResponse;
import com.binhdc.profileservice.ProfileService.dto.request.ProfileCreationRequest;
import com.binhdc.profileservice.ProfileService.dto.response.UserProfileResponse;
import com.binhdc.profileservice.ProfileService.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

//    @PostMapping("/users")
//    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request) {
//        return userProfileService.createProfile(request);
//    }


    @GetMapping("/users/{profileId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String profileId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfile(profileId))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    ApiResponse<List<UserProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .result(userProfileService.getAllProfile())
                .build();
    }
}
