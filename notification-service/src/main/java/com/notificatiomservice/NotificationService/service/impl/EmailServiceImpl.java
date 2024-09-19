package com.notificatiomservice.NotificationService.service.impl;

import com.notificatiomservice.NotificationService.dto.request.EmailRequest;
import com.notificatiomservice.NotificationService.dto.request.SendEmailRequest;
import com.notificatiomservice.NotificationService.dto.request.Sender;
import com.notificatiomservice.NotificationService.dto.response.EmailResponse;
import com.notificatiomservice.NotificationService.exception.AppException;
import com.notificatiomservice.NotificationService.exception.ErrorCode;
import com.notificatiomservice.NotificationService.repository.EmailClient;
import com.notificatiomservice.NotificationService.service.EmailService;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailServiceImpl implements EmailService {

    final EmailClient emailClient;

    @Value("${brevo.key}")
    private String api_key;
    @Override
    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Dinh Cong Binh")
                        .email("binhdinh1351@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(api_key, emailRequest);
        } catch (FeignException e){
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
