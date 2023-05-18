package com.website.enbookingbe.card;

import com.website.enbookingbe.exception.NotFoundException;

public class CardNotFoundException extends NotFoundException {

    public CardNotFoundException(Integer id) {
        super("Card not found with id: %d", id);
    }
}
