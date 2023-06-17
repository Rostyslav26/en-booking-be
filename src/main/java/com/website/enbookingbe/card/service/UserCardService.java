package com.website.enbookingbe.card.service;

import com.website.enbookingbe.card.CardV2;
import com.website.enbookingbe.card.repository.UserCardRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCardService {
    private final UserCardRepositoryV2 userCardRepository;

    public void addCardToUser(Integer userId, CardV2 card) {
        userCardRepository.save(userId, card.getId());
    }
}
