package calculator.model;

import static calculator.util.CollectionUtils.appendIfNotNull;
import static java.util.Collections.unmodifiableList;

import calculator.dto.CalculatorInput;
import java.util.ArrayList;
import java.util.List;
import org.junit.platform.commons.util.StringUtils;

public class InputParserImpl implements InputParser {
    private final List<String> defaultDelimiters;
    private final String customDelimiterPrefix;
    private final String customDelimiterSuffix;

    public InputParserImpl(
            List<String> defaultDelimiters,
            String customDelimiterPrefix,
            String customDelimiterSuffix
    ) {
        this.defaultDelimiters = defaultDelimiters;
        this.customDelimiterPrefix = customDelimiterPrefix;
        this.customDelimiterSuffix = customDelimiterSuffix;
    }

    @Override
    public CalculatorInput parse(String rawInput) {
        validateInputNotEmpty(rawInput);

        String customDelimiter = extractCustomDelimiter(rawInput);
        String cleanInput = removeDeclaration(rawInput);

        List<String> delimiters = new ArrayList<>(defaultDelimiters);
        appendIfNotNull(delimiters, customDelimiter);

        return new CalculatorInput(cleanInput, unmodifiableList(delimiters));
    }

    private void validateInputNotEmpty(String rawInput) {
        if (StringUtils.isBlank(rawInput)) {
            throw new IllegalArgumentException("입력 값이 존재하지 않거나 비어 있습니다.");
        }
    }

    private String extractCustomDelimiter(String rawInput) {
        if (hasNoDeclaration(rawInput)) {
            return null;
        }

        validateCustomDelimiterDeclaration(rawInput);

        String customDelimiter = getCustomDelimiter(rawInput);
        validateCustomDelimiter(customDelimiter);

        return customDelimiter;
    }

    private boolean hasNoDeclaration(String rawInput) {
        return !rawInput.contains(customDelimiterPrefix) && !rawInput.contains(customDelimiterSuffix);
    }

    private void validateCustomDelimiterDeclaration(String input) {
        if (!input.contains(customDelimiterPrefix) || !input.contains(customDelimiterSuffix)) {
            throw new IllegalArgumentException("커스텀 구분자의 접두사 혹은 접미사가 누락되었습니다.");
        }

        if (input.contains(customDelimiterPrefix) && !input.startsWith(customDelimiterPrefix)) {
            throw new IllegalArgumentException("커스텀 구분자 접두사는 입력 맨 앞에 와야 합니다.");
        }
    }

    private String getCustomDelimiter(String rawInput) {
        return rawInput.substring(lastIndexOfPrefix(rawInput), firstIndexOfSuffix(rawInput));
    }

    private void validateCustomDelimiter(String customDelimiter) {
        if (customDelimiter.isEmpty()) {
            throw new IllegalArgumentException("빈 커스텀 구분자입니다.");
        }
    }

    private String removeDeclaration(String rawInput) {
        if (hasNoDeclaration(rawInput)) {
            return rawInput;
        }

        return rawInput.substring(lastIndexOfSuffix(rawInput));
    }

    private int lastIndexOfPrefix(String rawInput) {
        return rawInput.indexOf(customDelimiterPrefix) + customDelimiterPrefix.length();
    }

    private int firstIndexOfSuffix(String rawInput) {
        return rawInput.indexOf(customDelimiterSuffix);
    }

    private int lastIndexOfSuffix(String rawInput) {
        return rawInput.indexOf(customDelimiterSuffix) + customDelimiterSuffix.length();
    }
}
