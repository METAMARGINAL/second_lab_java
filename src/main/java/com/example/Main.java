package com.example.calculator;

import com.example.calculator.evaluator.Evaluator;
import com.example.calculator.parser.ExpressionParser;
import com.example.calculator.parser.Token;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение: ");
        String input = scanner.nextLine();

        try {
            List<Token> rpn = ExpressionParser.toRPN(input);

            Map<String, Double> variables = new HashMap<>();

            for (Token t : rpn) {
                if (t.getType().name().equals("VARIABLE")) {
                    if (!variables.containsKey(t.getValue())) {
                        System.out.print("Введите значение " + t.getValue() + ": ");
                        variables.put(t.getValue(), scanner.nextDouble());
                    }
                }
            }

            double result = Evaluator.evaluate(rpn, variables);
            System.out.println("Результат: " + result);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}