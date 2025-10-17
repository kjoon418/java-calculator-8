package calculator.dto;

import java.util.List;

public record CalculatorInput(
        String delimitedValue,
        List<String> delimiters
) {
}
