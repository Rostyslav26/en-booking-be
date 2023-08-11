package com.website.enbookingbe.card.repository;

import com.google.common.collect.ImmutableList;
import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.repository.mapper.CardRecordMapper;
import com.website.enbookingbe.data.jooq.tables.records.CardRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

@Repository
@RequiredArgsConstructor
public class CardRepository {
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

    public CardV2 update(CardV2 card) {
        return update(card, CARD.QUESTION, CARD.ANSWER);
    }

    public CardV2 update(CardV2 card, Field<?>... fields) {
        var storeFields = ImmutableList.<Field<?>>builder()
            .add(fields)
            .add(CARD.UPDATED_AT)
            .build();

        CardRecord cardRecord = dsl.newRecord(CARD, mapper.unmap(card));
        cardRecord.setUpdatedAt(LocalDateTime.now());

        cardRecord.update(storeFields);

        return mapper.map(cardRecord);
    }

    public Optional<CardV2> findById(Integer id) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .where(CARD.ID.eq(id))
            .fetchOptional(mapper);
    }

    public List<CardV2> findAllByIds(List<Integer> ids) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .where(CARD.ID.in(ids))
            .fetch(mapper);
    }
}
