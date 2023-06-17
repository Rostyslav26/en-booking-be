package com.website.enbookingbe.card;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardV2 {
    private Integer id;
    private String question;
    private String answer;
    private Integer authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}