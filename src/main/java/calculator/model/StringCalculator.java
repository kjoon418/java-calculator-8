package calculator.model;

import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;

public class StringCalculator {
    private final String[] defaultDelimiters;

    public StringCalculator(String[] defaultDelimiters) {
        this.defaultDelimiters = defaultDelimiters;
    }

    public long sum(String input) {
        validateInputNotEmpty(input);

        String regex = DelimiterRegexBuilder.build(defaultDelimiters);

        return Arrays.stream(input.split(regex))
                .mapToLong(Long::parseLong)
                .sum();
    }

    private void validateInputNotEmpty(String input) {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException("입력 값이 존재하지 않거나 비어 있습니다.");
        }
    }
}
