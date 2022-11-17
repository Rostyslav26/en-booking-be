package com.websitre.enbookingbe.core.user.management.controller;

import com.websitre.enbookingbe.core.exception.BadRequestException;
import com.websitre.enbookingbe.core.user.management.domain.User;
import com.websitre.enbookingbe.core.user.management.dto.RegistrationRequest;
import com.websitre.enbookingbe.core.user.management.mapper.UserMapper;
import com.websitre.enbookingbe.core.user.management.service.UserMailService;
import com.websitre.enbookingbe.core.user.management.service.UserService;
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

        final User user = userMapper.toNewUser(dto);
        final User savedUser = userService.save(user);

        userMailService.sendActivationEmail(savedUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam String key) {
        userService.activate(key);

        return ResponseEntity.ok().build();
    }
}
