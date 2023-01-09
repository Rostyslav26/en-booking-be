package com.website.enbookingbe.core.user.management.controller;

import com.website.enbookingbe.core.security.SecurityUtils;
import com.website.enbookingbe.core.user.management.model.UserProfile;
import com.website.enbookingbe.core.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public UserProfile getUserProfile() {
        return SecurityUtils.getCurrentUserLogin()
            .map(userService::getProfile)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
