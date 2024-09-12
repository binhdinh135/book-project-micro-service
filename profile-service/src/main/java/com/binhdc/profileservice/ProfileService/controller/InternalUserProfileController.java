package com.binhdc.profileservice.ProfileService.controller;

import com.binhdc.profileservice.ProfileService.dto.ApiResponse;
import com.binhdc.profileservice.ProfileService.dto.request.ProfileCreationRequest;
import com.binhdc.profileservice.ProfileService.dto.response.UserProfileResponse;
import com.binhdc.profileservice.ProfileService.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/internal/users")
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.createProfile(request))
                .build();
    }

//    @GetMapping("/users/{profileId}")
//    UserProfileResponse getProfile(@PathVariable String profileId) {
//        return userProfileService.getProfile(profileId);
//    }

}
