package com.binhdc.springbootcommonproject.mapper;

import com.binhdc.springbootcommonproject.dto.request.ProfileCreationRequest;
import com.binhdc.springbootcommonproject.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
