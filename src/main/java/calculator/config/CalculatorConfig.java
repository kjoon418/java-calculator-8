package calculator.config;

import calculator.controller.CalculatorController;
import calculator.model.CustomDelimiterManager;
import calculator.model.CustomDelimiterManagerImpl;
import calculator.model.DelimiterRegexBuilder;
import calculator.model.DelimiterRegexBuilderImpl;
import calculator.model.StringCalculator;
import calculator.model.StringCalculatorImpl;
import calculator.view.CalculatorView;

public class CalculatorConfig {
    private static final String[] DEFAULT_DELIMITERS = {",", ":"};
    private static final String CUSTOM_DELIMITER_PREFIX = "//";
    private static final String CUSTOM_DELIMITER_SUFFIX = "\\n";

    private static final DelimiterRegexBuilder DELIMITER_REGEX_BUILDER = new DelimiterRegexBuilderImpl();
    private static final CalculatorView CALCULATOR_VIEW = new CalculatorView();

    private static final CustomDelimiterManager CUSTOM_DELIMITER_MANAGER = new CustomDelimiterManagerImpl(
            CUSTOM_DELIMITER_PREFIX,
            CUSTOM_DELIMITER_SUFFIX
    );
    private static final StringCalculator STRING_CALCULATOR = new StringCalculatorImpl(
            DEFAULT_DELIMITERS,
            CUSTOM_DELIMITER_MANAGER,
            DELIMITER_REGEX_BUILDER
    );
    private static final CalculatorController CALCULATOR_CONTROLLER = new CalculatorController(
            CALCULATOR_VIEW,
            STRING_CALCULATOR
    );

    public static CalculatorController calculatorController() {
        return CALCULATOR_CONTROLLER;
    }
}
