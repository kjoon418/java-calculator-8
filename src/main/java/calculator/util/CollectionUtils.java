package calculator.util;

import java.util.Collection;

public class CollectionUtils {
    public static <T> void appendIfNotNull(Collection<T> collection, T element) {
        if (element != null) {
            collection.add(element);
        }
    }
}
