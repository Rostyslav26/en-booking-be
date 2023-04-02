package com.website.enbookingbe.client.card.repository;

import com.website.enbookingbe.client.card.domain.Card;
import com.website.enbookingbe.client.card.mapper.CardRecordMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.website.enbookingbe.data.jooq.Tables.USER_CARD;
import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

@Repository
@RequiredArgsConstructor
public class UserCardRepository {
    private final DSLContext dsl;

    private final CardRecordMapper cardMapper = new CardRecordMapper();

    public List<Card> findAll(Integer userId) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .join(USER_CARD)
            .on(CARD.ID.eq(USER_CARD.CARD_ID))
            .where(USER_CARD.USER_ID.eq(userId))
            .fetch(f -> cardMapper.map(f.into(CARD)));
    }

    public Optional<Card> findOne(Integer cardId, Integer userId) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .join(USER_CARD)
            .on(CARD.ID.eq(USER_CARD.CARD_ID))
            .where(CARD.ID.eq(cardId))
            .and(USER_CARD.USER_ID.eq(userId))
            .fetchOptional(f -> cardMapper.map(f.into(CARD)));
    }

    public List<Card> findByCardIds(Set<Integer> cardIds, Integer userId) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .join(USER_CARD)
            .on(CARD.ID.eq(USER_CARD.CARD_ID))
            .where(USER_CARD.USER_ID.eq(userId))
            .and(CARD.ID.in(cardIds))
            .fetch(f -> cardMapper.map(f.into(CARD)));
    }

    public List<Card> findNotLearned(Integer userId, Integer limit) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .join(USER_CARD)
            .on(CARD.ID.eq(USER_CARD.CARD_ID))
            .where(USER_CARD.USER_ID.eq(userId))
            .and(CARD.COMPLETED.eq(false))
            .orderBy(CARD.CREATED_AT.desc())
            .limit(limit)
            .fetch(f -> cardMapper.map(f.into(CARD)));
    }

    public void removeById(Integer cardId, Integer userId) {
        dsl.delete(USER_CARD)
            .where(USER_CARD.CARD_ID.eq(cardId))
            .and(USER_CARD.USER_ID.eq(userId))
            .execute();
    }

    public void save(Integer id, Integer userId) {
        dsl.insertInto(USER_CARD)
            .set(USER_CARD.CARD_ID, id)
            .set(USER_CARD.USER_ID, userId)
            .execute();
    }
}
