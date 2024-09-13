//package com.binhdc.springbootcommonproject.user;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
////@PropertySource("classpath:application.properties")
//public class UserUtil {
//
//    private final RedisTemplate redisTemplate;
//
//    private static final String TOKEN_KEY = "TOKEN_KEY";
//
//    @Value("${commonproject.client-id}")
////    @Value("binhdc-test-b13c05hyhp1992")
//    private String clientId;
//
//    @Value("${commonproject.client-secret}")
////    @Value("7yglkk5qd4orn2bl9jjzi4n6ayg09871uj2ld82yojskh3p5yj7wy0uq0rap95bzh56vobg5bqbyjx9htriwlqn2ixud2ximphq2aixyh7814jjlc9crokg41c03soufkc04f7ghcwtw2m94o60b7v")
//    private String clientSecret;
//
//    @Value("${commonproject.user-id}")
////    @Value("binh.dc")
//    private String userId;
//
//    public static final String URI_API_USER_PROFILE = "/identity/users/get-all-user-other-service";
////    @Value("http://localhost:9000")
//    public static final String urlWeb = "http://localhost:8081";
//
//    public static final String URI_API_GET_TOKEN = "/identity/auth/token-other-service";
//
//    public String getToken() {
//        if (redisTemplate.opsForValue().get(TOKEN_KEY) == null) {
//            return resolveToken();
//        } else {
//            return Objects.requireNonNull(redisTemplate.opsForValue().get(TOKEN_KEY).toString());
//        }
//    }
//    private String resolveToken() {
//        RestTemplateConfig restTemplateConfig = new RestTemplateConfig();
//        RestTemplate restTemplate = restTemplateConfig.restTemplate();
//        String token = null;
//        String restUrl = urlWeb + URI_API_GET_TOKEN;
//        // Tạo HttpHeaders
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        AuthenticationRequest info = new AuthenticationRequest();
//        info.setClientId(clientId);
//        info.setClientSecret(clientSecret);
//        info.setUserId(userId);
//
//        // Tạo HttpEntity với DTO và headers
//        HttpEntity<AuthenticationRequest> entity = new HttpEntity<>(info, headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(restUrl, HttpMethod.POST
//                , entity, String.class);
//        token = responseEntity.getBody();
//
//        // Luu tru token vao Redis:
//        redisTemplate.opsForValue().set(TOKEN_KEY, token);
//
//        return token;
//    }
//
//    public List<UserResponse> getAllUsers(String token) {
//        RestTemplateConfig restTemplateConfig = new RestTemplateConfig();
//        RestTemplate restTemplate = restTemplateConfig.restTemplate();
//        List<UserResponse> users = new ArrayList<>();
//        String restUrl = urlWeb + URI_API_USER_PROFILE;
//        try {
//            HttpEntity<?> entity = configRequestHeader(token);
//            ResponseEntity<List<UserResponse>> response = restTemplate.exchange(restUrl
//                    , HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserResponse>>() {});
//            users = response.getBody();
//
//        } catch (HttpClientErrorException e) {
//            if (e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
//                log.error("Forbidden");
//                log.error(Arrays.toString(e.getStackTrace()));
//            } else {
//                log.error("Error token");
//                log.error(Arrays.toString(e.getStackTrace()));
//
//            }
//
//        } catch (Exception e) {
//            log.error("Exception: " + e.getMessage() + " \n Stack trace: " + e);
//        }
//        return users;
//
//    }
//
//    private HttpEntity<?> configRequestHeader(String token) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + token);
//        headers.add("Content-Type", "application/json");
//        return new HttpEntity<>(headers);
//    }
//
//}
