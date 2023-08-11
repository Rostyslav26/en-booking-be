package com.website.enbookingbe.quiz.repository.mapper;

import com.website.enbookingbe.data.jooq.tables.records.QuizCardRecord;
import com.website.enbookingbe.quiz.domain.QuizCard;
import com.website.enbookingbe.quiz.domain.QuizCardStatus;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

import javax.annotation.Nonnull;

import static com.website.enbookingbe.data.jooq.tables.QuizCard.QUIZ_CARD;

public class QuizRecordCardMapper implements RecordMapper<Record, QuizCard>, RecordUnmapper<QuizCard, QuizCardRecord> {

    @Override
    public QuizCard map(Record record) {
        return QuizCard.builder()
            .quizId(record.get(QUIZ_CARD.QUIZ_ID))
            .cardId(record.get(QUIZ_CARD.CARD_ID))
            .status(QuizCardStatus.valueOf(record.get(QUIZ_CARD.STATUS)))
            .build();
    }

    @Override
    @Nonnull
    public QuizCardRecord unmap(QuizCard source) throws MappingException {
        final QuizCardRecord quizCardRecord = new QuizCardRecord();
        quizCardRecord.setQuizId(source.getQuizId());
        quizCardRecord.setCardId(source.getCardId());
        quizCardRecord.setStatus(source.getStatus().name());

        return quizCardRecord;
    }
}
