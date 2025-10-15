package calculator.controller;

import calculator.config.CalculatorConfig;
import calculator.model.StringCalculator;
import calculator.view.CalculatorView;

public class CalculatorController {
    private final CalculatorView view = CalculatorConfig.calculatorView();
    private final StringCalculator calculator = CalculatorConfig.stringCalculator();

    public void run() {
        String input = view.readInput();

        long result = calculator.sum(input);

        view.printResult(result);
    }
}
