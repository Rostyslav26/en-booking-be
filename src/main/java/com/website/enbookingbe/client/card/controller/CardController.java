package com.website.enbookingbe.client.card.controller;

import com.website.enbookingbe.client.card.domain.Card;
import com.website.enbookingbe.client.card.exception.CardNotFoundException;
import com.website.enbookingbe.client.card.model.request.CreateCardRequest;
import com.website.enbookingbe.client.card.model.request.UpdateCardRequest;
import com.website.enbookingbe.client.card.service.CardService;
import com.website.enbookingbe.core.user.management.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("/my")
    public List<Card> myCards(@AuthenticationPrincipal User user) {
        return cardService.getByUserId(user.getId());
    }

    @PutMapping("/my/{id}")
    public Card update(@RequestBody UpdateCardRequest dto, @AuthenticationPrincipal User user) {
        return cardService.update(dto, user.getId());
    }

    @PostMapping("/my")
    public Card create(@RequestBody CreateCardRequest dto, @AuthenticationPrincipal User user) {
        return cardService.create(dto, user.getId());
    }

    @GetMapping("/my/not-learned")
    public List<Card> notLearnedCards(@RequestParam(required = false) Integer limit, @AuthenticationPrincipal User user) {
        return cardService.getNotLearnedByUserId(user.getId(), limit);
    }

    @DeleteMapping("/my/{id}")
    public void remove(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        cardService.deleteFromUserCollection(id, user.getId());
    }

    @PostMapping("/{id}/add-to-my-collection")
    public Card addToUserCollection(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        return cardService.addToUserCollection(id, user.getId());
    }

    @PostMapping("/my/favorites/{id}")
    public void markAsFavorite(@PathVariable Integer id, @RequestParam boolean favorite, @AuthenticationPrincipal User user) {
        cardService.markAsFavorite(id, favorite, user.getId());
    }

    @GetMapping("/{id}")
    public Card cardById(@PathVariable Integer id) {
        return cardService.getById(id).orElseThrow(() -> new CardNotFoundException(id));
    }
}
