package com.website.enbookingbe.card;

import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

public class CardRecordMapper implements RecordMapper<Record, CardV2> {

    @Override
    public CardV2 map(Record record) {
        CardV2 card = new CardV2();

        card.setId(record.get(CARD.ID));
        card.setQuestion(record.get(CARD.QUESTION));
        card.setAnswer(record.get(CARD.ANSWER));
        card.setAuthorId(record.get(CARD.AUTHOR_ID));
        card.setCreatedAt(record.get(CARD.CREATED_AT));
        card.setUpdatedAt(record.get(CARD.UPDATED_AT));

        return card;
    }
}
