package com.website.enbookingbe.card.controller;

import com.website.enbookingbe.card.converter.CardResourceConverter;
import com.website.enbookingbe.card.resource.CardResource;
import com.website.enbookingbe.card.service.CardService;
import com.website.enbookingbe.card.service.UserCardService;
import com.website.enbookingbe.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final UserCardService userCardService;

    @PostMapping("/{id}/add-to-my-collection")
    public void addToUserCollection(@PathVariable Integer id, @AuthenticationPrincipal Principal principal) {
        userCardService.copyCardToUser(id, principal.getId());
    }

    @GetMapping("/{id}")
    public CardResource cardById(@PathVariable Integer id) {
        return CardResourceConverter.toResource(cardService.getById(id));
    }
}
