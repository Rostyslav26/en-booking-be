package com.website.enbookingbe.card.converter;

import com.website.enbookingbe.card.domain.Card;
import com.website.enbookingbe.card.resource.CardResource;

public class CardResourceConverter {

    private CardResourceConverter() {
    }

    public static CardResource toResource(Card card) {
        return new CardResource(
            card.getId(),
            card.getQuestion(),
            card.getAnswer()
        );
    }
}
