let currentExpression = '';
let currentValue = '0';
let pendingOperation = null;
let pendingValue = null;
let currentLanguage = localStorage.getItem('language') || 'ru';
let translations = {};

const API_BASE = '/api';

document.addEventListener('DOMContentLoaded', () => {
    loadTranslations();
    initializeEventListeners();
    loadHistory();
});

async function loadTranslations() {
    try {
        const response = await fetch(`locales/${currentLanguage}.json`);
        translations = await response.json();
        applyTranslations();
    } catch (error) {
        console.error('Error loading translations:', error);
    }
}

function applyTranslations() {
    document.querySelectorAll('[data-i18n]').forEach(element => {
        const key = element.getAttribute('data-i18n');
        if (translations[key]) {
            element.textContent = translations[key];
        }
    });
}


function initializeEventListeners() {
    document.querySelectorAll('.number').forEach(btn => {
        btn.addEventListener('click', () => handleNumber(btn.dataset.value));
    });

    document.querySelectorAll('.operation').forEach(btn => {
        btn.addEventListener('click', () => handleOperation(btn.dataset.value));
    });

    document.getElementById('equals').addEventListener('click', calculateResult);

    document.getElementById('clear').addEventListener('click', clearCalculator);

    document.getElementById('clear-history').addEventListener('click', clearHistory);

    document.getElementById('lang-ru').addEventListener('click', () => switchLanguage('ru'));
    document.getElementById('lang-en').addEventListener('click', () => switchLanguage('en'));
}

function handleNumber(num) {
    if (currentValue === '0' || currentValue === 'Error') {
        currentValue = num;
    } else {
        currentValue += num;
    }
    updateDisplay();
}

function handleOperation(operation) {
    if (['sin', 'cos', 'factorial'].includes(operation)) {
        const value = parseFloat(currentValue);
        if (!isNaN(value)) {
            calculateAPIOperation(operation, [value]);
        }
    } else {
        if (pendingOperation) {
            calculateResult();
        }
        pendingValue = parseFloat(currentValue);
        pendingOperation = operation;
        currentExpression = `${currentValue} ${getOperationSymbol(operation)}`;
        currentValue = '0';
        updateDisplay();
    }
}

async function calculateResult() {
    if (!pendingOperation || pendingValue === null) return;

    const secondValue = parseFloat(currentValue);
    if (isNaN(secondValue)) return;

    currentExpression += ` ${currentValue}`;
    await calculateAPIOperation(pendingOperation, [pendingValue, secondValue]);

    pendingOperation = null;
    pendingValue = null;
}

async function calculateAPIOperation(operation, operands) {
    try {
        const response = await fetch(`${API_BASE}/calculate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ operation, operands })
        });

        if (response.ok) {
            const data = await response.json();
            currentValue = data.result.toString();
            currentExpression = '';
            updateDisplay();
            loadHistory();
        } else {
            currentValue = 'Error';
            updateDisplay();
        }
    } catch (error) {
        console.error('Calculation error:', error);
        currentValue = 'Error';
        updateDisplay();
    }
}

function getOperationSymbol(operation) {
    const symbols = {
        'add': '+',
        'subtract': '−',
        'multiply': '×',
        'divide': '÷',
        'modulo': '%'
    };
    return symbols[operation] || operation;
}

function updateDisplay() {
    document.getElementById('expression').textContent = currentExpression;
    document.getElementById('result').textContent = currentValue;
}

function clearCalculator() {
    currentValue = '0';
    currentExpression = '';
    pendingOperation = null;
    pendingValue = null;
    updateDisplay();
}

async function loadHistory() {
    try {
        const response = await fetch(`${API_BASE}/history`);
        if (response.ok) {
            const history = await response.json();
            displayHistory(history);
        }
    } catch (error) {
        console.error('Error loading history:', error);
    }
}

function displayHistory(history) {
    const historyList = document.getElementById('history-list');
    historyList.innerHTML = '';

    if (history.length === 0) {
        historyList.innerHTML = '<p style="color: #999; text-align: center;">История пуста</p>';
        return;
    }

    history.reverse().forEach(item => {
        const historyItem = document.createElement('div');
        historyItem.className = 'history-item';

        const operationText = formatHistoryOperation(item);

        historyItem.innerHTML = `
            <div>
                <div class="history-operation">${operationText}</div>
            </div>
            <div style="display: flex; align-items: center; gap: 10px;">
                <div class="history-result">${item.result}</div>
                <button class="history-delete" onclick="deleteHistoryItem(${item.id})">×</button>
            </div>
        `;

        historyList.appendChild(historyItem);
    });
}

function formatHistoryOperation(item) {
    const { operation, operands } = item;

    if (['sin', 'cos'].includes(operation)) {
        return `${operation}(${operands[0]})`;
    } else if (operation === 'factorial') {
        return `${operands[0]}!`;
    } else {
        const symbol = getOperationSymbol(operation);
        return `${operands[0]} ${symbol} ${operands[1]}`;
    }
}

async function deleteHistoryItem(id) {
    try {
        const response = await fetch(`${API_BASE}/history/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadHistory();
        }
    } catch (error) {
        console.error('Error deleting history item:', error);
    }
}

async function clearHistory() {
    if (!confirm(translations.confirmClearHistory || 'Очистить всю историю?')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/history`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadHistory();
        }
    } catch (error) {
        console.error('Error clearing history:', error);
    }
}

function switchLanguage(lang) {
    currentLanguage = lang;
    localStorage.setItem('language', lang);

    document.querySelectorAll('.lang-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    document.getElementById(`lang-${lang}`).classList.add('active');

    loadTranslations();
}
