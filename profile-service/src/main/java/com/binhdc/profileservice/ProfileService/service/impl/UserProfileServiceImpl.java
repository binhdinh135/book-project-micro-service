package com.binhdc.profileservice.ProfileService.service.impl;

import com.binhdc.profileservice.ProfileService.dto.request.ProfileCreationRequest;
import com.binhdc.profileservice.ProfileService.dto.response.UserProfileResponse;
import com.binhdc.profileservice.ProfileService.entity.UserProfile;
import com.binhdc.profileservice.ProfileService.mapper.UserProfileMapper;
import com.binhdc.profileservice.ProfileService.repository.UserProfileRepository;
import com.binhdc.profileservice.ProfileService.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse createProfile(ProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    @Override
    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile =
                userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));

        return userProfileMapper.toUserProfileResponse(userProfile);
    }
}