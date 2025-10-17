package calculator.model;

import calculator.dto.CalculatorInput;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculatorImpl implements StringCalculator {
    private static final Pattern POSITIVE_NUMBER_PATTERN = Pattern.compile("^\\d+$");

    @Override
    public long sum(CalculatorInput input) {
        List<String> delimiters = input.delimiters();
        String delimitedValue = input.delimitedValue();

        String regex = getRegexToSplit(delimiters);
        String[] splitValues = delimitedValue.split(regex);

        for (String splitValue : splitValues) {
            validatePositiveNumber(splitValue);
        }

        return Arrays.stream(splitValues)
                .mapToLong(Long::parseLong)
                .sum();
    }

    private String getRegexToSplit(List<String> delimiters) {
        return delimiters.stream()
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
    }

    private void validatePositiveNumber(String string) {
        if (!POSITIVE_NUMBER_PATTERN.matcher(string).matches()) {
            throw new IllegalArgumentException("음수 혹은 구분자 외 문자가 존재합니다.");
        }
    }
}
