package calculator.config;

import calculator.controller.CalculatorController;
import calculator.model.InputParser;
import calculator.model.InputParserImpl;
import calculator.model.StringCalculator;
import calculator.model.StringCalculatorImpl;
import calculator.view.CalculatorView;
import java.util.List;

public class CalculatorConfig {
    private static final List<String> DEFAULT_DELIMITERS = List.of(",", ":");
    private static final String CUSTOM_DELIMITER_PREFIX = "//";
    private static final String CUSTOM_DELIMITER_SUFFIX = "\\n";

    private static final CalculatorView CALCULATOR_VIEW = new CalculatorView();
    private static final StringCalculator STRING_CALCULATOR = new StringCalculatorImpl();

    private static final InputParser INPUT_PARSER = new InputParserImpl(
            DEFAULT_DELIMITERS,
            CUSTOM_DELIMITER_PREFIX,
            CUSTOM_DELIMITER_SUFFIX
    );
    private static final CalculatorController CALCULATOR_CONTROLLER = new CalculatorController(
            CALCULATOR_VIEW,
            INPUT_PARSER,
            STRING_CALCULATOR
    );

    public static CalculatorController calculatorController() {
        return CALCULATOR_CONTROLLER;
    }
}
