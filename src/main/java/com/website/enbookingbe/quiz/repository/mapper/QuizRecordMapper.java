package com.website.enbookingbe.quiz.repository.mapper;

import com.website.enbookingbe.data.jooq.tables.records.QuizRecord;
import com.website.enbookingbe.quiz.domain.Quiz;
import com.website.enbookingbe.quiz.domain.QuizStatus;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;

import javax.annotation.Nonnull;

import static com.website.enbookingbe.data.jooq.tables.Quiz.QUIZ;

public class QuizRecordMapper implements RecordMapper<Record, Quiz>, RecordUnmapper<Quiz, QuizRecord> {

    @Override
    public Quiz map(Record record) {
        return Quiz.builder()
            .id(record.get(QUIZ.ID))
            .userId(record.get(QUIZ.USER_ID))
            .status(QuizStatus.valueOf(record.get(QUIZ.STATUS)))
            .createdAt(record.get(QUIZ.CREATED_AT))
            .updatedAt(record.get(QUIZ.UPDATED_AT))
            .build();
    }

    @Override
    @Nonnull
    public QuizRecord unmap(Quiz source) {
        final QuizRecord quizRecord = new QuizRecord();
        quizRecord.setId(source.getId());
        quizRecord.setUserId(source.getUserId());
        quizRecord.setStatus(source.getStatus().name());
        quizRecord.setCreatedAt(source.getCreatedAt());

        return quizRecord;
    }
}
