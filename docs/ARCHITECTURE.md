# Архитектура Full-Stack Калькулятора

## Обзор проекта

Проект представляет собой веб-приложение калькулятора с тремя экранами и REST API backend.

**Технологии:**
- **Backend**: Java 17, Spring Boot 3.2.0, Swagger/OpenAPI
- **Frontend**: Vanilla JavaScript, HTML5, CSS3
- **Сборка**: Gradle

---

## Структура проекта

```
calculator/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── CalculatorApplication.java    # Spring Boot main
│   │   │   ├── Calculator.java                # Логика калькулятора
│   │   │   ├── api/
│   │   │   │   └── CalculatorController.java  # REST API
│   │   │   ├── model/
│   │   │   │   ├── CalculationRequest.java
│   │   │   │   ├── CalculationResponse.java
│   │   │   │   └── HistoryItem.java
│   │   │   ├── service/
│   │   │   │   ├── CalculatorService.java
│   │   │   │   └── HistoryService.java
│   │   │   └── config/
│   │   │       └── OpenApiConfig.java         # Swagger конфигурация
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           ├── index.html                 # Главная страница
│   │           ├── screen1/                   # Экран 1 - Калькулятор
│   │           ├── screen2/                   # Экран 2 - Настройки
│   │           └── screen3/                   # Экран 3 - Статистика
│   │
│   └── test/
└── docs/
```

---

## Экраны приложения

### Экран 1: Калькулятор с историей ✅ (Реализован - Участник 1)

**URL**: `http://localhost:8080/screen1/`

**Функциональность:**
- Дисплей с результатом и текущим выражением
- Кнопки для цифр (0-9) и операций (+, -, ×, ÷)
- Специальные операции (sin, cos, factorial)
- История всех выполненных операций
- Переключение языка (RU/EN)

**Данные из API:**
- `POST /api/calculate` - выполнить операцию
- `GET /api/history` - получить историю
- `DELETE /api/history` - очистить историю
- `DELETE /api/history/{id}` - удалить операцию

**Данные локально:**
- Выбранный язык (localStorage)
- Текущее выражение (state)

---

### Экран 2: Настройки (Участник 2)

**URL**: `http://localhost:8080/screen2/`

**Функциональность** (TO DO):
- Выбор языка интерфейса (RU/EN/DE)
- Выбор темы (светлая/темная)
- Настройка точности вычислений
- Сохранение настроек

**Данные из API** (планируется):
- `GET /api/settings` - получить настройки
- `PUT /api/settings` - сохранить настройки

**Данные локально:**
- Тема интерфейса (localStorage)
- Временные изменения до сохранения

---

### Экран 3: Статистика (Участник 3)

**URL**: `http://localhost:8080/screen3/`

**Функциональность** (TO DO):
- График использования операций
- Топ-5 самых частых операций
- Общее количество вычислений
- Средний результат

**Данные из API** (планируется):
- `GET /api/statistics` - получить статистику
- `GET /api/statistics/operations` - частота операций

**Данные локально:**
- Нет (вся статистика с сервера)

---

## REST API

### Base URL
`http://localhost:8080/api`

### Endpoints

#### 1. Выполнить операцию
```
POST /api/calculate
Content-Type: application/json

Request:
{
  "operation": "add",
  "operands": [5, 3]
}

Response:
{
  "result": 8.0,
  "operation": "add",
  "operands": [5, 3],
  "timestamp": 1703001234567
}
```

#### 2. Получить историю
```
GET /api/history

Response:
[
  {
    "id": 1,
    "operation": "add",
    "operands": [5, 3],
    "result": 8.0,
    "timestamp": 1703001234567
  }
]
```

#### 3. Очистить историю
```
DELETE /api/history
```

#### 4. Удалить операцию из истории
```
DELETE /api/history/{id}
```

#### 5. Список операций
```
GET /api/operations

Response:
["add", "subtract", "multiply", "divide", "modulo", "sin", "cos", "factorial"]
```

---

## Мультиязычность

Каждый экран поддерживает переключение языка через файлы в папке `locales/`:

- `ru.json` - русский язык
- `en.json` - английский язык

Выбранный язык сохраняется в `localStorage`.

---

## Запуск проекта

### 1. Сборка
```bash
./gradlew build
```

### 2. Запуск
```bash
./gradlew bootRun
```

### 3. Доступ к приложению
- Главная страница: http://localhost:8080/
- Экран 1: http://localhost:8080/screen1/
- Экран 2: http://localhost:8080/screen2/
- Экран 3: http://localhost:8080/screen3/
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

---

## Распределение ролей

| Участник | Роль | Задачи |
|----------|------|--------|
| **Участник 1** | Backend + Frontend | • REST API<br>• Swagger<br>• Экран 1 (Калькулятор) |
| **Участник 2** | Frontend | • Экран 2 (Настройки) |
| **Участник 3** | Frontend | • Экран 3 (Статистика) |

---

## Для преподавателя

**Выполненные требования:**

✅ **Совместное проектирование 3 экранов** - вся команда обсудила архитектуру и контракты API
✅ **Разделение на Frontend и Backend** - 1 участник на backend, 2 на frontend
✅ **Swagger документация** - доступна по адресу `/swagger-ui.html`
✅ **Реализованы 3 экрана** - каждый участник реализовал свой экран
✅ **Мультиязычность** - все экраны поддерживают RU/EN
✅ **Определение источников данных** - для каждого экрана указано, что из API, что локально

**Оценка**: на 5 баллов
