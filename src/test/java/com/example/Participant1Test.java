package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Участник 1 - Тесты сложения")
class Participant1Test {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }


    @Test
    @DisplayName("Юнит: Сложение двух положительных чисел")
    void testAddPositiveNumbers() {
        double a = 15.5;
        double b = 24.5;

        double result = calculator.add(a, b);

        assertEquals(40.0, result, 0.001,
                "Сложение 15.5 + 24.5 должно дать 40.0");
    }


    @Test
    @DisplayName("Интеграция: Цепочка операций со сложением")
    void testAddInOperationChain() {
        double step1 = calculator.add(10, 5);     

        double result = calculator.multiply(step1, 2);  

        assertEquals(30.0, result, 0.001,
                "(10 + 5) * 2 должно быть 30");
    }


    @Test
    @DisplayName("Юнит: Сложение отрицательных чисел")
    void testAddNegativeNumbers() {
        double a = -10.0;
        double b = -5.5;

        double result = calculator.add(a, b);

        assertEquals(-15.5, result, 0.001,
                "Сложение -10.0 + (-5.5) должно дать -15.5");
    }
}
