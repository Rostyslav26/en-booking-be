package com.website.enbookingbe.card;

import com.website.enbookingbe.card.domain.CardV2;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

public class CardRecordMapper implements RecordMapper<Record, CardV2> {

    @Override
    public CardV2 map(Record record) {
        return CardV2.builder()
            .id(record.get(CARD.ID))
            .question(record.get(CARD.QUESTION))
            .answer(record.get(CARD.ANSWER))
            .authorId(record.get(CARD.AUTHOR_ID))
            .createdAt(record.get(CARD.CREATED_AT))
            .updatedAt(record.get(CARD.UPDATED_AT))
            .build();
    }
}
