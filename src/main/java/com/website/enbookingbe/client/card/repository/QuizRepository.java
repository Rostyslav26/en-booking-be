package com.website.enbookingbe.client.card.repository;

import com.website.enbookingbe.client.card.domain.Quiz;
import com.website.enbookingbe.client.card.model.QuizSummaryInfo;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizRepository {
    private final DSLContext dslContext;

    public Quiz save(Quiz quiz) {
        return null;
    }

    public Optional<Quiz> findByIdAndUserId(Integer quizId, Integer userId) {
        return Optional.empty();
    }

    public void update(Quiz quiz) {

    }

    public List<QuizSummaryInfo> findQuizSummaryInfoByUserId(Integer userId) {
        return null;
    }
}
