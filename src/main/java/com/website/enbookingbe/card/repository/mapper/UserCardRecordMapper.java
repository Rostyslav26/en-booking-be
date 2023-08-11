package com.website.enbookingbe.card.repository.mapper;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.domain.UserCard;
import com.website.enbookingbe.data.jooq.tables.records.UserCardRecord;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

import javax.annotation.Nonnull;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;
import static com.website.enbookingbe.data.jooq.tables.UserCard.USER_CARD;

public class UserCardRecordMapper implements RecordMapper<Record, UserCard>, RecordUnmapper<UserCard, UserCardRecord> {

    @Override
    @Nonnull
    public UserCardRecord unmap(UserCard source) throws MappingException {
        UserCardRecord record = new UserCardRecord();
        record.setUserId(source.getUserId());
        record.setCardId(source.getCard().getId());
        record.setFavorite(source.isFavorite());
        record.setLearned(source.isLearned());

        return record;
    }

    @Override
    public UserCard map(Record record) {
        return UserCard.builder()
            .card(toCard(record))
            .userId(record.get(USER_CARD.USER_ID))
            .favorite(record.get(USER_CARD.FAVORITE))
            .learned(record.get(USER_CARD.LEARNED))
            .build();
    }

    private CardV2 toCard(Record record) {
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
