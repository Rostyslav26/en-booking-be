package com.website.enbookingbe.quiz.repository;

import com.website.enbookingbe.data.jooq.tables.records.QuizCardRecord;
import com.website.enbookingbe.quiz.domain.QuizCard;
import com.website.enbookingbe.quiz.repository.mapper.QuizRecordCardMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.website.enbookingbe.data.jooq.tables.QuizCard.QUIZ_CARD;
import static com.website.enbookingbe.quiz.domain.QuizCardStatus.COMPLETED;

@Repository
@RequiredArgsConstructor
public class QuizCardRepository {
    private final QuizRecordCardMapper mapper = new QuizRecordCardMapper();

    private final DSLContext dsl;

    public void saveAll(List<QuizCard> quizCards) {
        final List<QuizCardRecord> records = quizCards.stream()
            .map(mapper::unmap)
            .toList();

        dsl.batchInsert(records).execute();
    }

    public List<QuizCard> findAllByQuizId(Integer quizId) {
        return dsl.select(QUIZ_CARD.fields())
            .from(QUIZ_CARD)
            .where(QUIZ_CARD.QUIZ_ID.eq(quizId))
            .fetch(mapper);
    }

    public Optional<QuizCard> findOne(Integer quizId, Integer cardId) {
        return dsl.select(QUIZ_CARD.fields())
            .from(QUIZ_CARD)
            .where(QUIZ_CARD.QUIZ_ID.eq(quizId))
            .and(QUIZ_CARD.CARD_ID.eq(cardId))
            .fetchOptional(mapper);
    }

    public List<QuizCard> findNotCompleted(Integer quizId) {
        return dsl.select(QUIZ_CARD.fields())
            .from(QUIZ_CARD)
            .where(QUIZ_CARD.QUIZ_ID.eq(quizId))
            .and(QUIZ_CARD.STATUS.notEqual(COMPLETED.name()))
            .fetch(mapper);
    }
}
