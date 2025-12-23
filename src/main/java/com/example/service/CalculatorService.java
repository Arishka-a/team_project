package com.example.service;

import com.example.Calculator;
import org.springframework.stereotype.Service;


@Service
public class CalculatorService {
    private final Calculator calculator;

    public CalculatorService() {
        this.calculator = new Calculator();
    }

    public double calculate(String operation, double[] operands) {
        return switch (operation.toLowerCase()) {
            case "add" -> binaryOperation(operands, 2, "Add", calculator::add);
            case "subtract" -> binaryOperation(operands, 2, "Subtract", calculator::subtract);
            case "multiply" -> binaryOperation(operands, 2, "Multiply", calculator::multiply);
            case "divide" -> binaryOperation(operands, 2, "Divide", calculator::divide);
            case "modulo" -> binaryOperation(operands, 2, "Modulo", calculator::modulo);
            case "sin" -> unaryOperation(operands, 1, "Sin", calculator::sin);
            case "cos" -> unaryOperation(operands, 1, "Cos", calculator::cos);
            case "factorial" -> calculator.factorial((int) checkOperands(operands, 1, "Factorial")[0]);
            default -> throw new IllegalArgumentException("Unknown operation: " + operation);
        };
    }

    private double binaryOperation(double[] operands, int required, String name,
                                    BinaryOp operation) {
        checkOperands(operands, required, name);
        return operation.apply(operands[0], operands[1]);
    }

    private double unaryOperation(double[] operands, int required, String name,
                                   UnaryOp operation) {
        checkOperands(operands, required, name);
        return operation.apply(operands[0]);
    }

    private double[] checkOperands(double[] operands, int required, String name) {
        if (operands.length != required) {
            throw new IllegalArgumentException(name + " requires " + required + " operand(s)");
        }
        return operands;
    }

    @FunctionalInterface
    private interface BinaryOp {
        double apply(double a, double b);
    }

    @FunctionalInterface
    private interface UnaryOp {
        double apply(double a);
    }
}
