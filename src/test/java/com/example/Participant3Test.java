package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты деления и факториала")
class Participant3Test {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }


    @Test
    @DisplayName("Юнит: Деление двух положительных чисел")
    void testDividePositiveNumbers() {
        double a = 15.0;
        double b = 3.0;

        double result = calculator.divide(a, b);

        assertEquals(5.0, result, 0.001,
                "Деление 15.0 / 3.0 должно дать 5.0");
    }


    @Test
    @DisplayName("Юнит: Факториал 5 должен быть равен 120")
    void testFactorialOfFive() {
        int n = 5;

        long result = calculator.factorial(n);

        assertEquals(120, result,
                "Факториал 5 должен быть равен 120");
    }


    @Test
    @DisplayName("Интеграция: Вычисление биномиального коэффициента C(n,k) = n! / (k! * (n-k)!)")
    void testBinomialCoefficient() {
        // Вычисляем C(5, 2) = 5! / (2! * 3!) = 120 / (2 * 6) = 10
        int n = 5;
        int k = 2;

        long factorialN = calculator.factorial(n);          // 5! = 120
        long factorialK = calculator.factorial(k);          // 2! = 2
        long factorialNMinusK = calculator.factorial(n - k); // 3! = 6

        double denominator = calculator.multiply(factorialK, factorialNMinusK); // 2 * 6 = 12
        double binomialCoefficient = calculator.divide(factorialN, denominator); // 120 / 12 = 10

        assertEquals(10.0, binomialCoefficient, 0.001,
                "Биномиальный коэффициент C(5,2) должен быть равен 10");
    }
}
