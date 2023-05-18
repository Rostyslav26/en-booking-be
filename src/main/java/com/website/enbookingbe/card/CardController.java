package com.website.enbookingbe.card;

import com.website.enbookingbe.card.resource.CardResource;
import com.website.enbookingbe.card.resource.CreateCardResource;
import com.website.enbookingbe.card.resource.UpdateCardResource;
import com.website.enbookingbe.security.Principal;
import com.website.enbookingbe.management.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final CardMapper cardMapper = new CardMapper();

    @GetMapping("/my")
    public List<CardResource> myCards(@AuthenticationPrincipal Principal principal) {
        return cardService.getByUserId(principal.getId()).stream()
            .map(cardMapper::toResource)
            .toList();
    }

    @PutMapping("/my")
    public CardResource update(@RequestBody UpdateCardResource dto, @AuthenticationPrincipal User user) {
        final Card card = cardService.update(dto, user.getId());

        return cardMapper.toResource(card);
    }

    @PostMapping("/my")
    public CardResource create(@RequestBody CreateCardResource dto, @AuthenticationPrincipal User user) {
        final Card card = cardService.create(dto, user.getId());

        return cardMapper.toResource(card);
    }

    @GetMapping("/my/not-learned")
    public List<CardResource> notLearnedCards(@RequestParam(required = false) Integer limit, @AuthenticationPrincipal User user) {
        return cardService.getNotLearnedByUserId(user.getId(), limit).stream()
            .map(cardMapper::toResource)
            .toList();
    }

    @DeleteMapping("/my/{id}")
    public void remove(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        cardService.deleteFromUserCollection(id, user.getId());
    }

    @PostMapping("/{id}/add-to-my-collection")
    public void addToUserCollection(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        cardService.addToUserCollection(id, user.getId());
    }

    @PostMapping("/my/favorites/{id}")
    public void markAsFavorite(@PathVariable Integer id, @RequestParam boolean favorite, @AuthenticationPrincipal User user) {
        cardService.markAsFavorite(id, favorite, user.getId());
    }

    @GetMapping("/{id}")
    public CardResource cardById(@PathVariable Integer id) {
        return cardService.getById(id)
            .map(cardMapper::toResource)
            .orElseThrow(() -> new CardNotFoundException(id));
    }
}
