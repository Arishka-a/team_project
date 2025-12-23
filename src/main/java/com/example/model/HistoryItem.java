package com.example.model;

public class HistoryItem {
    private Long id;

    private String operation;

    private double[] operands;

    private double result;

    private long timestamp;

    public HistoryItem() {
    }

    public HistoryItem(Long id, String operation, double[] operands, double result, long timestamp) {
        this.id = id;
        this.operation = operation;
        this.operands = operands;
        this.result = result;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
