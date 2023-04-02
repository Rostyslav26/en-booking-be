package com.website.enbookingbe.client.card.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Card {
    private Integer id;
    private String question;
    private String answer;
    private Integer authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favorite;
    private boolean learned;
}