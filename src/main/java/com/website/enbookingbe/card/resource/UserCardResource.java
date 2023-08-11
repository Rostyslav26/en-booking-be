package com.website.enbookingbe.card.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserCardResource {
    private Integer id;
    private String question;
    private String answer;
    private boolean favorite;
    private boolean learned;
}