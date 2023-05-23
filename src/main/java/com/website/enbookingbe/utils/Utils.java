package com.website.enbookingbe.utils;

import java.util.function.Function;

public class Utils {
    private Utils() {
    }

    public static <T, R> R applyIfNotNull(T value, Function<T, R> function) {
        if (value != null) {
            return function.apply(value);
        }
        return null;
    }
}
