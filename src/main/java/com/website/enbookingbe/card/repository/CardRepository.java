package com.website.enbookingbe.card.repository;

import com.website.enbookingbe.card.domain.Card;
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

    public Card save(Card card) {
        return dsl.insertInto(CARD)
            .set(CARD.QUESTION, card.getQuestion())
            .set(CARD.ANSWER, card.getAnswer())
            .set(CARD.AUTHOR_ID, card.getAuthorId())
            .returning(CARD.ID, CARD.CREATED_AT, CARD.UPDATED_AT)
            .fetchOne(mapper);
    }

    public Card update(Card card) {
        return update(card, CARD.QUESTION, CARD.ANSWER);
    }

    public Card update(Card card, Field<?>... fields) {
        CardRecord cardRecord = dsl.newRecord(CARD, mapper.unmap(card));
        cardRecord.setUpdatedAt(LocalDateTime.now());

        cardRecord.update(fields);

        return mapper.map(cardRecord);
    }

    public Optional<Card> findById(Integer id) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .where(CARD.ID.eq(id))
            .fetchOptional(mapper);
    }

    public List<Card> findAllByIds(List<Integer> ids) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .where(CARD.ID.in(ids))
            .fetch(mapper);
    }
}
