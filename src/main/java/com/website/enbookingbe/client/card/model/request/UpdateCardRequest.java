package com.website.enbookingbe.client.card.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateCardRequest {
    private Integer id;
    private String question;
    private String answer;
}
