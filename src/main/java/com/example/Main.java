package com.example.calculator;

import com.example.calculator.evaluator.Evaluator;
import com.example.calculator.parser.ExpressionParser;
import com.example.calculator.parser.Token;
import com.example.calculator.parser.TokenType;

import java.util.*;

/**
 * Точка входа в программу-калькулятор.
 * * <p>Класс отвечает за:</p>
 * <ul>
 * <li>Чтение математического выражения из консоли</li>
 * <li>Инициализацию процесса парсинга в RPN</li>
 * <li>Сбор значений для всех уникальных переменных, встреченных в выражении</li>
 * <li>Вывод итогового результата вычислений или сообщения об ошибке</li>
 *  * @see <a href="https://github.com/METAMARGINAL/second_lab_java"_blank">Репозиторий проекта на GitHub</a>
 * </ul>
 */
public class Main {

    /**
     * Основной цикл программы.
     * * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = scanner.nextLine();

        try {
            List<Token> rpn = ExpressionParser.toRPN(input);
            Map<String, Double> variables = new HashMap<>();

            for (Token token : rpn) {
                if (token.getType() == TokenType.VARIABLE && !variables.containsKey(token.getValue())) {
                    System.out.print("Введите значение переменной " + token.getValue() + ": ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Ошибка: введите числовое значение.");
                        scanner.next();
                    }
                    variables.put(token.getValue(), scanner.nextDouble());
                }
            }

            double result = Evaluator.evaluate(rpn, variables);
            System.out.println("Результат: " + result);

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}