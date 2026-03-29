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
    void testBrackets() {
        assertDoesNotThrow(() ->
                ExpressionParser.toRPN("(2+3)*4"));
    }

    @Test
    void testNestedBrackets() {
        assertDoesNotThrow(() ->
                ExpressionParser.toRPN("((2+3)*4)"));
    }

    @Test
    void testFunctionParsing() {
        assertDoesNotThrow(() ->
                ExpressionParser.toRPN("sqrt(16)"));
    }

    @Test
    void testVariablesParsing() {
        assertDoesNotThrow(() ->
                ExpressionParser.toRPN("x+y*2"));
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

    @Test
    void testExtraClosingBracket() {
        assertThrows(ParseException.class,
                () -> ExpressionParser.toRPN("2+3)"));
    }
}