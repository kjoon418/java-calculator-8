package calculator.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCalculatorTest {
    final String[] DEFAULT_DELIMITERS = {",", ":"};
    final String CUSTOM_DELIMITER_PREFIX = "//";
    final String CUSTOM_DELIMITER_SUFFIX = "\\n";

    final CustomDelimiterManager customDelimiterManager = new CustomDelimiterManagerImpl(CUSTOM_DELIMITER_PREFIX, CUSTOM_DELIMITER_SUFFIX);
    final StringCalculator stringCalculator = new StringCalculator(DEFAULT_DELIMITERS, customDelimiterManager);

    @Nested
    class 기본_구분자만_사용한_경우 {
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

        /**
         * 테스트 케이스에 defaultDelimiters를 제공하기 위한 메서드
         */
        static Stream<String> defaultDelimiters() {
            return Stream.of(",", ":");
        }
    }

    @Nested
    class 커스텀_구분자를_사용한_경우 {
        @ParameterizedTest
        @ValueSource(strings = {"^", "%%", "\\", "\t", ")", "(", "()", "|"})
        void 커스텀_구분자가_선언되었을_경우_구분자에_포함한다(String customDelimiter) {
            // given
            int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            String input = CUSTOM_DELIMITER_PREFIX +
                    customDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    concatNumbersWithDelimiter(numbers, customDelimiter);
            int expectedResult = Arrays.stream(numbers)
                    .sum();

            // when
            long actualResult = stringCalculator.sum(input);

            // then
            assertThat(actualResult).isEqualTo(expectedResult);
        }


    }

    private String concatNumbersWithDelimiter(int[] numbers, String delimiter) {
        return Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(delimiter));
    }
}
