package com.website.enbookingbe.utils;

import java.util.List;
import java.util.function.Function;

public class CollectUtils {

    private CollectUtils() {
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        return list.stream().map(mapper).toList();
    }
}
