package com.website.enbookingbe.core.user.management.service;

import com.website.enbookingbe.core.service.MailService;
import com.website.enbookingbe.core.user.management.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserMailService {
    private final MailService mailService;

    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        mailService.sendEmailFromTemplate(user, "templates/mail/activationEmail", "email.activation.title");
    }
}