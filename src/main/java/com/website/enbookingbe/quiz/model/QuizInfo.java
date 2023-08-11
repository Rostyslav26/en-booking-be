package com.website.enbookingbe.quiz.model;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.quiz.domain.QuizCardStatus;
import com.website.enbookingbe.quiz.domain.QuizStatus;

import java.util.List;
import java.util.Map;

public record QuizInfo(
    Integer id,
    Map<QuizCardStatus, List<CardV2>> cardsByStatus,
    QuizStatus status
) {

}

