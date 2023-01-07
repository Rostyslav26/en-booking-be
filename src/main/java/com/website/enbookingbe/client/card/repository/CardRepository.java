package com.website.enbookingbe.client.card.repository;

import com.website.enbookingbe.client.card.domain.Card;
import com.website.enbookingbe.client.card.mapper.CardRecordMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.website.enbookingbe.core.utils.FieldsHolder.getPersonSelectRow;
import static com.website.enbookingbe.data.jooq.Tables.USER;
import static com.website.enbookingbe.data.jooq.Tables.USER_CARD;
import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

@Repository
@RequiredArgsConstructor
public class CardRepository {
    private final DSLContext dsl;
    private final CardRecordMapper cardMapper = new CardRecordMapper();

    public Optional<Card> getById(Integer id) {
        return dsl.select(
                CARD,
                getPersonSelectRow())
            .from(CARD)
            .join(USER).on(CARD.AUTHOR_ID.eq(USER.ID))
            .where(CARD.ID.eq(id))
            .fetchOptional(cardMapper);
    }

    public List<Card> getFavoriteCards(Integer userId) {
        return dsl.select(
                CARD,
                getPersonSelectRow())
            .from(CARD)
            .join(USER_CARD).on(CARD.ID.eq(USER_CARD.CARD_ID))
            .where(USER_CARD.USER_ID.eq(userId))
            .fetch(cardMapper);
    }
}
