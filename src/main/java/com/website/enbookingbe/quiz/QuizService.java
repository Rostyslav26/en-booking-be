package com.website.enbookingbe.quiz;

import com.google.common.collect.Sets;
import com.website.enbookingbe.card.Card;
import com.website.enbookingbe.card.CardService;
import com.website.enbookingbe.exception.NotFoundException;
import com.website.enbookingbe.management.entity.User;
import com.website.enbookingbe.quiz.entity.Quiz;
import com.website.enbookingbe.quiz.entity.QuizCard;
import com.website.enbookingbe.quiz.exception.QuizCardAlreadyCompletedException;
import com.website.enbookingbe.quiz.exception.QuizCardNotFoundException;
import com.website.enbookingbe.quiz.exception.QuizNotFoundException;
import com.website.enbookingbe.quiz.model.QuizCardResource;
import com.website.enbookingbe.quiz.model.QuizCardStatus;
import com.website.enbookingbe.quiz.model.QuizInfo;
import com.website.enbookingbe.quiz.model.QuizStatus;
import com.website.enbookingbe.quiz.repository.QuizCardRepository;
import com.website.enbookingbe.quiz.repository.QuizRepository;
import com.website.enbookingbe.utils.CollectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {
    private final CardService cardService;
    private final QuizRepository quizRepository;
    private final QuizCardRepository quizCardRepository;

    public List<QuizCardResource> learnQuiz(Integer quizId, Integer userId) {
        final Quiz quiz = quizRepository.findByIdAndAndUserId(quizId, userId)
            .orElseThrow(() -> new QuizNotFoundException(quizId));

        checkQuizIsCompleted(quiz);
        resetQuizStatus(quiz, QuizStatus.IN_PROGRESS);

        final List<QuizCard> notCompletedQuizCards = quiz.getQuizCards().stream()
            .filter(qc -> !Objects.equals(qc.getStatus(), QuizCardStatus.COMPLETED))
            .toList();

        return generateQuizCards(notCompletedQuizCards);
    }

    public List<QuizCardResource> relearnQuiz(Integer quizId, Integer userId) {
        final Quiz quiz = quizRepository.findByIdAndAndUserId(quizId, userId)
            .orElseThrow(() -> new QuizNotFoundException(quizId));

        resetQuizStatus(quiz, QuizStatus.IN_PROGRESS);

        return generateQuizCards(quiz.getQuizCards());
    }

    private void resetQuizStatus(Quiz quiz, QuizStatus status) {
        quiz.setStatus(status);
    }

    private List<QuizCardResource> generateQuizCards(Collection<QuizCard> quizCards) {
        quizCards.forEach(qc -> qc.setStatus(QuizCardStatus.IN_PROGRESS));

        final List<Integer> cardIds = quizCards.stream()
            .map(qz -> qz.getId().getCardId())
            .toList();

        final List<Card> cards = cardService.getByIds(cardIds);

        Collections.shuffle(cards);

        return cards.stream()
            .map(card -> {
                final String prompt = PromptGenerator.generatePrompt(card.getAnswer());
                return new QuizCardResource(card.getId(), card.getQuestion(), card.getAnswer(), prompt);
            })
            .toList();
    }

    public void answerQuizCard(Integer quizId, Integer cardId, Boolean isCorrect, Integer userId) {
        if (quizRepository.existsByIdAndUserId(quizId, userId)) {
            throw new QuizNotFoundException(quizId);
        }

        if (quizRepository.isQuizHasStatus(quizId, QuizStatus.COMPLETED, userId)) {
            throw new IllegalStateException("Quiz already completed");
        }

        final QuizCard quizCard = quizCardRepository.findByQuizIdAndCardId(quizId, cardId)
            .orElseThrow(() -> new QuizCardNotFoundException(quizId, cardId));

        if (Objects.equals(quizCard.getStatus(), QuizCardStatus.COMPLETED)) {
            throw new QuizCardAlreadyCompletedException(quizId, cardId);
        }

        final QuizCardStatus status = isCorrect ? QuizCardStatus.COMPLETED : QuizCardStatus.FAILED;
        quizCard.setStatus(status);

        cardService.markAsLearned(cardId, isCorrect, userId);
    }

    public QuizInfo completeQuiz(Integer quizId, Integer userId) {
        final Quiz quiz = quizRepository.findByIdAndAndUserId(quizId, userId)
            .orElseThrow(() -> new QuizNotFoundException(quizId));

        checkQuizIsCompleted(quiz);

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

        final List<Card> completedCards = cardsByStatus.getOrDefault(QuizCardStatus.COMPLETED, Collections.emptyList());
        if (completedCards.size() == quiz.getQuizCards().size()) {
            resetQuizStatus(quiz, QuizStatus.COMPLETED);
        }

        return new QuizInfo(quiz.getId(), cardsByStatus, quiz.getStatus());
    }

    public Quiz createQuiz(List<Integer> cardIds, User user) {
        final List<Card> cards = cardService.getUserCards(user.getId(), cardIds);

        return addCardsToQuiz(user, cards);
    }

    public Quiz createQuizByNotLearnedCards(User user, @Nullable Integer limit) {
        final List<Card> cards = cardService.getNotLearnedByUserId(user.getId(), limit);

        return addCardsToQuiz(user, cards);
    }

    private Quiz addCardsToQuiz(User user, List<Card> cards) {
        if (cards.isEmpty()) {
            throw new NotFoundException("No cards found for user with id: " + user.getId());
        }

        final Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setStatus(QuizStatus.CREATED);
        quiz.setCreatedAt(LocalDateTime.now());

        final Quiz savedQuiz = quizRepository.save(quiz);

        final List<QuizCard> quizCards = CollectUtils.map(cards, card -> new QuizCard(savedQuiz, card));
        quizCardRepository.saveAll(quizCards);

        savedQuiz.setQuizCards(Sets.newHashSet(quizCards));

        return savedQuiz;
    }

    private void checkQuizIsCompleted(Quiz quiz) {
        if (Objects.equals(quiz.getStatus(), QuizStatus.COMPLETED)) {
            throw new IllegalStateException("Quiz already completed");
        }
    }
}
