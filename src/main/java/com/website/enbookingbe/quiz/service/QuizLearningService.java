package com.website.enbookingbe.quiz.service;

import com.website.enbookingbe.card.Card;
import com.website.enbookingbe.card.CardService;
import com.website.enbookingbe.quiz.entity.Quiz;
import com.website.enbookingbe.quiz.entity.QuizCard;
import com.website.enbookingbe.quiz.exception.QuizCardNotFoundException;
import com.website.enbookingbe.quiz.model.QuizCardStatus;
import com.website.enbookingbe.quiz.model.QuizInfo;
import com.website.enbookingbe.quiz.model.QuizStatus;
import com.website.enbookingbe.quiz.repository.QuizCardRepository;
import com.website.enbookingbe.quiz.resource.QuizCardResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.website.enbookingbe.quiz.model.QuizCardStatus.*;
import static com.website.enbookingbe.quiz.model.QuizStatus.IN_PROGRESS;
import static java.lang.String.format;
import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class QuizLearningService {
    private final CardService cardService;
    private final QuizCardRepository quizCardRepository;
    private final QuizCardGenerator quizCardGenerator;

    public List<QuizCardResource> learnQuiz(Quiz quiz) {
        resetQuizStatus(quiz, IN_PROGRESS);

        final List<QuizCard> notCompletedQuizCards = getNotCompletedQuizCards(quiz);

        return quizCardGenerator.prepareQuizCardsToLearn(notCompletedQuizCards);
    }

    private List<QuizCard> getNotCompletedQuizCards(Quiz quiz) {
        return quiz.getQuizCards().stream()
            .filter(qc -> !Objects.equals(qc.getStatus(), COMPLETED))
            .toList();
    }

    public List<QuizCardResource> relearnQuiz(Quiz quiz) {
        resetQuizStatus(quiz, IN_PROGRESS);

        return quizCardGenerator.prepareQuizCardsToLearn(quiz.getQuizCards());
    }

    public void answerQuizCard(Quiz quiz, Integer cardId, boolean isCorrect, Integer userId) {
        checkQuizIsCompleted(quiz);

        final QuizCard quizCard = quizCardRepository.findByQuizIdAndCardId(quiz.getId(), cardId)
            .orElseThrow(() -> new QuizCardNotFoundException(quiz.getId(), cardId));

        checkQuizCardStatus(quizCard, COMPLETED);

        final QuizCardStatus status = isCorrect ? COMPLETED : FAILED;
        quizCard.setStatus(status);

        cardService.markAsLearned(cardId, isCorrect, userId);
    }

    public QuizInfo completeQuiz(Quiz quiz) {
        checkQuizIsCompleted(quiz);

        final Map<QuizCardStatus, List<Card>> cardsByStatus = getCardsByStatus(quiz);

        final List<Card> completedCards = cardsByStatus.getOrDefault(COMPLETED, emptyList());
        if (completedCards.size() == quiz.getQuizCards().size()) {
            resetQuizStatus(quiz, QuizStatus.COMPLETED);
        }

        return new QuizInfo(quiz.getId(), cardsByStatus, quiz.getStatus());
    }

    private Map<QuizCardStatus, List<Card>> getCardsByStatus(Quiz quiz) {
        final List<Integer> cardIds = quiz.getQuizCards().stream()
            .map(q -> q.getId().getCardId())
            .toList();

        final Map<Integer, Card> cardById = cardService.getByIds(cardIds).stream()
            .collect(Collectors.toMap(Card::getId, Function.identity()));

        final Map<QuizCardStatus, List<Card>> cardsByStatus = new HashMap<>();

        for (QuizCard quizCard : quiz.getQuizCards()) {
            final QuizCardStatus quizCardStatus = quizCard.getStatus();

            cardsByStatus.computeIfAbsent(quizCardStatus, k -> new ArrayList<>())
                .add(cardById.get(quizCard.getId().getCardId()));
        }

        return cardsByStatus;
    }

    public void checkQuizCardStatus(QuizCard quizCard, QuizCardStatus status) {
        if (Objects.equals(quizCard.getStatus(), status)) {
            throw new IllegalStateException(format("Quiz card already %s", status));
        }
    }

    public void checkQuizIsCompleted(Quiz quiz) {
        if (Objects.equals(quiz.getStatus(), QuizStatus.COMPLETED)) {
            throw new IllegalStateException("Quiz already completed");
        }
    }

    private void resetQuizStatus(Quiz quiz, QuizStatus status) {
        quiz.setStatus(status);
    }
}
