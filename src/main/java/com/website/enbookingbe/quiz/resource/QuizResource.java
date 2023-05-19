package com.website.enbookingbe.quiz.resource;

import com.website.enbookingbe.quiz.model.QuizStatus;

import java.time.LocalDateTime;

public record QuizResource (
    Integer id,
    Integer userId,
    QuizStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}