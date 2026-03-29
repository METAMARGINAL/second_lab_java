package com.example.calculator;

import com.example.calculator.evaluator.Evaluator;
import com.example.calculator.parser.ExpressionParser;
import com.example.calculator.parser.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Основной класс приложения консольного калькулятора.
 * <p>
 * Этот класс является точкой входа в программу. Он обрабатывает ввод пользователя,
 * преобразует математическое выражение в обратную польскую запись (RPN),
 * запрашивает значения для переменных (если они есть) и выводит результат вычисления.
 * </p>
 *
 * @see <a href="https://github.com/METAMARGINAL/second_lab_java" target="_blank">Репозиторий проекта на GitHub</a>
 */
public class Main {

    /**
     * Точка входа в приложение.
     * <p>
     * Метод выполняет следующие шаги:
     * <ol>
     *     <li>Считывает строку выражения от пользователя.</li>
     *     <li>Парсит выражение в список токенов (RPN).</li>
     *     <li>Находит переменные и запрашивает их значения.</li>
     *     <li>Вычисляет результат и выводит его в консоль.</li>
     * </ol>
     * </p>
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение: ");
        String input = scanner.nextLine();

        try {
            // Преобразование инфиксной записи в постфиксную (RPN)
            List<Token> rpn = ExpressionParser.toRPN(input);

            Map<String, Double> variables = new HashMap<>();

            // Поиск переменных в выражении и запрос их значений у пользователя
            for (Token t : rpn) {
                if (t.getType().name().equals("VARIABLE")) {
                    if (!variables.containsKey(t.getValue())) {
                        System.out.print("Введите значение " + t.getValue() + ": ");
                        variables.put(t.getValue(), scanner.nextDouble());
                    }
                }
            }

            // Вычисление результата
            double result = Evaluator.evaluate(rpn, variables);
            System.out.println("Результат: " + result);

        } catch (Exception e) {
            // Обработка ошибок парсинга или вычисления
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}