package com.website.enbookingbe.core.user.management.controller;

import com.website.enbookingbe.client.card.mapper.CardMapper;
import com.website.enbookingbe.client.card.resource.CardResource;
import com.website.enbookingbe.client.card.service.CardService;
import com.website.enbookingbe.core.user.management.model.UserInfo;
import com.website.enbookingbe.core.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CardService cardService;
    private final CardMapper cardMapper = new CardMapper();

    @GetMapping("/{id}")
    public UserInfo userInfo(@PathVariable Integer id) {
        return userService.getUserInfo(id);
    }

    @GetMapping("/{id}/cards")
    public List<CardResource> userCards(@PathVariable Integer id) {
        return cardService.getByUserId(id).stream()
            .map(cardMapper::toResource)
            .toList();
    }
}
