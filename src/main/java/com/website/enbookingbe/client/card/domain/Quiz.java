package com.website.enbookingbe.client.card.domain;

import com.website.enbookingbe.client.card.model.QuizStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Quiz {
    private Integer id;
    private Integer userId;
    private List<QuizCard> quizCards;
    private QuizStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
