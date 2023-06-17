package com.website.enbookingbe.card;

import com.website.enbookingbe.card.resource.CardResource;
import com.website.enbookingbe.card.resource.CreateCardResource;
import com.website.enbookingbe.card.resource.UpdateCardResource;
import com.website.enbookingbe.security.Principal;
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
    public CardResource update(@RequestBody UpdateCardResource dto, @AuthenticationPrincipal Principal principal) {
        final Card card = cardService.update(dto, principal.getId());

        return cardMapper.toResource(card);
    }

    @PostMapping("/my")
    public CardResource create(@RequestBody CreateCardResource dto, @AuthenticationPrincipal Principal principal) {
        final Card card = cardService.create(dto, principal.getId());

        return cardMapper.toResource(card);
    }

    @GetMapping("/my/not-learned")
    public List<CardResource> notLearnedCards(
        @RequestParam(required = false) Integer limit,
        @AuthenticationPrincipal Principal principal
    ) {
        return cardService.getNotLearnedByUserId(principal.getId(), limit).stream()
            .map(cardMapper::toResource)
            .toList();
    }

    @DeleteMapping("/my/{id}")
    public void remove(@PathVariable Integer id, @AuthenticationPrincipal Principal principal) {
        cardService.deleteFromUserCollection(id, principal.getId());
    }

    @PostMapping("/{id}/add-to-my-collection")
    public void addToUserCollection(@PathVariable Integer id, @AuthenticationPrincipal Principal principal) {
        cardService.addToUserCollection(id, principal.getId());
    }

    @PostMapping("/my/favorites/{id}")
    public void markAsFavorite(
        @PathVariable Integer id,
        @RequestParam boolean favorite,
        @AuthenticationPrincipal Principal principal
    ) {
        cardService.markAsFavorite(id, favorite, principal.getId());
    }

    @GetMapping("/{id}")
    public CardResource cardById(@PathVariable Integer id) {
        return cardService.getById(id)
            .map(cardMapper::toResource)
            .orElseThrow(() -> new CardNotFoundException(id));
    }
}
