package com.website.enbookingbe.card.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserCard {
    private Card card;
    private Integer userId;
    private boolean favorite;
    private boolean learned;
}
