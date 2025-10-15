package calculator.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCalculatorTest {
    final String[] defaultDelimiters = {",", ":"};
    final StringCalculator stringCalculator = new StringCalculator(defaultDelimiters);

    /**
     * 테스트 케이스에 defaultDelimiters를 제공하기 위한 메서드
     */
    static Stream<String> defaultDelimiters() {
        return Stream.of(",", ":");
    }

    @ParameterizedTest
    @MethodSource("defaultDelimiters")
    void 구분자를_통해_나눈_각_숫자의_합을_반환한다(String delimiter) {
        // given
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String input = concatNumbersWithDelimiter(numbers, delimiter);
        int expectedResult = Arrays.stream(numbers)
                .sum();

        // when
        long actualResult = stringCalculator.sum(input);

        // then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private String concatNumbersWithDelimiter(int[] numbers, String delimiter) {
        return Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(delimiter));
    }
}
