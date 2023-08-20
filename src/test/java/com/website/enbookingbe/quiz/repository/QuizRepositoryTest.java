package com.website.enbookingbe.quiz.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.website.enbookingbe.DBTest;
import com.website.enbookingbe.quiz.domain.Quiz;
import com.website.enbookingbe.quiz.domain.QuizStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DBTest
class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Test
    @DataSet(value = "datasets/quiz/quiz.xml", disableConstraints = true)
    void testFindOne() {
        final int quizId = 1;
        final int userId = 1;
        final Quiz quiz = quizRepository.findOne(quizId, userId).orElse(null);

        assertThat(quiz).isNotNull();
    }

    @Test
    @DataSet(value = "datasets/user/users.xml")
    void testSave() {
        final Quiz quiz = Quiz.builder()
            .userId(1)
            .status(QuizStatus.CREATED)
            .build();

        final Quiz savedQuiz = quizRepository.save(quiz);

        assertThat(savedQuiz.getId()).isNotNull();
        assertThat(savedQuiz.getUserId()).isEqualTo(quiz.getUserId());
        assertThat(savedQuiz.getStatus()).isEqualTo(quiz.getStatus());
        assertThat(savedQuiz.getCreatedAt()).isNotNull();
        assertThat(savedQuiz.getUpdatedAt()).isNotNull();
    }
}