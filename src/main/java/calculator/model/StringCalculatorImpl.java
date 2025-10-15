package calculator.model;

import calculator.util.ArrayUtils;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.junit.platform.commons.util.StringUtils;

public class StringCalculatorImpl implements StringCalculator {
    private static final Pattern POSITIVE_NUMBER_REGEX = Pattern.compile("^\\d+$");

    private final String[] defaultDelimiters;
    private final CustomDelimiterManager customDelimiterManager;
    private final DelimiterRegexBuilder delimiterRegexBuilder;

    public StringCalculatorImpl(
            String[] defaultDelimiters,
            CustomDelimiterManager customDelimiterManager,
            DelimiterRegexBuilder delimiterRegexBuilder
    ) {
        this.defaultDelimiters = defaultDelimiters;
        this.customDelimiterManager = customDelimiterManager;
        this.delimiterRegexBuilder = delimiterRegexBuilder;
    }

    @Override
    public long sum(String input) {
        validateInputNotEmpty(input);

        String regex = getRegexToSplit(input);
        String strippedInput = customDelimiterManager.stripCustomDelimiterDeclaration(input);
        String[] splitInputs = strippedInput.split(regex);

        for (String splitInput : splitInputs) {
            validatePositiveNumber(splitInput);
        }

        return Arrays.stream(splitInputs)
                .mapToLong(Long::parseLong)
                .sum();
    }

    private void validateInputNotEmpty(String input) {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException("입력 값이 존재하지 않거나 비어 있습니다.");
        }
    }

    private String getRegexToSplit(String input) {
        String customDelimiter = customDelimiterManager.extractCustomDelimiter(input);

        if (customDelimiter == null) {
            return delimiterRegexBuilder.build(defaultDelimiters);
        }

        String[] delimiters = ArrayUtils.getExtendedArray(defaultDelimiters, customDelimiter);

        return delimiterRegexBuilder.build(delimiters);
    }

    private void validatePositiveNumber(String string) {
        if (!POSITIVE_NUMBER_REGEX.matcher(string).matches()) {
            throw new IllegalArgumentException("음수 혹은 구분자 외 문자가 존재합니다.");
        }
    }
}
