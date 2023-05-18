package com.website.enbookingbe.quiz.model;

import com.website.enbookingbe.card.Card;

import java.util.List;
import java.util.Map;

public record QuizInfo(
    Integer id,
    Map<QuizCardStatus, List<Card>> cardsByStatus,
    QuizStatus status
) {

}

