package com.website.enbookingbe.card.repository;

import com.website.enbookingbe.card.domain.UserCard;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.website.enbookingbe.data.jooq.tables.UserCard.USER_CARD;

@Repository
@RequiredArgsConstructor
public class UserCardRepositoryV2 {
    private final DSLContext dsl;

    public void save(UserCard userCard) {
        dsl.insertInto(USER_CARD)
            .set(USER_CARD.USER_ID, userCard.getUserId())
            .set(USER_CARD.CARD_ID, userCard.getCard().getId())
            .set(USER_CARD.FAVORITE, userCard.isFavorite())
            .set(USER_CARD.LEARNED, userCard.isLearned())
            .execute();
    }

    public void remove(Integer userId, Integer cardId) {
        dsl.deleteFrom(USER_CARD)
            .where(USER_CARD.USER_ID.eq(userId))
            .and(USER_CARD.CARD_ID.eq(cardId))
            .execute();
    }
}
