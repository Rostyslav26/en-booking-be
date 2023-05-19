package com.website.enbookingbe.quiz;

import com.website.enbookingbe.quiz.entity.QuizCard;
import com.website.enbookingbe.quiz.exception.QuizNotFoundException;
import com.website.enbookingbe.quiz.model.QuizCardStatus;
import com.website.enbookingbe.quiz.model.QuizStatus;
import com.website.enbookingbe.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class QuizCardValidator {
    private final QuizRepository quizRepository;

    public void checkQuizCardStatus(QuizCard quizCard, QuizCardStatus status) {
        if (Objects.equals(quizCard.getStatus(), status)) {
            throw new IllegalStateException(format("Quiz card already %s", status));
        }
    }

    public void checkQuizIsCompleted(Integer quizId, Integer userId) {
        checkQuizStatus(quizId, userId, QuizStatus.COMPLETED);
    }

    public void checkQuizStatus(Integer quizId, Integer userId, QuizStatus status) {
        if (quizRepository.isQuizHasStatus(quizId, status, userId)) {
            throw new IllegalStateException(format("Quiz already %s", status));
        }
    }

    public void checkQuizIsExists(Integer quizId, Integer userId) {
        if (quizRepository.existsByIdAndUserId(quizId, userId)) {
            throw new QuizNotFoundException(quizId);
        }
    }
}
