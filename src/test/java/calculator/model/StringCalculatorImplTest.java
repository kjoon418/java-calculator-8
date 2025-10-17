package calculator.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import calculator.dto.CalculatorInput;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class StringCalculatorImplTest {
    final StringCalculator stringCalculator = new StringCalculatorImpl();

    @Nested
    class 총합을_계산한다 {
        @Test
        void 숫자의_합을_반환한다() {
            // given
            String delimitedValue = "1,2:3,4:5,6";
            List<String> delimiters = List.of(",", ":");
            int expectedResult = 21;
            CalculatorInput input = new CalculatorInput(delimitedValue, delimiters);

            // when
            long actualResult = stringCalculator.sum(input);

            // then
            assertThat(actualResult).isEqualTo(expectedResult);
        }
    }

    @Nested
    class 값을_검증한다 {
        @Test
        void 음수가_존재하면_예외를_던진다() {
            // given
            String delimitedValue = "1,-2:3";
            List<String> delimiters = List.of(",", ":");
            CalculatorInput illegalInput = new CalculatorInput(delimitedValue, delimiters);

            // when & then
            assertThatThrownBy(() -> stringCalculator.sum(illegalInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("음수 혹은 구분자 외 문자가 존재합니다.");
        }

        @Test
        void 구분자_외_문자가_존재하면_예외를_던진다() {
            // given
            String delimitedValue = "1,2/3:4";
            List<String> delimiters = List.of(",", ":");
            CalculatorInput illegalInput = new CalculatorInput(delimitedValue, delimiters);

            // when & then
            assertThatThrownBy(() -> stringCalculator.sum(illegalInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("음수 혹은 구분자 외 문자가 존재합니다.");
        }
    }
}
