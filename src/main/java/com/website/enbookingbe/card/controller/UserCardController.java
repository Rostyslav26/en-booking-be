package com.website.enbookingbe.card.controller;

import com.website.enbookingbe.card.converter.UserCardResourceConverter;
import com.website.enbookingbe.card.domain.UserCard;
import com.website.enbookingbe.card.resource.CreateCardResource;
import com.website.enbookingbe.card.resource.UpdateCardResource;
import com.website.enbookingbe.card.resource.UserCardResource;
import com.website.enbookingbe.card.service.UserCardService;
import com.website.enbookingbe.security.Principal;
import com.website.enbookingbe.utils.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/cards")
@RequiredArgsConstructor
public class UserCardController {
    private final UserCardService userCardService;

    @GetMapping
    public List<UserCardResource> getCards(@AuthenticationPrincipal Principal principal) {
        return userCardService.getAllByUserId(principal.getId()).stream()
            .map(UserCardResourceConverter::toResource)
            .toList();
    }

    @PutMapping("/{id}")
    public UserCardResource updateCard(
        @PathVariable Integer id,
        @RequestBody UpdateCardResource dto,
        @AuthenticationPrincipal Principal principal
    ) {
        final UserCard card = userCardService.update(id, dto, principal.getId());

        return UserCardResourceConverter.toResource(card);
    }

    @PostMapping
    public UserCardResource createCard(
        @RequestBody CreateCardResource dto,
        @AuthenticationPrincipal Principal principal
    ) {
        final UserCard card = userCardService.create(dto, principal.getId());

        return UserCardResourceConverter.toResource(card);
    }

    @GetMapping("/not-learned")
    public List<UserCardResource> getNotLearnedCards(
        PageRequest pageRequest,
        @AuthenticationPrincipal Principal principal
    ) {
        return userCardService.getNotLearnedCards(principal.getId(), pageRequest).stream()
            .map(UserCardResourceConverter::toResource)
            .toList();
    }

    @DeleteMapping("/{id}")
    public void removeCard(@PathVariable Integer id, @AuthenticationPrincipal Principal principal) {
        userCardService.remove(id, principal.getId());
    }

    @PostMapping("/favorites/{id}")
    public void markAsFavorite(
        @PathVariable Integer id,
        @RequestBody boolean favorite,
        @AuthenticationPrincipal Principal principal
    ) {
        userCardService.markAsFavorite(id, favorite, principal.getId());
    }
}
