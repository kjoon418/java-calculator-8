package calculator.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ArrayUtilsTest {
    @Test
    void 새_원소를_추가한_확장된_배열을_반환한다() {
        // given
        Integer[] originalArray = {1, 2, 3, 4, 5};
        Integer newElement = 10;

        // when
        Integer[] extendedArray = ArrayUtils.getExtendedArray(originalArray, newElement);

        // then
        assertThat(extendedArray.length).isEqualTo(originalArray.length + 1);
        Integer lastElement = extendedArray[extendedArray.length - 1];
        assertThat(lastElement).isEqualTo(newElement);
    }
}
