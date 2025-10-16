package calculator;

import calculator.config.CalculatorConfig;
import calculator.controller.CalculatorController;

public class Application {
    public static void main(String[] args) {
        CalculatorController controller = CalculatorConfig.calculatorController();

        controller.run();
    }
}
