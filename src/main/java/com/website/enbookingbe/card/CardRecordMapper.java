package com.website.enbookingbe.card;

import com.website.enbookingbe.data.jooq.tables.records.CardRecord;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;

import javax.annotation.Nonnull;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

public class CardRecordMapper implements RecordMapper<Record, CardV2>, RecordUnmapper<CardV2, CardRecord> {

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

    @Override
    @Nonnull
    public CardRecord unmap(CardV2 card) {
        CardRecord cardRecord = new CardRecord();

        cardRecord.setId(card.getId());
        cardRecord.setQuestion(card.getQuestion());
        cardRecord.setAnswer(card.getAnswer());
        cardRecord.setAuthorId(card.getAuthorId());
        cardRecord.setCreatedAt(card.getCreatedAt());
        cardRecord.setUpdatedAt(card.getUpdatedAt());

        return cardRecord;
    }
}
