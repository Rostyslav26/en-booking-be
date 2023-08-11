package com.website.enbookingbe.management.controller;

import com.website.enbookingbe.management.UserMailService;
import com.website.enbookingbe.management.UserService;
import com.website.enbookingbe.management.domain.User;
import com.website.enbookingbe.management.resource.RegistrationResource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AccountController {
    private final UserService userService;
    private final UserMailService userMailService;

    @Value("${website.url}")
    private String websiteUrl;

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegistrationResource resource) {
        User user = userService.save(resource);

        userMailService.sendActivationEmail(user);
    }

    @GetMapping("/activate")
    public void activate(@RequestParam String key, HttpServletResponse response) throws IOException {
        userService.activate(key);

        response.sendRedirect(websiteUrl + "/login");
    }
}
