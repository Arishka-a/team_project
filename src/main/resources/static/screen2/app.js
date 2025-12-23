document.addEventListener('DOMContentLoaded', () => {
    loadSettings();
    initializeEventListeners();
});

function loadSettings() {
    const language = localStorage.getItem('language') || 'ru';
    const theme = localStorage.getItem('theme') || 'light';
    const precision = localStorage.getItem('precision') || '2';

    document.querySelector(`input[name="language"][value="${language}"]`).checked = true;
    document.querySelector(`input[name="theme"][value="${theme}"]`).checked = true;
    document.getElementById('precision').value = precision;

    applyTheme(theme);
}

function applyTheme(theme) {
    if (theme === 'dark') {
        document.body.style.background = 'linear-gradient(135deg, #1a1a2e 0%, #16213e 100%)';
        document.querySelector('.container').style.background = '#0f3460';
        document.querySelector('.container').style.color = '#e0e0e0';
        document.querySelectorAll('h1, h2').forEach(el => el.style.color = '#e94560');
    } else {
        document.body.style.background = 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)';
        document.querySelector('.container').style.background = 'white';
        document.querySelector('.container').style.color = '#333';
        document.querySelectorAll('h1, h2').forEach(el => el.style.color = '#667eea');
    }
}

function initializeEventListeners() {
    document.getElementById('save-btn').addEventListener('click', saveSettings);

    document.getElementById('reset-btn').addEventListener('click', resetSettings);

    document.querySelectorAll('input[name="theme"]').forEach(radio => {
        radio.addEventListener('change', (e) => {
            applyTheme(e.target.value);
        });
    });
}

function saveSettings() {
    const language = document.querySelector('input[name="language"]:checked').value;
    const theme = document.querySelector('input[name="theme"]:checked').value;
    const precision = document.getElementById('precision').value;

    localStorage.setItem('language', language);
    localStorage.setItem('theme', theme);
    localStorage.setItem('precision', precision);

    showMessage('Настройки успешно сохранены!', 'success');
}

function resetSettings() {
    localStorage.removeItem('language');
    localStorage.removeItem('theme');
    localStorage.removeItem('precision');

    loadSettings();

    showMessage('Настройки сброшены к значениям по умолчанию', 'success');
}

function showMessage(text, type) {
    const messageDiv = document.getElementById('message');
    messageDiv.textContent = text;
    messageDiv.className = `message ${type}`;

    setTimeout(() => {
        messageDiv.textContent = '';
        messageDiv.className = 'message';
    }, 3000);
}
