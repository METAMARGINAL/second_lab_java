package com.example.calculator;

import com.example.calculator.evaluator.Evaluator;
import com.example.calculator.parser.ExpressionParser;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты вычисления выражений
 */
public class EvaluatorTest {

    @Test
    void testAddition() {
        var rpn = ExpressionParser.toRPN("2+3");
        assertEquals(5, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testSubtraction() {
        var rpn = ExpressionParser.toRPN("5-3");
        assertEquals(2, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testMultiplication() {
        var rpn = ExpressionParser.toRPN("4*3");
        assertEquals(12, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testDivision() {
        var rpn = ExpressionParser.toRPN("10/2");
        assertEquals(5, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testPriority() {
        var rpn = ExpressionParser.toRPN("2+3*4");
        assertEquals(14, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testBrackets() {
        var rpn = ExpressionParser.toRPN("(2+3)*4");
        assertEquals(20, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testDoubleNumbers() {
        var rpn = ExpressionParser.toRPN("2.5*2");
        assertEquals(5.0, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testVariable() {
        var rpn = ExpressionParser.toRPN("x*2");
        assertEquals(10, Evaluator.evaluate(rpn, Map.of("x", 5.0)));
    }

    @Test
    void testMultipleVariables() {
        var rpn = ExpressionParser.toRPN("x+y");
        assertEquals(7, Evaluator.evaluate(rpn, Map.of(
                "x", 3.0,
                "y", 4.0
        )));
    }

    @Test
    void testSqrtFunction() {
        var rpn = ExpressionParser.toRPN("sqrt(25)");
        assertEquals(5, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testSinFunction() {
        var rpn = ExpressionParser.toRPN("sin(0)");
        assertEquals(0, Evaluator.evaluate(rpn, Map.of()), 0.0001);
    }

    @Test
    void testCosFunction() {
        var rpn = ExpressionParser.toRPN("cos(0)");
        assertEquals(1, Evaluator.evaluate(rpn, Map.of()), 0.0001);
    }

    @Test
    void testLogFunction() {
        var rpn = ExpressionParser.toRPN("log(1)");
        assertEquals(0, Evaluator.evaluate(rpn, Map.of()), 0.0001);
    }

    @Test
    void testDivisionByZero() {
        var rpn = ExpressionParser.toRPN("5/0");
        assertThrows(ArithmeticException.class,
                () -> Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testUnknownVariable() {
        var rpn = ExpressionParser.toRPN("x+2");
        assertThrows(RuntimeException.class,
                () -> Evaluator.evaluate(rpn, Map.of()));
    }
}