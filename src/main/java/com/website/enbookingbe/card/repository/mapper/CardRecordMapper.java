package com.website.enbookingbe.card.repository.mapper;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.data.jooq.tables.records.CardRecord;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.lang.NonNull;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

public class CardRecordMapper implements RecordMapper<Record, CardV2>, RecordUnmapper<CardV2, CardRecord> {

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

    @Override
    @NonNull
    public CardRecord unmap(CardV2 source) throws MappingException {
        final CardRecord cardRecord = new CardRecord();
        cardRecord.setId(source.getId());
        cardRecord.setQuestion(source.getQuestion());
        cardRecord.setAnswer(source.getAnswer());
        cardRecord.setAuthorId(source.getAuthorId());
        cardRecord.setCreatedAt(source.getCreatedAt());
        cardRecord.setUpdatedAt(source.getUpdatedAt());

        return cardRecord;
    }
}
