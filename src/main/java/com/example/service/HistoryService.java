package com.example.service;

import com.example.model.HistoryItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Сервис для управления историей вычислений.
 */
@Service
public class HistoryService {
    private final List<HistoryItem> history = new ArrayList<>();

    private final AtomicLong idCounter = new AtomicLong(1);

    /**
     * Добавляет запись в историю.
     */
    public HistoryItem addToHistory(String operation, double[] operands, double result) {
        HistoryItem item = new HistoryItem(
                idCounter.getAndIncrement(),
                operation,
                operands,
                result,
                System.currentTimeMillis()
        );
        history.add(item);
        return item;
    }

    /**
     * Возвращает всю историю вычислений.
     */
    public List<HistoryItem> getHistory() {
        return new ArrayList<>(history);
    }

    /**
     * Очищает историю вычислений.
     */
    public void clearHistory() {
        history.clear();
    }

    /**
     * Удаляет запись из истории по ID.
     */
    public boolean deleteById(Long id) {
        return history.removeIf(item -> item.getId().equals(id));
    }
}
