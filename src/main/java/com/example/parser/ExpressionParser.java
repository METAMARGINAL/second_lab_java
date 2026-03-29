package com.example.calculator.parser;

import com.example.calculator.exception.ParseException;

import java.util.*;

/**
 * Класс для парсинга математических выражений.
 *
 * <p>Реализует алгоритм сортировочной станции (Shunting-yard),
 * который преобразует инфиксное выражение (обычную запись)
 * в обратную польскую запись (RPN).</p>
 *
 * <p>Поддерживает:</p>
 * <ul>
 *     <li>Арифметические операции: +, -, *, /</li>
 *     <li>Скобки</li>
 *     <li>Переменные (x, y и т.д.)</li>
 *     <li>Функции: sin, cos, log, sqrt</li>
 * </ul>
 */
public class ExpressionParser {

    /**
     * Приоритет операторов
     */
    private static final Map<String, Integer> PRECEDENCE = Map.of(
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2
    );

    /**
     * Преобразует математическое выражение в обратную польскую запись (RPN).
     *
     * @param expression входное выражение в инфиксной форме
     * @return список токенов в формате RPN
     * @throws ParseException если выражение некорректно (например, несогласованные скобки)
     */
    public static List<Token> toRPN(String expression) {
        List<Token> output = new ArrayList<>();
        Stack<Token> stack = new Stack<>();

        List<Token> tokens = tokenize(expression);

        for (Token token : tokens) {
            switch (token.getType()) {

                case NUMBER, VARIABLE -> output.add(token);

                case FUNCTION -> stack.push(token);

                case OPERATOR -> {
                    while (!stack.isEmpty() &&
                            stack.peek().getType() == TokenType.OPERATOR &&
                            PRECEDENCE.get(stack.peek().getValue()) >= PRECEDENCE.get(token.getValue())) {
                        output.add(stack.pop());
                    }
                    stack.push(token);
                }

                case LEFT_PAREN -> stack.push(token);

                case RIGHT_PAREN -> {
                    while (!stack.isEmpty() && stack.peek().getType() != TokenType.LEFT_PAREN) {
                        output.add(stack.pop());
                    }

                    if (stack.isEmpty()) {
                        throw new ParseException("Несогласованные скобки");
                    }

                    stack.pop();

                    if (!stack.isEmpty() && stack.peek().getType() == TokenType.FUNCTION) {
                        output.add(stack.pop());
                    }
                }
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().getType() == TokenType.LEFT_PAREN) {
                throw new ParseException("Несогласованные скобки");
            }
            output.add(stack.pop());
        }

        return output;
    }

    /**
     * Разбивает строку выражения на токены.
     *
     * @param expr строка выражения
     * @return список токенов (числа, операторы, переменные, функции, скобки)
     * @throws ParseException если встречен неизвестный символ
     */
    private static List<Token> tokenize(String expr) {
        List<Token> tokens = new ArrayList<>();

        for (int i = 0; i < expr.length(); ) {
            char ch = expr.charAt(i);

            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            // Числа (включая дробные)
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() &&
                        (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i++));
                }
                tokens.add(new Token(TokenType.NUMBER, sb.toString()));
                continue;
            }

            // Переменные или функции
            if (Character.isLetter(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && Character.isLetter(expr.charAt(i))) {
                    sb.append(expr.charAt(i++));
                }

                String word = sb.toString();

                if (isFunction(word)) {
                    tokens.add(new Token(TokenType.FUNCTION, word));
                } else {
                    tokens.add(new Token(TokenType.VARIABLE, word));
                }
                continue;
            }

            // Операторы и скобки
            switch (ch) {
                case '+', '-', '*', '/' ->
                        tokens.add(new Token(TokenType.OPERATOR, String.valueOf(ch)));
                case '(' ->
                        tokens.add(new Token(TokenType.LEFT_PAREN, "("));
                case ')' ->
                        tokens.add(new Token(TokenType.RIGHT_PAREN, ")"));
                default ->
                        throw new ParseException("Неизвестный символ: " + ch);
            }

            i++;
        }

        return tokens;
    }

    /**
     * Проверяет, является ли строка именем функции.
     *
     * @param s строка
     * @return true, если это функция (sin, cos, log, sqrt)
     */
    private static boolean isFunction(String s) {
        return Set.of("sin", "cos", "log", "sqrt").contains(s);
    }
}