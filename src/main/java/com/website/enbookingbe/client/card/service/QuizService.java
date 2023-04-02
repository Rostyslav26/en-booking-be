package com.website.enbookingbe.client.card.service;

import com.website.enbookingbe.client.card.domain.Card;
import com.website.enbookingbe.client.card.domain.Quiz;
import com.website.enbookingbe.client.card.domain.QuizCard;
import com.website.enbookingbe.client.card.exception.QuizCardAlreadyCompletedException;
import com.website.enbookingbe.client.card.exception.QuizCardNotFoundException;
import com.website.enbookingbe.client.card.exception.QuizNotFoundException;
import com.website.enbookingbe.client.card.model.*;
import com.website.enbookingbe.client.card.repository.QuizCardRepository;
import com.website.enbookingbe.client.card.repository.QuizRepository;
import com.website.enbookingbe.client.card.repository.UserCardRepository;
import com.website.enbookingbe.core.exception.NotFoundException;
import com.website.enbookingbe.core.utils.CollectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {
    private final CardService cardService;
    private final QuizRepository quizRepository;
    private final QuizCardRepository quizCardRepository;
    private final UserCardRepository userCardRepository;
    private final PromptGenerator promptGenerator;

    public List<QuizCardModel> learnQuiz(Integer quizId, Integer userId) {
        final Quiz quiz = quizRepository.findByIdAndUserId(quizId, userId)
            .orElseThrow(() -> new QuizNotFoundException(quizId));

        checkQuizIsCompleted(quiz);
        resetQuizStatus(quiz, QuizStatus.IN_PROGRESS);

        final List<QuizCard> quizCards = quiz.getQuizCards().stream()
            .filter(qc -> !Objects.equals(qc.getStatus(), QuizCardStatus.COMPLETED))
            .toList();

        return generateQuizCards(quizCards);
    }

    public List<QuizCardModel> relearnQuiz(Integer quizId, Integer userId) {
        final Quiz quiz = quizRepository.findByIdAndUserId(quizId, userId)
            .orElseThrow(() -> new QuizNotFoundException(quizId));

        resetQuizStatus(quiz, QuizStatus.IN_PROGRESS);

        return generateQuizCards(quiz.getQuizCards());
    }

    private void resetQuizStatus(Quiz quiz, QuizStatus status) {
        quiz.setStatus(status);
        quizRepository.update(quiz);
    }

    private List<QuizCardModel> generateQuizCards(List<QuizCard> quizCards) {
        quizCards.forEach(qc -> qc.setStatus(QuizCardStatus.IN_PROGRESS));
        quizCardRepository.update(quizCards);

        final List<Integer> cardIds = CollectUtils.map(quizCards, QuizCard::getCardId);
        final List<Card> cards = cardService.getByIds(cardIds);
        Collections.shuffle(cards);

        return cards.stream()
            .map(card -> {
                final String prompt = promptGenerator.generatePrompt(card.getAnswer());
                return new QuizCardModel(card.getId(), card.getQuestion(), card.getAnswer(), prompt);
            })
            .toList();
    }

    public void answerQuizCard(Integer quizId, Integer cardId, Boolean isCorrect, Integer userId) {
        final Quiz quiz = quizRepository.findByIdAndUserId(quizId, userId)
            .orElseThrow(() -> new QuizNotFoundException(quizId));

        checkQuizIsCompleted(quiz);

        final QuizCard quizCard = quiz.getQuizCards().stream()
            .filter(qc -> Objects.equals(qc.getCardId(), cardId))
            .findFirst()
            .orElseThrow(() -> new QuizCardNotFoundException(quizId, cardId));

        if (Objects.equals(quizCard.getStatus(), QuizCardStatus.COMPLETED)) {
            throw new QuizCardAlreadyCompletedException(quizId, cardId);
        }

        final QuizCardStatus status = isCorrect ? QuizCardStatus.COMPLETED : QuizCardStatus.FAILED;
        quizCard.setStatus(status);
        quizCardRepository.update(quizCard);

        cardService.markAsLearned(cardId, isCorrect, userId);
    }

    public QuizInfo completeQuiz(Integer quizId, Integer userId) {
        final Quiz quiz = quizRepository.findByIdAndUserId(quizId, userId)
            .orElseThrow(() -> new QuizNotFoundException(quizId));

        checkQuizIsCompleted(quiz);

        final List<Integer> cardIds = quiz.getQuizCards().stream()
            .map(QuizCard::getCardId)
            .toList();

        final Map<Integer, Card> cardById = cardService.getByIds(cardIds).stream()
            .collect(Collectors.toMap(Card::getId, Function.identity()));

        final Map<QuizCardStatus, List<Card>> cardsByStatus = new HashMap<>();
        for (QuizCard quizCard : quiz.getQuizCards()) {
            final QuizCardStatus quizCardStatus = quizCard.getStatus();

            cardsByStatus.computeIfAbsent(quizCardStatus, k -> new ArrayList<>())
                .add(cardById.get(quizCard.getCardId()));
        }

        final List<Card> completedCards = cardsByStatus.getOrDefault(QuizCardStatus.COMPLETED, Collections.emptyList());
        if (completedCards.size() == quiz.getQuizCards().size()) {
            resetQuizStatus(quiz, QuizStatus.COMPLETED);
        }

        return new QuizInfo(quiz.getId(), cardsByStatus, quiz.getStatus());
    }

    public Quiz createQuiz(List<Integer> cardIds, Integer userId) {
        final List<Card> cards = userCardRepository.findByCardIds(new HashSet<>(cardIds), userId);
        return save(userId, cards);
    }

    public Quiz createQuizByNotLearnedCards(Integer userId, @Nullable Integer limit) {
        final List<Card> cards = userCardRepository.findNotLearned(userId, limit);
        return save(userId, cards);
    }

    @Transactional(readOnly = true)
    public List<QuizSummaryInfo> getQuizSummaries(Integer userId) {
        return quizRepository.findQuizSummaryInfoByUserId(userId);
    }

    private Quiz save(Integer userId, List<Card> cards) {
        if (cards.isEmpty()) {
            throw new NotFoundException("No cards found for user with id: " + userId);
        }

        final Quiz quiz = new Quiz();
        quiz.setUserId(userId);
        quiz.setStatus(QuizStatus.CREATED);
        quiz.setCreatedAt(LocalDateTime.now());

        final Quiz savedQuiz = quizRepository.save(quiz);

        final List<QuizCard> quizCards = CollectUtils.map(cards, card -> new QuizCard(savedQuiz.getId(), card.getId()));
        quizCardRepository.saveAll(quizCards);

        savedQuiz.setQuizCards(quizCards);

        return savedQuiz;
    }

    private void checkQuizIsCompleted(Quiz quiz) {
        if (Objects.equals(quiz.getStatus(), QuizStatus.COMPLETED)) {
            throw new IllegalStateException("Quiz already completed");
        }
    }
}
