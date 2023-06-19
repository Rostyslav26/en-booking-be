package com.website.enbookingbe.card.service;

import com.website.enbookingbe.card.domain.CardV2;
import com.website.enbookingbe.card.domain.UserCard;
import com.website.enbookingbe.card.repository.UserCardRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCardService {
    private final UserCardRepositoryV2 userCardRepository;

    public void addCardToUser(Integer userId, CardV2 card) {
        final UserCard userCard = UserCard.builder()
            .card(card)
            .userId(userId)
            .build();

        userCardRepository.save(userCard);
    }

    public void deleteCardFromUser(Integer userId, CardV2 card) {
        userCardRepository.remove(userId, card.getId());
    }
}
