package com.example.calculator;

import com.example.calculator.exception.ParseException;
import com.example.calculator.parser.ExpressionParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты парсера выражений
 */
public class ExpressionParserTest {

    @Test
    void testValidExpression() {
        assertDoesNotThrow(() ->
                ExpressionParser.toRPN("2+3*4"));
    }

    @Test
    void testUnaryMinus() {
        assertDoesNotThrow(() ->
                ExpressionParser.toRPN("-5+abs(-10)"));
    }

    @Test
    void testPowerParsing() {
        assertDoesNotThrow(() ->
                ExpressionParser.toRPN("2^3^4"));
    }

    @Test
    void testNestedFunctions() {
        assertDoesNotThrow(() ->
                ExpressionParser.toRPN("sin(cos(sqrt(16)))"));
    }

    @Test
    void testInvalidSymbol() {
        assertThrows(ParseException.class,
                () -> ExpressionParser.toRPN("2+@"));
    }

    @Test
    void testUnclosedBracket() {
        assertThrows(ParseException.class,
                () -> ExpressionParser.toRPN("(2+3"));
    }
}