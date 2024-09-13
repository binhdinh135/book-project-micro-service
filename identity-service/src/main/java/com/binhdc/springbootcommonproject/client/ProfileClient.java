package com.binhdc.springbootcommonproject.client;

import com.binhdc.springbootcommonproject.dto.request.ProfileCreationRequest;
import com.binhdc.springbootcommonproject.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "profile-service",
        url = "${app.services.profile}"
)
public interface ProfileClient {

//    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
//    UserProfileResponse createProfile(
//            // Cach lam thu cong, moi lan gui request sang service Profile, chung ta se kem 1 token
//            // de ben Profile service co the authentication va pass toi controller.
//            @RequestHeader("Authorization") String token,
//            @RequestBody ProfileCreationRequest request
//    );

    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request);

}
