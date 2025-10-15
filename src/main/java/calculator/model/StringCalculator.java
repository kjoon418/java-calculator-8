package calculator.model;

import java.util.Arrays;

public class StringCalculator {
    private final String[] defaultDelimiters;

    public StringCalculator(String[] defaultDelimiters) {
        this.defaultDelimiters = defaultDelimiters;
    }

    public long sum(String input) {
        String regex = DelimiterRegexBuilder.build(defaultDelimiters);

        return Arrays.stream(input.split(regex))
                .mapToLong(Long::parseLong)
                .sum();
    }
}
