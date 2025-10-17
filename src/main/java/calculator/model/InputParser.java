package calculator.model;

import calculator.dto.CalculatorInput;

public interface InputParser {
    CalculatorInput parse(String rawInput);
}
