package calculator.controller;

import calculator.dto.CalculatorInput;
import calculator.model.InputParser;
import calculator.model.StringCalculator;
import calculator.view.CalculatorView;

public class CalculatorController {
    private final CalculatorView view;
    private final InputParser inputParser;
    private final StringCalculator calculator;

    public CalculatorController(
            CalculatorView view,
            InputParser inputParser,
            StringCalculator calculator
    ) {
        this.view = view;
        this.inputParser = inputParser;
        this.calculator = calculator;
    }

    public void run() {
        String rawInput = view.readInput();

        CalculatorInput parsedInput = inputParser.parse(rawInput);
        long result = calculator.sum(parsedInput);

        view.printResult(result);
    }
}
