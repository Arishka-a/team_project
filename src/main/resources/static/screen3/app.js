const API_BASE = '/api';

document.addEventListener('DOMContentLoaded', () => {
    loadStatistics();
    initializeEventListeners();
});

function initializeEventListeners() {
    document.getElementById('refresh-btn').addEventListener('click', loadStatistics);
    document.getElementById('clear-stats-btn').addEventListener('click', clearStatistics);
}

async function loadStatistics() {
    try {
        const response = await fetch(`${API_BASE}/history`);
        const history = await response.json();

        if (history.length === 0) {
            showEmptyState();
            return;
        }

        calculateStatistics(history);
    } catch (error) {
        console.error('Error loading statistics:', error);
        showEmptyState();
    }
}

function calculateStatistics(history) {
    document.getElementById('total-calculations').textContent = history.length;

    const operationCounts = {};
    history.forEach(item => {
        operationCounts[item.operation] = (operationCounts[item.operation] || 0) + 1;
    });

    const mostUsed = Object.keys(operationCounts).reduce((a, b) =>
        operationCounts[a] > operationCounts[b] ? a : b
    );
    document.getElementById('most-used').textContent = getOperationName(mostUsed);

    buildChart(operationCounts);

    buildTopList(operationCounts);
}

function buildChart(operationCounts) {
    const chartDiv = document.getElementById('operations-chart');
    chartDiv.innerHTML = '';

    const maxCount = Math.max(...Object.values(operationCounts));

    Object.entries(operationCounts)
        .sort((a, b) => b[1] - a[1])
        .forEach(([operation, count]) => {
            const barContainer = document.createElement('div');
            barContainer.className = 'chart-bar';

            const label = document.createElement('div');
            label.className = 'chart-label';
            label.textContent = getOperationName(operation);

            const barFill = document.createElement('div');
            barFill.className = 'chart-bar-fill';
            const percentage = (count / maxCount) * 100;
            barFill.style.width = `${percentage}%`;
            barFill.textContent = count;

            barContainer.appendChild(label);
            barContainer.appendChild(barFill);
            chartDiv.appendChild(barContainer);
        });
}

function buildTopList(operationCounts) {
    const topListDiv = document.getElementById('top-list');
    topListDiv.innerHTML = '';

    const sortedOperations = Object.entries(operationCounts)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 5);

    sortedOperations.forEach(([operation, count], index) => {
        const item = document.createElement('div');
        item.className = 'top-item';

        const rank = document.createElement('span');
        rank.className = 'rank';
        rank.textContent = `${index + 1}.`;

        const operationName = document.createElement('span');
        operationName.className = 'operation';
        operationName.textContent = getOperationName(operation);

        const countSpan = document.createElement('span');
        countSpan.className = 'count';
        countSpan.textContent = `${count} раз`;

        item.appendChild(rank);
        item.appendChild(operationName);
        item.appendChild(countSpan);
        topListDiv.appendChild(item);
    });
}

function getOperationName(operation) {
    const names = {
        'add': 'Сложение (+)',
        'subtract': 'Вычитание (−)',
        'multiply': 'Умножение (×)',
        'divide': 'Деление (÷)',
        'sin': 'Синус',
        'cos': 'Косинус',
        'factorial': 'Факториал'
    };
    return names[operation] || operation;
}

async function clearStatistics() {
    if (!confirm('Вы уверены, что хотите очистить всю статистику?')) {
        return;
    }

    try {
        await fetch(`${API_BASE}/history`, { method: 'DELETE' });
        showEmptyState();
    } catch (error) {
        console.error('Error clearing statistics:', error);
    }
}

function showEmptyState() {
    document.getElementById('total-calculations').textContent = '0';
    document.getElementById('most-used').textContent = '—';

    document.getElementById('operations-chart').innerHTML =
        '<div class="empty-state">Нет данных для отображения. Выполните вычисления на экране калькулятора.</div>';

    document.getElementById('top-list').innerHTML =
        '<div class="empty-state">Статистика пока отсутствует</div>';
}
