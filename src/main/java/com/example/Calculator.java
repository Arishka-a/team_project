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

    public double modulo(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Modulo by zero");
        }
        return a % b;
    }

    public double sin(double angle) {
        return Math.sin(angle);
    }

    public double cos(double angle) {
        return Math.cos(angle);
    }

    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        if (n > 20) {
            throw new IllegalArgumentException("Factorial overflow: n must be <= 20");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
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
        System.out.println("10 % 3 = " + calc.modulo(10, 3));
        System.out.println("10 % 2 = " + calc.modulo(10, 2));

        // New functions
        System.out.println("sin(0) = " + calc.sin(0));
        System.out.println("sin(PI/2) = " + calc.sin(Math.PI / 2));
        System.out.println("cos(0) = " + calc.cos(0));
        System.out.println("cos(PI) = " + calc.cos(Math.PI));
        try {
            System.out.println("5! = " + calc.factorial(5));
            System.out.println("10! = " + calc.factorial(10));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}