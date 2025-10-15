package calculator.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class StringCalculatorImplTest {
    final String[] DEFAULT_DELIMITERS = {",", ":"};
    final String CUSTOM_DELIMITER_PREFIX = "//";
    final String CUSTOM_DELIMITER_SUFFIX = "\\n";

    final CustomDelimiterManager customDelimiterManager = new CustomDelimiterManagerImpl(CUSTOM_DELIMITER_PREFIX,
            CUSTOM_DELIMITER_SUFFIX);
    final DelimiterRegexBuilder delimiterRegexBuilder = new DelimiterRegexBuilderImpl();

    final StringCalculator stringCalculatorImpl = new StringCalculatorImpl(
            DEFAULT_DELIMITERS,
            customDelimiterManager,
            delimiterRegexBuilder
    );

    @Nested
    class 빈_값_전달 {
        @Test
        void 값이_null이라면_예외가_발생한다() {
            assertThatThrownBy(() -> stringCalculatorImpl.sum(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("입력 값이 존재하지 않거나 비어 있습니다.");
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "   ", "\t", "\n", "\t\n"})
        void 값이_비어_있다면_예외가_발생한다(String emptyInput) {
            assertThatThrownBy(() -> stringCalculatorImpl.sum(emptyInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("입력 값이 존재하지 않거나 비어 있습니다.");
        }
    }

    @Nested
    class 기본_구분자만_사용 {
        @ParameterizedTest
        @MethodSource("defaultDelimiters")
        void 구분자를_통해_나눈_각_숫자의_합을_반환한다(String delimiter) {
            // given
            long[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            String input = concatNumbersWithDelimiter(numbers, delimiter);
            long expectedResult = Arrays.stream(numbers)
                    .sum();

            // when
            long actualResult = stringCalculatorImpl.sum(input);

            // then
            assertThat(actualResult).isEqualTo(expectedResult);
        }

        @ParameterizedTest
        @ValueSource(longs = {-1, -100, -1000, Integer.MIN_VALUE, Long.MIN_VALUE})
        void 음수가_포함되어_있다면_예외가_발생한다(long negative) {
            // given
            long[] numbers = {1, 2, 3, negative, 4, 5, 6};
            String illegalInput = concatNumbersWithDelimiter(numbers, DEFAULT_DELIMITERS[0]);

            // when & then
            assertThatThrownBy(() -> stringCalculatorImpl.sum(illegalInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("음수 혹은 구분자 외 문자가 존재합니다.");
        }

        /**
         * 테스트 케이스에 defaultDelimiters를 제공하기 위한 메서드
         */
        static Stream<String> defaultDelimiters() {
            return Stream.of(",", ":");
        }
    }

    @Nested
    class 커스텀_구분자_사용 {
        @ParameterizedTest
        @ValueSource(strings = {"^", "%%", "\\", "\t", ")", "(", "()", "|"})
        void 커스텀_구분자가_선언되었을_경우_구분자에_포함한다(String customDelimiter) {
            // given
            long[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            String input = CUSTOM_DELIMITER_PREFIX +
                    customDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    concatNumbersWithDelimiter(numbers, customDelimiter);
            long expectedResult = Arrays.stream(numbers)
                    .sum();

            // when
            long actualResult = stringCalculatorImpl.sum(input);

            // then
            assertThat(actualResult).isEqualTo(expectedResult);
        }

        @ParameterizedTest
        @ValueSource(longs = {-1, -100, -1000, Integer.MIN_VALUE, Long.MIN_VALUE})
        void 음수가_포함되어_있다면_예외가_발생한다(long negative) {
            // given
            long[] numbers = {1, 2, 3, negative, 4, 5, 6};
            String customDelimiter = "CustomDelimiter";
            String illegalInput = concatNumbersWithDelimiter(numbers, customDelimiter);

            // when & then
            assertThatThrownBy(() -> stringCalculatorImpl.sum(illegalInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("음수 혹은 구분자 외 문자가 존재합니다.");
        }
    }

    private String concatNumbersWithDelimiter(long[] numbers, String delimiter) {
        return Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(delimiter));
    }
}
