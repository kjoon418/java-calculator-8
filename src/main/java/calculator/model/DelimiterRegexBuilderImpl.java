package calculator.model;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DelimiterRegexBuilderImpl implements DelimiterRegexBuilder {
    @Override
    public String build(String[] delimiters) {
        return Arrays.stream(delimiters)
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
    }
}
