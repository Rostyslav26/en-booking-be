package com.website.enbookingbe.quiz.model;

import com.website.enbookingbe.quiz.domain.QuizCardStatus;
import com.website.enbookingbe.quiz.domain.QuizStatus;

import java.time.LocalDateTime;
import java.util.Map;

public record QuizSummaryInfo(
    Integer id,
    Integer totalCards,
    Map<QuizCardStatus, Integer> totalCardsByStatus,
    QuizStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

