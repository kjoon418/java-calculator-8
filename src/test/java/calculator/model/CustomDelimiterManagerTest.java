package calculator.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomDelimiterManagerTest {
    final String CUSTOM_DELIMITER_PREFIX = "//";
    final String CUSTOM_DELIMITER_SUFFIX = "\\n";
    final String INPUT_BODY = "1,2,3,4,5,6,7,8,9,10";

    final CustomDelimiterManager customDelimiterManager = new CustomDelimiterManagerImpl(CUSTOM_DELIMITER_PREFIX, CUSTOM_DELIMITER_SUFFIX);

    @Nested
    class 커스텀_구분자_추출 {
        @ParameterizedTest
        @ValueSource(strings = {" ", ".", "-", "\t", "\\", "abc"})
        void 입력으로부터_커스텀_구분자를_추출한다(String customDelimiter) {
            // given
            String input = CUSTOM_DELIMITER_PREFIX +
                    customDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    INPUT_BODY;

            // when
            String actualResult = customDelimiterManager.extractCustomDelimiter(input);

            // then
            assertThat(actualResult).isEqualTo(customDelimiter);
        }

        @Test
        void 커스텀_구분자_선언부가_없다면_null을_반환한다() {
            // given
            String input = INPUT_BODY;

            // when
            String actualResult = customDelimiterManager.extractCustomDelimiter(input);

            // then
            assertThat(actualResult).isNull();
        }

        @Test
        void 커스텀_구분자_선언문의_접두사가_누락되면_예외가_발생한다() {
            // given
            String customDelimiter = "CustomDelimiter";
            String illegalInput = customDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    INPUT_BODY;

            // when & then
            assertThatThrownBy(() -> customDelimiterManager.extractCustomDelimiter(illegalInput))
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
            assertThatThrownBy(() -> customDelimiterManager.extractCustomDelimiter(illegalInput))
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
            assertThatThrownBy(() -> customDelimiterManager.extractCustomDelimiter(illegalInput))
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
            assertThatThrownBy(() -> customDelimiterManager.extractCustomDelimiter(illegalInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("커스텀 구분자 접두사는 입력 맨 앞에 와야 합니다.");
        }
    }

    @Nested
    class 커스텀_구분자_선언부_제거 {
        @Test
        void 입력에서_선언부를_제거한_나머지를_반환한다() {
            // given
            String customDelimiter = "CustomDelimiter";
            String input = CUSTOM_DELIMITER_PREFIX +
                    customDelimiter +
                    CUSTOM_DELIMITER_SUFFIX +
                    INPUT_BODY;

            // when
            String actualResult = customDelimiterManager.stripCustomDelimiterDeclaration(input);

            // then
            assertThat(actualResult).isEqualTo(INPUT_BODY);
        }
    }
}
