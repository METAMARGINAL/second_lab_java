package com.example.calculator.exception;

/**
 * Ошибка парсинга выражения
 */
public class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
    }
}