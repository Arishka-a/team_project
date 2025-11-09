package com.example;

public class Calculator {
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    public double sqrt(double a) {
        if (a < 0) {
            throw new IllegalArgumentException("Negative value");
        }
        return Math.sqrt(a);
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println("3 + 5 = " + calc.add(3, 5));
        System.out.println("10 - 4 = " + calc.subtract(10, 4));
        System.out.println("6 * 7 = " + calc.multiply(6, 7));
        try {
            System.out.println("15 / 3 = " + calc.divide(15, 3));
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("√9 = " + calc.sqrt(9));
    }
}