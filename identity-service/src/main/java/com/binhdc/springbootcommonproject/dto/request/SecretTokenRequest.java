package com.binhdc.springbootcommonproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SecretTokenRequest {
    String clientId;
    String clientSecret;
    String userId;

}
