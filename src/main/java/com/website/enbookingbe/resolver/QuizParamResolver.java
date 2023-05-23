package com.website.enbookingbe.resolver;

import com.website.enbookingbe.quiz.QuizService;
import com.website.enbookingbe.quiz.entity.Quiz;
import com.website.enbookingbe.quiz.exception.QuizNotFoundException;
import com.website.enbookingbe.security.Principal;
import com.website.enbookingbe.security.SecurityUtils;
import com.website.enbookingbe.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nonnull;

@RequiredArgsConstructor
public class QuizParamResolver implements HandlerMethodArgumentResolver {
    private final QuizService quizService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == Quiz.class;
    }

    @Override
    public Quiz resolveArgument(
        @Nonnull MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        Principal principal = SecurityUtils.getCurrentUser().orElseThrow();
        Integer quizId = Utils.applyIfNotNull(webRequest.getParameter("quizId"), Integer::parseInt);

        return quizService.getQuizById(quizId, principal.getId())
            .orElseThrow(() -> new QuizNotFoundException(quizId));
    }
}
