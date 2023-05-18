package com.website.enbookingbe.management;

import com.website.enbookingbe.service.MailService;
import com.website.enbookingbe.management.entity.User;
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
        mailService.sendEmailFromTemplate(user, "mail/activation-email");
    }
}