package com.website.enbookingbe.client.card.resource;

import com.website.enbookingbe.client.card.model.QuizStatus;

import java.time.LocalDateTime;

public record QuizResource (
    Integer id,
    Integer userId,
    QuizStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
