package com.website.enbookingbe.client.card.repository;

import com.google.common.collect.ImmutableSet;
import com.website.enbookingbe.client.card.domain.Card;
import com.website.enbookingbe.client.card.mapper.CardRecordMapper;
import com.website.enbookingbe.core.repository.UpdatableRepository;
import com.website.enbookingbe.data.jooq.tables.records.CardRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.TableField;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.website.enbookingbe.data.jooq.tables.Card.CARD;

@Repository
@RequiredArgsConstructor
public class CardRepository implements UpdatableRepository<Card, CardRecord> {
    private final DSLContext dsl;
    private final CardRecordMapper cardMapper = new CardRecordMapper();

    public Card save(Card card) {
        card.setCreatedAt(LocalDateTime.now());

        final CardRecord cardRecord = dsl.newRecord(CARD, cardMapper.unmap(card));
        cardRecord.store();

        return cardMapper.map(cardRecord);
    }

    @Override
    public Card update(Card card, Set<TableField<CardRecord, ?>> fields) {
        card.setUpdatedAt(LocalDateTime.now());
        final CardRecord record = dsl.newRecord(CARD, cardMapper.unmap(card));

        if (fields.isEmpty()) {
            record.update();
        } else {
            final Set<Field<?>> data = ImmutableSet.<Field<?>>builder()
                .addAll(fields)
                .add(CARD.UPDATED_AT)
                .build();

            record.update(data);
        }

        return cardMapper.map(record);
    }

    public List<Card> findByIds(List<Integer> ids) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .where(CARD.ID.in(ids))
            .fetch(f -> cardMapper.map(f.into(CARD)));
    }

    public Optional<Card> findById(Integer id) {
        return dsl.select(CARD.fields())
            .from(CARD)
            .where(CARD.ID.eq(id))
            .fetchOptional(f -> cardMapper.map(f.into(CARD)));
    }
}
