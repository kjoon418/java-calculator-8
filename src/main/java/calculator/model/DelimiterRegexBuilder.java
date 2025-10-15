package calculator.model;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DelimiterRegexBuilder {
    public static String build(String[] delimiters) {
        return Arrays.stream(delimiters)
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
    }
}
