package com.website.enbookingbe.quiz;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class PromptGenerator {
    private static final int MIN_MASK_LENGTH = 2;
    private static final int MAX_MASK_LENGTH = 3;

    private final Random random = new Random();

    public String generatePrompt(final String answer) {
        final List<String> words = Arrays.asList(StringUtils.split(answer, " "));

        for (int i = 0; i < words.size(); i++) {
            final String word = words.get(i);
            final int maskLength = Math.max(MIN_MASK_LENGTH, Math.min(MAX_MASK_LENGTH, word.length() - 1));

            final int start = random.nextInt(word.length() - maskLength + 1);
            final int end = start + maskLength;

            final String masked = word.substring(0, start) +
                StringUtils.repeat("*", maskLength) +
                word.substring(end);

            words.set(i, masked);
        }

        return StringUtils.join(words, " ");
    }
}