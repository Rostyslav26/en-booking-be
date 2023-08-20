package com.website.enbookingbe.quiz.service;

import com.website.enbookingbe.card.domain.Card;
import com.website.enbookingbe.card.service.CardService;
import com.website.enbookingbe.card.service.UserCardService;
import com.website.enbookingbe.quiz.domain.Quiz;
import com.website.enbookingbe.quiz.domain.QuizCard;
import com.website.enbookingbe.quiz.domain.QuizCardStatus;
import com.website.enbookingbe.quiz.domain.QuizStatus;
import com.website.enbookingbe.quiz.exception.QuizCardNotFoundException;
import com.website.enbookingbe.quiz.model.QuizInfo;
import com.website.enbookingbe.quiz.repository.QuizCardRepository;
import com.website.enbookingbe.quiz.resource.QuizCardResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;
import static com.website.enbookingbe.quiz.domain.QuizCardStatus.COMPLETED;
import static com.website.enbookingbe.quiz.domain.QuizCardStatus.FAILED;
import static com.website.enbookingbe.quiz.domain.QuizStatus.IN_PROGRESS;
import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class QuizLearningService {
    private final CardService cardService;
    private final UserCardService userCardService;
    private final QuizCardRepository quizCardRepository;
    private final QuizCardGenerator quizCardGenerator;

    public List<QuizCardResource> learnQuiz(Quiz quiz) {
        final List<QuizCard> notCompletedQuizCards = quizCardRepository.findNotCompleted(quiz.getId());
        if (notCompletedQuizCards.isEmpty()) {
            return relearnQuiz(quiz);
        }

        quiz.setStatus(IN_PROGRESS);

        return quizCardGenerator.prepareQuizCardsToLearn(notCompletedQuizCards);
    }

    public List<QuizCardResource> relearnQuiz(Quiz quiz) {
        quiz.setStatus(IN_PROGRESS);

        List<QuizCard> quizCards = quizCardRepository.findAllByQuizId(quiz.getId());

        return quizCardGenerator.prepareQuizCardsToLearn(quizCards);
    }

    public void answerQuizCard(Quiz quiz, Integer cardId, boolean isCorrect, Integer userId) {
        checkQuizIsCompleted(quiz);

        final QuizCard quizCard = quizCardRepository.findOne(quiz.getId(), cardId)
            .orElseThrow(() -> new QuizCardNotFoundException(quiz.getId(), cardId));

        checkState(quizCard.getStatus() == COMPLETED, "Quiz card should be in progress");

        final QuizCardStatus status = isCorrect ? COMPLETED : FAILED;
        quizCard.setStatus(status);

        userCardService.markAsLearned(cardId, userId, isCorrect);
    }

    public QuizInfo completeQuiz(Quiz quiz) {
        checkQuizIsCompleted(quiz);

        final List<QuizCard> quizCards = quizCardRepository.findAllByQuizId(quiz.getId());
        final Map<QuizCardStatus, List<Card>> cardsByStatus = getCardsByStatus(quizCards);

        if (isAllCardsCompleted(quizCards, cardsByStatus)) {
            quiz.setStatus(QuizStatus.COMPLETED);
        } else {
            quiz.setStatus(QuizStatus.FAILED);
        }

        return new QuizInfo(quiz.getId(), cardsByStatus, quiz.getStatus());
    }

    private boolean isAllCardsCompleted(
        List<QuizCard> quizCards,
        Map<QuizCardStatus, List<Card>> cardsByStatus
    ) {
        final List<Card> completedCards = cardsByStatus.getOrDefault(COMPLETED, emptyList());

        return quizCards.size() == completedCards.size();
    }

    private Map<QuizCardStatus, List<Card>> getCardsByStatus(List<QuizCard> quizCards) {
        final List<Integer> cardIds = quizCards.stream()
            .map(QuizCard::getCardId)
            .toList();

        final Map<Integer, Card> cardById = getCardsById(cardIds);

        Map<QuizCardStatus, List<Card>> cardsByStatus = new EnumMap<>(QuizCardStatus.class);
        for (QuizCard quizCard : quizCards) {
            final QuizCardStatus quizCardStatus = quizCard.getStatus();
            final Card card = cardById.get(quizCard.getCardId());

            cardsByStatus.computeIfAbsent(quizCardStatus, k -> new ArrayList<>()).add(card);
        }

        return cardsByStatus;
    }

    private Map<Integer, Card> getCardsById(List<Integer> cardIds) {
        return cardService.getByIds(cardIds).stream()
            .collect(Collectors.toMap(Card::getId, Function.identity()));
    }

    public void checkQuizIsCompleted(Quiz quiz) {
        checkState(quiz.getStatus() == QuizStatus.COMPLETED, "Quiz is already completed");
    }
}
