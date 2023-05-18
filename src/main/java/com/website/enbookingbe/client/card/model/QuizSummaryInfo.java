package com.website.enbookingbe.client.card.model;

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
