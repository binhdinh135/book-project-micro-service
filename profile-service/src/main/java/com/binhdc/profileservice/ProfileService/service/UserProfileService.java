package com.binhdc.profileservice.ProfileService.service;

import com.binhdc.profileservice.ProfileService.dto.request.ProfileCreationRequest;
import com.binhdc.profileservice.ProfileService.dto.response.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse createProfile(ProfileCreationRequest request);

    UserProfileResponse getProfile(String profileId);
}
