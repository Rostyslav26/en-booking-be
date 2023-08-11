package com.website.enbookingbe.card.converter;

import com.website.enbookingbe.card.domain.UserCard;
import com.website.enbookingbe.card.resource.UserCardResource;

public class UserCardResourceConverter {

    private UserCardResourceConverter() {
    }

    public static UserCardResource toResource(UserCard userCard) {
        return UserCardResource.builder()
            .id(userCard.getCard().getId())
            .question(userCard.getCard().getQuestion())
            .answer(userCard.getCard().getAnswer())
            .favorite(userCard.isFavorite())
            .learned(userCard.isLearned())
            .build();
    }
}
