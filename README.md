### РИС-22-1б
### Участники:
- Барыбин Кирилл
- Вахрушева Арина
- Юсупов Тимур

### Выполнено на оценку 5, пункты из CI:
## Team Project — ЛР 1, ЛР 2

### 1. Организован Git Flow
#### Основные ветки: `main`, `develop`
#### Рабочие ветки: `feature/*`, `bugfix/*`, `refactor/*`, `epic/*`
#### CI запускается на всех типах веток  
- [Триггеры pipeline](.github/workflows/ci.yml#L4-L10)

### 2. Stage 1 
#### Проверка стиля кода: 
- [checkstyle job](.github/workflows/ci.yml#L11-L34)
#### Проверка размера PR (300/150/50 строк): 
- [ci.yml](.github/workflows/ci.yml#L36-L48)
- [check_pr.py](scripts/check_pr.py#L6-L41)
#### Вывод команды: 
- [ci.yml](.github/workflows/ci.yml#L50-L59)
- [check_pr.py](scripts/check_pr.py#L43-L59)
#### Обновление описания Epic PR: 
- [ci.yml](.github/workflows/ci.yml#L61-L72) 
- [check_pr.py](scripts/check_pr.py#L61-L121)

### Stage 2
#### Сборка проекта и сохранение артефакта: 
- [build job](.github/workflows/ci.yml#L74-L91)

### Stage 3 
#### Запуск тестов: 
- [test job](.github/workflows/ci.yml#L93-L104)   

### Stage 4 
#### Публикация релиза: 
- [publish job](.github/workflows/ci.yml#L106-L130)



## ЛР 2

### Канбан-доска проекта:
- [Project «Team Project»](https://github.com/users/Arishka-a/projects/3)  

### Список всех задач:
- [Задачи](https://github.com/Arishka-a/team_project/issues)

#### Ссылки на отдельные задачи:
- [Задача на добавление новых операций](https://github.com/Arishka-a/team_project/issues/25)
- [Задача на исправление операции синуса](https://github.com/Arishka-a/team_project/issues/26)
- [Задача на проверку новых операций](https://github.com/Arishka-a/team_project/issues/27)


  
