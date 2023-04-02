package com.website.enbookingbe.client.card.exception;

import com.website.enbookingbe.core.exception.NotFoundException;

public class CardNotFoundException extends NotFoundException {

    public CardNotFoundException(Integer id) {
        super("Card not found with id: %d", id);
    }
}
