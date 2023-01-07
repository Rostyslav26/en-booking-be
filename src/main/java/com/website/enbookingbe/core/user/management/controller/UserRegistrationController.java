package com.website.enbookingbe.core.user.management.controller;

import com.website.enbookingbe.core.exception.BadRequestException;
import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.user.management.mapper.UserMapper;
import com.website.enbookingbe.core.user.management.model.RegistrationRequest;
import com.website.enbookingbe.core.user.management.service.UserMailService;
import com.website.enbookingbe.core.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRegistrationController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserMailService userMailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest dto) {
        if (!dto.getPassword().equals(dto.getConfirmedPassword())) {
            throw new BadRequestException("Passwords are not confirmed!");
        }

        User user = userMapper.toNewUser(dto);
        user = userService.save(user);

        userMailService.sendActivationEmail(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam String key) {
        userService.activate(key);

        return ResponseEntity.ok().build();
    }
}
