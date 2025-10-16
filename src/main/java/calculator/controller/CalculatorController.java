package calculator.controller;

import calculator.model.StringCalculator;
import calculator.view.CalculatorView;

public class CalculatorController {
    private final CalculatorView view;
    private final StringCalculator calculator;

    public CalculatorController(
            CalculatorView view,
            StringCalculator calculator
    ) {
        this.view = view;
        this.calculator = calculator;
    }

    public void run() {
        String input = view.readInput();

        long result = calculator.sum(input);

        view.printResult(result);
    }
}
