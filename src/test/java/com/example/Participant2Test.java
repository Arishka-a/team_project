package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты умножения и косинуса")
class Participant2Test {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }


    @Test
    @DisplayName("Юнит: Умножение двух положительных чисел")
    void testMultiplyPositiveNumbers() {
        double a = 6.0;
        double b = 7.0;

        double result = calculator.multiply(a, b);

        assertEquals(42.0, result, 0.001,
                "Умножение 6.0 * 7.0 должно дать 42.0");
    }


    @Test
    @DisplayName("Юнит: Косинус нуля должен быть равен 1")
    void testCosineOfZero() {
        double angle = 0.0;

        double result = calculator.cos(angle);

        assertEquals(1.0, result, 0.001,
                "cos(0) должен быть равен 1.0");
    }


    @Test
    @DisplayName("Интеграция: Проверка тригонометрического тождества sin²(x) + cos²(x) = 1")
    void testPythagoreanIdentity() {
        double angle = Math.PI / 4;  // 45 градусов

        double sinValue = calculator.sin(angle);
        double cosValue = calculator.cos(angle);
        double result = sinValue * sinValue + cosValue * cosValue;

        assertEquals(1.0, result, 0.001,
                "sin²(π/4) + cos²(π/4) должно равняться 1");
    }
}
