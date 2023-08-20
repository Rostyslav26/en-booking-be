package com.website.enbookingbe.card.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Card {
    private Integer id;
    private String question;
    private String answer;
    private Integer authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}