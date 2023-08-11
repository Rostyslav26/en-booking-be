package com.website.enbookingbe.card.repository;

import com.website.enbookingbe.card.domain.UserCard;
import com.website.enbookingbe.card.repository.mapper.UserCardRecordMapper;
import com.website.enbookingbe.data.jooq.tables.records.UserCardRecord;
import com.website.enbookingbe.utils.PageRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;
import static com.website.enbookingbe.data.jooq.tables.UserCard.USER_CARD;

@Repository
@RequiredArgsConstructor
public class UserCardRepository {
    private final DSLContext dsl;
    private final UserCardRecordMapper mapper = new UserCardRecordMapper();

    public UserCard save(UserCard userCard) {
        dsl.insertInto(USER_CARD)
            .set(USER_CARD.USER_ID, userCard.getUserId())
            .set(USER_CARD.CARD_ID, userCard.getCard().getId())
            .set(USER_CARD.FAVORITE, userCard.isFavorite())
            .set(USER_CARD.LEARNED, userCard.isLearned())
            .execute();

        return userCard;
    }

    public List<UserCard> findAllByUserId(Integer userId) {
        return dsl.select(getFields())
            .from(USER_CARD)
            .join(CARD).on(USER_CARD.CARD_ID.eq(CARD.ID))
            .where(USER_CARD.USER_ID.eq(userId))
            .fetch(mapper);
    }

    public Optional<UserCard> findById(Integer userId, Integer cardId) {
        return dsl.select(getFields())
            .from(USER_CARD)
            .join(CARD).on(USER_CARD.CARD_ID.eq(CARD.ID))
            .where(USER_CARD.USER_ID.eq(userId))
            .and(USER_CARD.CARD_ID.eq(cardId))
            .fetchOptional(mapper);
    }

    public List<UserCard> findAllByUserIdAndCardIds(Integer userId, List<Integer> cardIds) {
        return dsl.select(getFields())
            .from(USER_CARD)
            .join(CARD).on(USER_CARD.CARD_ID.eq(CARD.ID))
            .where(USER_CARD.USER_ID.eq(userId))
            .and(USER_CARD.CARD_ID.in(cardIds))
            .fetch(mapper);
    }

    public List<UserCard> findNotLearnedByUserId(Integer userId, PageRequest pageRequest) {
        return dsl.select(getFields())
            .from(USER_CARD)
            .join(CARD).on(USER_CARD.CARD_ID.eq(CARD.ID))
            .where(USER_CARD.USER_ID.eq(userId))
            .and(USER_CARD.LEARNED.isFalse())
            .orderBy(CARD.CREATED_AT.desc())
            .limit(pageRequest.getSize())
            .offset(pageRequest.getOffset())
            .fetch(mapper);
    }

    public void remove(Integer userId, Integer cardId) {
        dsl.deleteFrom(USER_CARD)
            .where(USER_CARD.USER_ID.eq(userId))
            .and(USER_CARD.CARD_ID.eq(cardId))
            .execute();
    }

    public void update(UserCard userCard, Field<?>... fields) {
        UserCardRecord record = dsl.newRecord(USER_CARD, mapper.unmap(userCard));
        record.update(fields);
    }

    private List<Field<?>> getFields() {
        return List.of(
            USER_CARD.USER_ID,
            USER_CARD.CARD_ID,
            USER_CARD.FAVORITE,
            USER_CARD.LEARNED,
            CARD.ID,
            CARD.QUESTION,
            CARD.ANSWER,
            CARD.AUTHOR_ID,
            CARD.CREATED_AT,
            CARD.UPDATED_AT
        );
    }
}
