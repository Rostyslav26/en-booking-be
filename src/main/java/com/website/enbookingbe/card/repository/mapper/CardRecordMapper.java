package com.website.enbookingbe.card.repository.mapper;

import com.website.enbookingbe.card.domain.Card;
import com.website.enbookingbe.data.jooq.tables.records.CardRecord;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.lang.NonNull;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

public class CardRecordMapper implements RecordMapper<Record, Card>, RecordUnmapper<Card, CardRecord> {

    @Override
    public Card map(Record record) {
        return Card.builder()
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
    public CardRecord unmap(Card source) throws MappingException {
        final CardRecord cardRecord = new CardRecord();
        cardRecord.setId(source.getId());
        cardRecord.setQuestion(source.getQuestion());
        cardRecord.setAnswer(source.getAnswer());
        cardRecord.setAuthorId(source.getAuthorId());
        cardRecord.setCreatedAt(source.getCreatedAt());

        return cardRecord;
    }
}
