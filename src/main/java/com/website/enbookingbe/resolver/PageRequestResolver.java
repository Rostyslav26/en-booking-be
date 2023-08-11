package com.website.enbookingbe.resolver;

import com.website.enbookingbe.utils.PageRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nonnull;

import static com.website.enbookingbe.utils.Utils.applyIfNotNull;

public class PageRequestResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageRequest.class);
    }

    @Override
    public Object resolveArgument(
        @Nonnull MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        int page = applyIfNotNull(webRequest.getParameter("page"), Integer::parseInt, 1);
        int size = applyIfNotNull(webRequest.getParameter("size"), Integer::parseInt, 0);

        return PageRequest.of(page, size);
    }
}