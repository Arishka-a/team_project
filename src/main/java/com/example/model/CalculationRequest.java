package com.example.model;

public class CalculationRequest {
    private String operation;

    private double[] operands;

    public CalculationRequest() {
    }

    public CalculationRequest(String operation, double[] operands) {
        this.operation = operation;
        this.operands = operands;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double[] getOperands() {
        return operands;
    }

    public void setOperands(double[] operands) {
        this.operands = operands;
    }
}
