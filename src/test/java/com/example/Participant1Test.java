package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
    @DisplayName("UI: Проверка вывода операции сложения в main()")
    void testAddOutputInMain() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Calculator.main(new String[]{});

            String output = outputStream.toString();
            assertTrue(output.contains("3 + 5 = 8.0"),
                    "Вывод должен содержать '3 + 5 = 8.0'");
        } finally {
            System.setOut(originalOut);
        }
    }
}
