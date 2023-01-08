package com.website.enbookingbe.core.user.management.controller;

import com.website.enbookingbe.core.security.jwt.JWTFilter;
import com.website.enbookingbe.core.security.jwt.JWTProvider;
import com.website.enbookingbe.core.user.management.model.LoginRequest;
import com.website.enbookingbe.core.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final JWTProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginRequest request) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        );

        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtProvider.createToken(authentication, request.isRememberMe());
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + token);

        final Map<String, Object> body = Map.of(
            "user", userService.getProfile(request.getEmail()),
            "token", token
        );

        return new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
    }
}
