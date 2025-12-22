// Экран 3: Статистика
// TODO для Участника 3

// API endpoint
const API_BASE = '/api';

// Инициализация
document.addEventListener('DOMContentLoaded', () => {
    console.log('Screen 3: Statistics loaded');
    // TODO: Добавьте вашу логику здесь
    // Пример: loadStatistics();
});

// TODO: Добавьте функции для работы со статистикой

// Пример функции для получения истории
async function loadHistory() {
    try {
        const response = await fetch(`${API_BASE}/history`);
        if (response.ok) {
            const history = await response.json();
            // TODO: Обработайте данные и вычислите статистику
            console.log('History:', history);
        }
    } catch (error) {
        console.error('Error loading history:', error);
    }
}
