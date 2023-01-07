package com.website.enbookingbe.client.card.domain;

import com.website.enbookingbe.core.user.management.model.Person;
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
    private Person author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}