package calculator;

import calculator.model.StringCalculator;
import calculator.view.CalculatorView;

public class Application {
    private static final String[] defaultDelimiters = {",", ":"};
    private static final StringCalculator calculator = new StringCalculator(defaultDelimiters);
    private static final CalculatorView view = new CalculatorView();

    public static void main(String[] args) {
        String input = view.readInput();

        long result = calculator.sum(input);

        view.printResult(result);
    }
}
