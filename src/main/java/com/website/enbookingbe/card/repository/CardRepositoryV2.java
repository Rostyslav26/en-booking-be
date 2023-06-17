package com.website.enbookingbe.card.repository;

import com.website.enbookingbe.card.Card;
import com.website.enbookingbe.card.CardRecordMapper;
import com.website.enbookingbe.card.CardV2;
import com.website.enbookingbe.data.jooq.tables.records.CardRecord;
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
        CardRecord record = mapper.unmap(card);
        dsl.executeInsert(record);

        return mapper.map(record);
    }

    public Optional<Card> findById(Integer id) {
        return dsl.select(CARD.ID, CARD.QUESTION, CARD.ANSWER, CARD.AUTHOR_ID, CARD.CREATED_AT, CARD.UPDATED_AT)
            .from(CARD)
            .where(CARD.ID.eq(id))
            .fetchOptionalInto(Card.class);
    }

    public List<Card> findAllByIds(List<Integer> ids) {
        return dsl.select(CARD.ID, CARD.QUESTION, CARD.ANSWER, CARD.AUTHOR_ID, CARD.CREATED_AT, CARD.UPDATED_AT)
            .from(CARD)
            .where(CARD.ID.in(ids))
            .fetchInto(Card.class);
    }
}
