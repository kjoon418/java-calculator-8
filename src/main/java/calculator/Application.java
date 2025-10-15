package calculator;

import calculator.model.CustomDelimiterManager;
import calculator.model.CustomDelimiterManagerImpl;
import calculator.model.StringCalculator;
import calculator.view.CalculatorView;

public class Application {
    private static final String[] defaultDelimiters = {",", ":"};
    private static final String customDelimiterPrefix = "//";
    private static final String customDelimiterSuffix = "\\n";

    private static final CustomDelimiterManager customDelimiterManager = new CustomDelimiterManagerImpl(customDelimiterPrefix, customDelimiterSuffix);
    private static final StringCalculator calculator = new StringCalculator(defaultDelimiters, customDelimiterManager);
    private static final CalculatorView view = new CalculatorView();

    public static void main(String[] args) {
        String input = view.readInput();

        long result = calculator.sum(input);

        view.printResult(result);
    }
}
