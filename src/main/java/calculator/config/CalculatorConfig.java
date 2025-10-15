package calculator.config;

import calculator.model.CustomDelimiterManager;
import calculator.model.CustomDelimiterManagerImpl;
import calculator.model.StringCalculator;
import calculator.view.CalculatorView;

public class CalculatorConfig {
    private static final String[] DEFAULT_DELIMITERS = {",", ":"};
    private static final String CUSTOM_DELIMITER_PREFIX = "//";
    private static final String CUSTOM_DELIMITER_SUFFIX = "\\n";

    private static final CustomDelimiterManager CUSTOM_DELIMITER_MANAGER = new CustomDelimiterManagerImpl(
            CUSTOM_DELIMITER_PREFIX,
            CUSTOM_DELIMITER_SUFFIX
    );
    private static final StringCalculator STRING_CALCULATOR = new StringCalculator(
            DEFAULT_DELIMITERS,
            CUSTOM_DELIMITER_MANAGER
    );
    private static final CalculatorView CALCULATOR_VIEW = new CalculatorView();

    public static StringCalculator stringCalculator() {
        return STRING_CALCULATOR;
    }

    public static CalculatorView calculatorView() {
        return CALCULATOR_VIEW;
    }
}
