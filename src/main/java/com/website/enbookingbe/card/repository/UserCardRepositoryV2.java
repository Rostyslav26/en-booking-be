package com.website.enbookingbe.card.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.website.enbookingbe.data.jooq.tables.UserCard.USER_CARD;

@Repository
@RequiredArgsConstructor
public class UserCardRepositoryV2 {
    private final DSLContext dsl;

    public void save(Integer userId, Integer cardId) {
        dsl.insertInto(USER_CARD)
            .set(USER_CARD.USER_ID, userId)
            .set(USER_CARD.CARD_ID, cardId)
            .execute();
    }
}
