package com.website.enbookingbe.client.card.mapper;

import com.website.enbookingbe.client.card.domain.Card;
import com.website.enbookingbe.core.user.management.model.Person;
import com.website.enbookingbe.data.jooq.tables.records.CardRecord;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

public class CardRecordMapper implements RecordMapper<Record, Card> {

    @Override
    public Card map(Record record) {
        final CardRecord cardRecord = record.get(CARD.getName(), CardRecord.class);

        final Card card = new Card();
        card.setId(cardRecord.get(CARD.ID));
        card.setQuestion(cardRecord.get(CARD.QUESTION));
        card.setAnswer(cardRecord.get(CARD.ANSWER));
        card.setCreatedAt(cardRecord.get(CARD.CREATED_AT));
        card.setUpdatedAt(cardRecord.get(CARD.UPDATED_AT));

        final Person author = record.get("person", Person.class);
        card.setAuthor(author);

        return card;
    }
}
