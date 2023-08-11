package com.website.enbookingbe.quiz;

import com.website.enbookingbe.quiz.domain.Quiz;
import com.website.enbookingbe.quiz.model.QuizInfo;
import com.website.enbookingbe.quiz.resource.QuizCardResource;
import com.website.enbookingbe.quiz.resource.QuizResource;
import com.website.enbookingbe.quiz.service.QuizLearningService;
import com.website.enbookingbe.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.website.enbookingbe.quiz.converter.QuizResourceConverter.toResource;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final QuizLearningService quizLearningService;

    @PostMapping
    public QuizResource createQuiz(
        @RequestBody List<Integer> cardIds,
        @AuthenticationPrincipal Principal user
    ) {
        final Quiz quiz = quizService.createQuiz(cardIds, user.getId());

        return toResource(quiz, user.getId());
    }

    @PostMapping("/not-learned")
    public QuizResource createQuizByNotLearnedCards(
        @RequestParam Integer limit,
        @AuthenticationPrincipal Principal user
    ) {
        Quiz quiz = quizService.createQuizByNotLearnedCards(user.getId(), limit);

        return toResource(quiz, user.getId());
    }

    @GetMapping("/{quizId}")
    public List<QuizCardResource> learnQuiz(@PathVariable("quizId") Quiz quiz) {
        return quizLearningService.learnQuiz(quiz);
    }

    @PostMapping("/{quizId}/relearn")
    public List<QuizCardResource> relearnQuiz(@PathVariable("quizId") Quiz quiz) {
        return quizLearningService.relearnQuiz(quiz);
    }

    @PostMapping("/{quizId}/answer")
    public void answerQuizCard(
        @PathVariable("quizId") Quiz quiz,
        @RequestParam Integer cardId,
        @RequestParam boolean isCorrect,
        @AuthenticationPrincipal Principal user
        ) {
        quizLearningService.answerQuizCard(quiz, cardId, isCorrect, user.getId());
    }

    @PostMapping("/{quizId}/complete")
    public QuizInfo completeQuiz(@PathVariable("quizId") Quiz quiz) {
        return quizLearningService.completeQuiz(quiz);
    }
}
