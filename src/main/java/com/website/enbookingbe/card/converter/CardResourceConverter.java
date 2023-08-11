package com.website.enbookingbe.card.converter;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.resource.CardResource;

public class CardResourceConverter {

    private CardResourceConverter() {
    }

    public static CardResource toResource(CardV2 card) {
        return new CardResource(
            card.getId(),
            card.getQuestion(),
            card.getAnswer()
        );
    }
}
