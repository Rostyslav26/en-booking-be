package com.website.enbookingbe.client.card.mapper;

import com.website.enbookingbe.client.card.entity.Card;
import com.website.enbookingbe.client.card.resource.CardResource;

public class CardMapper {

    public CardResource toResource(Card card) {
        return new CardResource(
            card.getId(),
            card.getQuestion(),
            card.getAnswer()
        );
    }
}
