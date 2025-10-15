package calculator.model;

public class CustomDelimiterManagerImpl implements CustomDelimiterManager {
    private final String customDelimiterPrefix;
    private final String customDelimiterSuffix;

    public CustomDelimiterManagerImpl(
            String customDelimiterPrefix,
            String customDelimiterSuffix
    ) {
        this.customDelimiterPrefix = customDelimiterPrefix;
        this.customDelimiterSuffix = customDelimiterSuffix;
    }

    @Override
    public String extractCustomDelimiter(String input) {
        if (hasNoDeclaration(input)) {
            return null;
        }

        validateCustomDelimiterDeclaration(input);
        String customDelimiter = getCustomDelimiter(input);

        if (customDelimiter.isEmpty()) {
            throw new IllegalArgumentException("빈 커스텀 구분자입니다.");
        }

        return customDelimiter;
    }

    @Override
    public String stripCustomDelimiterDeclaration(String input) {
        if (hasNoDeclaration(input)) {
            return input;
        }

        return input.substring(lastIndexOfSuffix(input));
    }

    private boolean hasNoDeclaration(String input) {
        return !input.contains(customDelimiterPrefix) && !input.contains(customDelimiterSuffix);
    }

    private void validateCustomDelimiterDeclaration(String input) {
        if (!input.contains(customDelimiterPrefix) || !input.contains(customDelimiterSuffix)) {
            throw new IllegalArgumentException("커스텀 구분자의 접두사 혹은 접미사가 누락되었습니다.");
        }

        if (input.contains(customDelimiterPrefix) && !input.startsWith(customDelimiterPrefix)) {
            throw new IllegalArgumentException("커스텀 구분자 접두사는 입력 맨 앞에 와야 합니다.");
        }
    }

    private String getCustomDelimiter(String input) {
        return input.substring(lastIndexOfPrefix(input), firstIndexOfSuffix(input));
    }

    private int lastIndexOfPrefix(String input) {
        return input.indexOf(customDelimiterPrefix) + customDelimiterPrefix.length();
    }

    private int firstIndexOfSuffix(String input) {
        return input.indexOf(customDelimiterSuffix);
    }

    private int lastIndexOfSuffix(String input) {
        return input.indexOf(customDelimiterSuffix) + customDelimiterSuffix.length();
    }
}
