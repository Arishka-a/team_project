package com.example.service;

import com.example.model.HistoryItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class HistoryService {
    private final List<HistoryItem> history = new ArrayList<>();

    private final AtomicLong idCounter = new AtomicLong(1);

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

    public List<HistoryItem> getHistory() {
        return new ArrayList<>(history);
    }

    public void clearHistory() {
        history.clear();
    }

    public boolean deleteById(Long id) {
        return history.removeIf(item -> item.getId().equals(id));
    }
}
