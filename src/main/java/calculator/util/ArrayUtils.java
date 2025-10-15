package calculator.util;

import java.util.Arrays;

public class ArrayUtils {
    public static <T> T[] getExtendedArray(T[] originalArray, T element) {
        T[] extendedArray = Arrays.copyOf(originalArray, originalArray.length + 1);
        extendedArray[extendedArray.length - 1] = element;

        return extendedArray;
    }
}
