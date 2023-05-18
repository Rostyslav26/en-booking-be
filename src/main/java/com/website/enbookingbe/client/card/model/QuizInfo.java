package com.website.enbookingbe.client.card.model;

import com.website.enbookingbe.client.card.entity.Card;

import java.util.List;
import java.util.Map;

public record QuizInfo(
    Integer id,
    Map<QuizCardStatus, List<Card>> cardsByStatus,
    QuizStatus status
) {

}

