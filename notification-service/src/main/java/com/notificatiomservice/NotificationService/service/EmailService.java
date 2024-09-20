package com.notificatiomservice.NotificationService.service;

import com.notificatiomservice.NotificationService.dto.request.SendEmailRequest;
import com.notificatiomservice.NotificationService.dto.response.EmailResponse;

public interface EmailService {
    EmailResponse sendEmail(SendEmailRequest request);
}
