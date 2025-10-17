package calculator.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class CollectionUtilsTest {
    @Test
    void null이_아니라면_컬렉션에_새_원소를_삽입한다() {
        // given
        List<String> collection = mutableListOf("existElement1", "existElement2");
        String newElement = "element";
        int expectedSize = collection.size() + 1;

        // when
        CollectionUtils.appendIfNotNull(collection, newElement);

        // then
        assertThat(collection.size()).isEqualTo(expectedSize);
        assertThat(collection).contains(newElement);
    }

    @Test
    void null이라면_삽입하지_않는다() {
        // given
        List<String> collection = mutableListOf("existElement1", "existElement2");
        int expectedSize = collection.size();

        // when
        CollectionUtils.appendIfNotNull(collection, null);

        // then
        assertThat(collection.size()).isEqualTo(expectedSize);
    }

    @SafeVarargs
    private <T> List<T> mutableListOf(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }
}
