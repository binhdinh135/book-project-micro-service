package com.notificatiomservice.NotificationService.controller;

import com.notificatiomservice.NotificationService.dto.request.SendEmailRequest;
import com.notificatiomservice.NotificationService.dto.response.ApiResponse;
import com.notificatiomservice.NotificationService.dto.response.EmailResponse;
import com.notificatiomservice.NotificationService.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest request){
        return ApiResponse.<EmailResponse>builder()
                .result(emailService.sendEmail(request))
                .build();
    }
}
