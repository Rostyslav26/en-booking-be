package com.website.enbookingbe.client.card.controller;

import com.website.enbookingbe.client.card.entity.Quiz;
import com.website.enbookingbe.client.card.mapper.QuizMapper;
import com.website.enbookingbe.client.card.model.QuizCardModel;
import com.website.enbookingbe.client.card.model.QuizInfo;
import com.website.enbookingbe.client.card.resource.QuizResource;
import com.website.enbookingbe.client.card.service.QuizService;
import com.website.enbookingbe.core.user.management.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final QuizMapper quizMapper = new QuizMapper();

    @PostMapping
    public QuizResource createQuiz(@RequestBody List<Integer> cardIds, @AuthenticationPrincipal User user) {
        final Quiz quiz = quizService.createQuiz(cardIds, user);

        return quizMapper.toResource(quiz, user.getId());
    }

    @PostMapping("/not-learned")
    public QuizResource createQuizByNotLearnedCards(@Nullable @RequestParam Integer limit, @AuthenticationPrincipal User user) {
        Quiz quiz = quizService.createQuizByNotLearnedCards(user, limit);

        return quizMapper.toResource(quiz, user.getId());
    }

    @GetMapping("/{quizId}")
    public List<QuizCardModel> learnQuiz(@PathVariable Integer quizId, @AuthenticationPrincipal User user) {
        return quizService.learnQuiz(quizId, user.getId());
    }

    @PostMapping("/{quizId}/relearn")
    public List<QuizCardModel> relearnQuiz(@PathVariable Integer quizId, @AuthenticationPrincipal User user) {
        return quizService.relearnQuiz(quizId, user.getId());
    }

    @PostMapping("/{quizId}/answer")
    public void answerQuizCard(
        @PathVariable Integer quizId,
        @RequestParam Integer cardId,
        @RequestParam boolean isCorrect,
        @AuthenticationPrincipal User user
    ) {
        quizService.answerQuizCard(quizId, cardId, isCorrect, user.getId());
    }

    @PostMapping("/{quizId}/complete")
    public QuizInfo completeQuiz(@PathVariable Integer quizId, @AuthenticationPrincipal User user) {
        return quizService.completeQuiz(quizId, user.getId());
    }
}
