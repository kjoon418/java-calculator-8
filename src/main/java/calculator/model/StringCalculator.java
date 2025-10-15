package calculator.model;

import calculator.util.ArrayUtils;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;

public class StringCalculator {
    private final String[] defaultDelimiters;
    private final CustomDelimiterManager customDelimiterManager;

    public StringCalculator(
            String[] defaultDelimiters,
            CustomDelimiterManager customDelimiterManager
    ) {
        this.defaultDelimiters = defaultDelimiters;
        this.customDelimiterManager = customDelimiterManager;
    }

    public long sum(String input) {
        validateInputNotEmpty(input);

        String regex = getRegexToSplit(input);
        String strippedInput = customDelimiterManager.stripCustomDelimiterDeclaration(input);

        return Arrays.stream(strippedInput.split(regex))
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
            return DelimiterRegexBuilder.build(defaultDelimiters);
        }

        String[] delimiters = ArrayUtils.getExtendedArray(defaultDelimiters, customDelimiter);

        return DelimiterRegexBuilder.build(delimiters);
    }
}
