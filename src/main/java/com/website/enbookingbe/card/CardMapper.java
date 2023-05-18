package com.website.enbookingbe.card;

import com.website.enbookingbe.card.resource.CardResource;

public class CardMapper {

    public CardResource toResource(Card card) {
        return new CardResource(
            card.getId(),
            card.getQuestion(),
            card.getAnswer()
        );
    }
}
