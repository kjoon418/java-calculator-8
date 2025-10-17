package calculator.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import calculator.dto.CalculatorInput;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputParserImplTest {
    final List<String> DEFAULT_DELIMITERS = List.of("//", "\\n");
    final String CUSTOM_DELIMITER_PREFIX = "//";
    final String CUSTOM_DELIMITER_SUFFIX = "\\n";
    final String INPUT_BODY = "1,2,3,4,5,6,7,8,9,10";

    final InputParser inputParser = new InputParserImpl(
            DEFAULT_DELIMITERS,
            CUSTOM_DELIMITER_PREFIX,
            CUSTOM_DELIMITER_SUFFIX
    );

    @Nested
    class 문자열_입력을_DTO로_파싱한다 {
        @Test
        void 커스텀_구분자가_선언되지_않았다면_구분자_목록을_기본_구분자로만_구성한다() {
            // given
            String stringInput = INPUT_BODY;

            // when
            CalculatorInput parsedInput = inputParser.parse(stringInput);

            // then
            assertThat(parsedInput.delimiters())
                    .containsExactlyInAnyOrderElementsOf(DEFAULT_DELIMITERS);
        }

        @ParameterizedTest
        @ValueSource(strings = {" ", ".", "-", "\t", "\\", "abc"})
        void 커스텀_구분자가_선언_되었다면_구분자_목록에_추가한다(String customDelimiter) {
            // given
            String stringInput = CUSTOM_DELIMITER_PREFIX +
                    customDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    INPUT_BODY;

            // when
            CalculatorInput parsedInput = inputParser.parse(stringInput);

            // then
            assertThat(parsedInput.delimiters()).containsAll(DEFAULT_DELIMITERS);
            assertThat(parsedInput.delimiters()).contains(customDelimiter);
        }

        @ParameterizedTest
        @ValueSource(strings = {" ", ".", "-", "\t", "\\", "abc"})
        void 커스텀_구분자_선언부를_제거한_본문을_담는다(String customDelimiter) {
            // given
            String stringInput = CUSTOM_DELIMITER_PREFIX +
                    customDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    INPUT_BODY;

            // when
            CalculatorInput parsedInput = inputParser.parse(stringInput);

            // then
            assertThat(parsedInput.delimitedValue()).isEqualTo(INPUT_BODY);
        }
    }

    @Nested
    class 입력을_검증한다 {
        @Test
        void 입력_값이_null이라면_예외가_발생한다() {
            assertThatThrownBy(() -> inputParser.parse(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("입력 값이 존재하지 않거나 비어 있습니다.");
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "   ", "\t", "\n", "\t\n"})
        void 입력_값이_비어_있다면_예외가_발생한다(String emptyInput) {
            assertThatThrownBy(() -> inputParser.parse(emptyInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("입력 값이 존재하지 않거나 비어 있습니다.");
        }

        @Test
        void 커스텀_구분자_선언문의_접두사가_누락되면_예외가_발생한다() {
            // given
            String customDelimiter = "CustomDelimiter";
            String illegalInput = customDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    INPUT_BODY;

            // when & then
            assertThatThrownBy(() -> inputParser.parse(illegalInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("커스텀 구분자의 접두사 혹은 접미사가 누락되었습니다.");
        }

        @Test
        void 커스텀_구분자_선언문의_접미사가_누락되면_예외가_발생한다() {
            // given
            String customDelimiter = "CustomDelimiter";
            String illegalInput = CUSTOM_DELIMITER_PREFIX +
                    customDelimiter +
                    INPUT_BODY;

            // when & then
            assertThatThrownBy(() -> inputParser.parse(illegalInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("커스텀 구분자의 접두사 혹은 접미사가 누락되었습니다.");
        }

        @Test
        void 커스텀_구분자의_값이_비어_있으면_예외가_발생한다() {
            // given
            String emptyCustomDelimiter = "";
            String illegalInput = CUSTOM_DELIMITER_PREFIX +
                    emptyCustomDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    INPUT_BODY;

            // when & then
            assertThatThrownBy(() -> inputParser.parse(illegalInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("빈 커스텀 구분자입니다.");
        }

        @ParameterizedTest
        @ValueSource(strings = {",", " ", "\\", "1", "123", "abcde"})
        void 커스텀_구분자의_접두사가_문자열_맨_앞이_아닌_곳에_있다면_예외가_발생한다(String illegalPrefix) {
            // given
            String customDelimiter = "customDelimiter";
            String illegalInput = illegalPrefix +
                    CUSTOM_DELIMITER_PREFIX +
                    customDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    INPUT_BODY;

            // when & then
            assertThatThrownBy(() -> inputParser.parse(illegalInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("커스텀 구분자 접두사는 입력 맨 앞에 와야 합니다.");
        }
    }
}
