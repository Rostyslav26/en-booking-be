package com.website.enbookingbe.card;

import com.website.enbookingbe.card.repository.CardRepositoryV2;
import com.website.enbookingbe.card.resource.CreateCardResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CardServiceV2 {
    private final CardRepositoryV2 cardRepository;
    private final UserCardService userCardService;

    public CardV2 create(CreateCardResource dto, Integer userId) {
        final CardV2 card = createNewCard(dto.question(), dto.answer(), userId);

        final CardV2 savedCard = cardRepository.save(card);
        userCardService.addCardToUser(userId, savedCard);

        return savedCard;
    }

    private CardV2 createNewCard(String question, String answer, Integer userId) {
        final CardV2 card = new CardV2();
        card.setQuestion(question);
        card.setAnswer(answer);
        card.setAuthorId(userId);

        return card;
    }
}
