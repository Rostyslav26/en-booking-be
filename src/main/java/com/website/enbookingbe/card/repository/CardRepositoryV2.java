package com.website.enbookingbe.card.repository;

import com.website.enbookingbe.card.CardRecordMapper;
import com.website.enbookingbe.card.CardV2;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

@Repository
@RequiredArgsConstructor
public class CardRepositoryV2 {
    private final DSLContext dsl;
    private final CardRecordMapper mapper = new CardRecordMapper();

    public CardV2 save(CardV2 card) {
        return dsl.insertInto(CARD)
            .set(CARD.QUESTION, card.getQuestion())
            .set(CARD.ANSWER, card.getAnswer())
            .set(CARD.AUTHOR_ID, card.getAuthorId())
            .returning(CARD.ID, CARD.CREATED_AT, CARD.UPDATED_AT)
            .fetchOne(mapper);
    }

    public Optional<CardV2> findById(Integer id) {
        return dsl.select(CARD.ID, CARD.QUESTION, CARD.ANSWER, CARD.AUTHOR_ID, CARD.CREATED_AT, CARD.UPDATED_AT)
            .from(CARD)
            .where(CARD.ID.eq(id))
            .fetchOptional(mapper);
    }

    public List<CardV2> findAllByIds(List<Integer> ids) {
        return dsl.select(CARD.ID, CARD.QUESTION, CARD.ANSWER, CARD.AUTHOR_ID, CARD.CREATED_AT, CARD.UPDATED_AT)
            .from(CARD)
            .where(CARD.ID.in(ids))
            .fetch(mapper);
    }
}
