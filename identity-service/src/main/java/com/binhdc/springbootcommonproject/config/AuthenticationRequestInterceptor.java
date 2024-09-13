package com.binhdc.springbootcommonproject.config;

import com.binhdc.springbootcommonproject.dto.request.AuthenticationRequest;
import com.binhdc.springbootcommonproject.service.AuthenticationService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationRequestInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String TOKEN_TYPE = "Bearer";

    private final AuthenticationService authenticationService;

    @Override
    public void apply(RequestTemplate requestTemplate) {

        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authHeader = servletRequestAttributes.getRequest().getHeader("Authorization");
        if (authHeader == null) {
            // Neu truong hop khong co token -> tao token de chung ta co the dung.
            // Todo: chung ta co the ket hop cung redis cache de lay va luu tru token cho cac lan goi tiep theo
            authHeader = authenticationService.authenticate(
                    AuthenticationRequest.builder()
                            .username("admin")
                            .password("admin")
                            .build()
            ).getToken();

        }

        if (!hasBearerToken(authHeader))
            authHeader = "Bearer " + authHeader;

        log.info("Header: {}", authHeader);

        if (StringUtils.hasText(authHeader))
            requestTemplate.header("Authorization", authHeader);


//        var authentication = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (authentication != null) {
//            var token = authentication.getTokenValue();
//            requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, authentication));
//        }

    }

    private boolean hasBearerToken(String token) {
        return token != null && token.startsWith("Bearer ");
    }
}
