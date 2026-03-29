package com.example.calculator.evaluator;

import com.example.calculator.parser.Token;
import com.example.calculator.parser.TokenType;

import java.util.*;

/**
 * Класс для вычисления математических выражений,
 * представленных в виде обратной польской записи (RPN).
 *
 * <p>Использует стек для вычислений:</p>
 * <ul>
 * <li>Числа помещаются в стек</li>
 * <li>При встрече оператора извлекаются два значения</li>
 * <li>Результат операции возвращается обратно в стек</li>
 * </ul>
 *
 * <p>Поддерживает:</p>
 * <ul>
 * <li>Операции: +, -, *, /, ^</li>
 * <li>Переменные</li>
 * <li>Функции: sin, cos, tan, log, sqrt, abs</li>
 * </ul>
 */
public class Evaluator {

    /**
     * Вычисляет значение выражения в формате RPN.
     *
     * @param rpn список токенов в обратной польской записи
     * @param variables значения переменных (имя → значение)
     * @return результат вычисления
     *
     * @throws RuntimeException если переменная не задана или неизвестный оператор
     * @throws ArithmeticException при делении на ноль
     */
    public static double evaluate(List<Token> rpn, Map<String, Double> variables) {
        Stack<Double> stack = new Stack<>();

        for (Token token : rpn) {
            switch (token.getType()) {

                case NUMBER ->
                        stack.push(Double.parseDouble(token.getValue()));

                case VARIABLE -> {
                    String var = token.getValue();

                    if (!variables.containsKey(var)) {
                        throw new RuntimeException("Переменная не задана: " + var);
                    }

                    stack.push(variables.get(var));
                }

                case OPERATOR -> {
                    double b = stack.pop();
                    double a = stack.pop();

                    stack.push(switch (token.getValue()) {
                        case "+" -> a + b;
                        case "-" -> a - b;
                        case "*" -> a * b;
                        case "/" -> {
                            if (b == 0) {
                                throw new ArithmeticException("Деление на ноль");
                            }
                            yield a / b;
                        }
                        case "^" -> Math.pow(a, b);
                        default -> throw new RuntimeException("Ошибка оператора: " + token.getValue());
                    });
                }

                case FUNCTION -> {
                    double val = stack.pop();
                    stack.push(applyFunction(token.getValue(), val));
                }
            }
        }

        return stack.pop();
    }

    /**
     * Применяет математическую функцию к значению.
     *
     * @param func имя функции
     * @param x аргумент функции
     * @return результат вычисления функции
     *
     * @throws RuntimeException если функция неизвестна
     */
    private static double applyFunction(String func, double x) {
        return switch (func) {
            case "sin" -> Math.sin(x);
            case "cos" -> Math.cos(x);
            case "tan" -> Math.tan(x);
            case "log" -> Math.log(x);
            case "sqrt" -> Math.sqrt(x);
            case "abs" -> Math.abs(x);
            default -> throw new RuntimeException("Неизвестная функция: " + func);
        };
    }
}