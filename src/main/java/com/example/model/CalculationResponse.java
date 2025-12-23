package com.example.model;

public class CalculationResponse {
    private double result;

    private String operation;

    private double[] operands;

    private long timestamp;

    public CalculationResponse() {
    }

    public CalculationResponse(double result, String operation, double[] operands) {
        this.result = result;
        this.operation = operation;
        this.operands = operands;
        this.timestamp = System.currentTimeMillis();
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
