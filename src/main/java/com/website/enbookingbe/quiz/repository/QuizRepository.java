package com.website.enbookingbe.quiz.repository;

import com.website.enbookingbe.data.jooq.tables.records.QuizRecord;
import com.website.enbookingbe.quiz.domain.Quiz;
import com.website.enbookingbe.quiz.repository.mapper.QuizRecordMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.website.enbookingbe.data.jooq.tables.Quiz.QUIZ;

@Repository
@RequiredArgsConstructor
public class QuizRepository {
    private final QuizRecordMapper mapper = new QuizRecordMapper();

    private final DSLContext dsl;

    public Optional<Quiz> findOne(Integer quizId, Integer userId) {
        return dsl.select(QUIZ.fields())
            .from(QUIZ)
            .where(QUIZ.ID.eq(quizId))
            .and(QUIZ.USER_ID.eq(userId))
            .fetchOptional(mapper);
    }

    public Quiz save(Quiz quiz) {
        final QuizRecord record = mapper.unmap(quiz);

        dsl.insertInto(QUIZ)
            .set(record)
            .execute();

        return mapper.map(record);
    }
}
