package com.website.enbookingbe.quiz.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Quiz {
    private Integer id;
    private Integer userId;
    private QuizStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
