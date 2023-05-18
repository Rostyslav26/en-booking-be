package com.website.enbookingbe.management.controller;

import com.website.enbookingbe.card.CardMapper;
import com.website.enbookingbe.card.resource.CardResource;
import com.website.enbookingbe.card.CardService;
import com.website.enbookingbe.management.resource.UserInfo;
import com.website.enbookingbe.management.UserService;
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
    private static final CardMapper cardMapper = new CardMapper();

    private final UserService userService;
    private final CardService cardService;


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
