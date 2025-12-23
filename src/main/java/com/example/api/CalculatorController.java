package com.example.api;

import com.example.model.CalculationRequest;
import com.example.model.CalculationResponse;
import com.example.model.HistoryItem;
import com.example.service.CalculatorService;
import com.example.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Calculator", description = "API для выполнения математических операций")
public class CalculatorController {

    private final CalculatorService calculatorService;

    private final HistoryService historyService;

    public CalculatorController(CalculatorService calculatorService, HistoryService historyService) {
        this.calculatorService = calculatorService;
        this.historyService = historyService;
    }

    @PostMapping("/calculate")
    @Operation(summary = "Выполнить математическую операцию",
               description = "Выполняет операцию и возвращает результат")
    public ResponseEntity<CalculationResponse> calculate(
            @RequestBody CalculationRequest request) {
        try {
            double result = calculatorService.calculate(
                    request.getOperation(), request.getOperands());
            CalculationResponse response = new CalculationResponse(
                    result, request.getOperation(), request.getOperands());

            // Добавляем в историю
            historyService.addToHistory(request.getOperation(), request.getOperands(), result);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | ArithmeticException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/history")
    @Operation(summary = "Получить историю вычислений",
               description = "Возвращает список всех выполненных операций")
    public ResponseEntity<List<HistoryItem>> getHistory() {
        return ResponseEntity.ok(historyService.getHistory());
    }

    @DeleteMapping("/history")
    @Operation(summary = "Очистить историю вычислений")
    public ResponseEntity<Void> clearHistory() {
        historyService.clearHistory();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/history/{id}")
    @Operation(summary = "Удалить запись из истории по ID")
    public ResponseEntity<Void> deleteHistoryItem(@PathVariable Long id) {
        boolean deleted = historyService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/operations")
    @Operation(summary = "Получить список доступных операций")
    public ResponseEntity<List<String>> getOperations() {
        List<String> operations = List.of("add", "subtract", "multiply", "divide", "modulo", "sin", "cos", "factorial");
        return ResponseEntity.ok(operations);
    }
}
