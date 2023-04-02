package com.website.enbookingbe.client.card.mapper;

import com.website.enbookingbe.client.card.domain.Card;
import com.website.enbookingbe.data.jooq.tables.records.CardRecord;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

public class CardRecordMapper implements RecordMapper<CardRecord, Card>, RecordUnmapper<Card, CardRecord> {

    @Override
    public Card map(CardRecord record) {
        final Card card = new Card();
        card.setId(record.getId());
        card.setQuestion(record.getQuestion());
        card.setAnswer(record.getAnswer());
        card.setAuthorId(record.getAuthorId());
        card.setCreatedAt(record.getCreatedAt());
        card.setUpdatedAt(record.getUpdatedAt());
        card.setFavorite(record.getFavorite());
        card.setLearned(record.getCompleted());

        return card;
    }

    @Override
    public CardRecord unmap(Card card) throws MappingException {
        final CardRecord target = new CardRecord();
        target.setId(card.getId());
        target.setAuthorId(card.getAuthorId());
        target.setQuestion(card.getQuestion());
        target.setAnswer(card.getAnswer());
        target.setCreatedAt(card.getCreatedAt());
        target.setUpdatedAt(card.getUpdatedAt());
        target.setFavorite(card.isFavorite());
        target.setCompleted(card.isLearned());

        return target;
    }
}
