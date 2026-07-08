package com.hca.notification_service.config;

import com.hca.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final EmailService emailService;

    @GetMapping("/test-email")
    public String send() {

        emailService.sendEmail(
                "your-other-email@gmail.com",
                "Test",
                "Hello");

        return "Sent";
    }
}