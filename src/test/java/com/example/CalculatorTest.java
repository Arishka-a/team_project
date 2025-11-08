package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void testAdd() {
        assertEquals(8.0, calculator.add(3, 5), 0.001);
        assertEquals(-1.0, calculator.add(-3, 2), 0.001);
    }

    @Test
    void testSubtract() {
        assertEquals(6.0, calculator.subtract(10, 4), 0.001);
    }

    @Test
    void testMultiply() {
        assertEquals(42.0, calculator.multiply(6, 7), 0.001);
        assertEquals(0.0, calculator.multiply(0, 10), 0.001);
    }

    @Test
    void testDivide() {
        assertEquals(5.0, calculator.divide(15, 3), 0.001);
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
    }
}