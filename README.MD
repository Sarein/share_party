# ShareParty

Веб-приложение для управления вечеринками и расходами на них.

## Описание

ShareParty - это Spring Boot приложение на Kotlin, которое позволяет:
- Создавать вечеринки
- Приглашать друзей
- Добавлять и управлять расходами
- Отслеживать общие траты

## Технологии

- **Backend**: Spring Boot 3.4.4, Kotlin
- **База данных**: PostgreSQL 14
- **Миграции**: Liquibase
- **API документация**: Swagger UI
- **Сборка**: Gradle

## Требования

- Java 17 или выше
- PostgreSQL 14
- Gradle (включен в проект)

## Быстрый запуск

### 🐳 **Рекомендуемый способ: Docker Compose**

Для продакшена и большинства случаев использования:

```bash
./docker-start.sh
```

**Преимущества:**
- ✅ Изолированная среда
- ✅ Автоматическая настройка базы данных
- ✅ Портативность
- ✅ Простота развертывания

**Требования**: Docker Desktop для macOS

### 🔧 **Альтернативный способ: Локальный запуск**

### 🎯 **Когда какой способ использовать?**

| Сценарий | Рекомендуемый способ | Причина |
|----------|---------------------|---------|
| **Продакшен** | Docker Compose | Изоляция, портативность |
| **Тестирование** | Docker Compose | Воспроизводимая среда |
| **Разработка** | Локальный запуск | Быстрота, отладка |
| **CI/CD** | Docker Compose | Стандартизация |
| **Демонстрация** | Docker Compose | Простота развертывания |

### 🔧 **Альтернативный способ: Локальный запуск**

Для разработки и отладки:

```bash
./start.sh
```

**Преимущества:**
- ✅ Быстрее для разработки
- ✅ Легче отладка
- ✅ Прямой доступ к файлам
- ✅ Меньше потребление ресурсов

### 📋 **Ручные команды**

#### Docker Compose:
```bash
# Сборка JAR файла
./gradlew build -x test

# Запуск через Docker Compose
docker-compose up --build -d

# Просмотр логов
docker-compose logs -f app
```

#### Локальный запуск:

#### Автоматический запуск:
```bash
./start.sh
```

Этот скрипт автоматически:
- Проверит и настроит Java 17
- Запустит PostgreSQL
- Создаст базу данных и пользователя
- Запустит приложение

#### Ручной запуск:

#### Шаг 1: Настройка Java 17

```bash
export JAVA_HOME=/Users/a.s.smirnov/Library/Java/JavaVirtualMachines/jbr-17.0.8/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
```

#### Шаг 2: Запуск PostgreSQL

```bash
brew services start postgresql@14
```

#### Шаг 3: Создание базы данных

```bash
createdb shareparty
createuser -s postgres
```

#### Шаг 4: Запуск приложения

```bash
./gradlew bootRun
```

## Доступные endpoints

После запуска приложение будет доступно по адресу: **http://localhost:9000**

### Основные URL:

- **API документация**: http://localhost:9000/v3/api-docs
- **Swagger UI**: http://localhost:9000/swagger-ui/index.html

### API Endpoints:

- `POST /api/v1/party` - Создать вечеринку
- `GET /api/v1/parties` - Получить список вечеринок
- `POST /api/v1/party/{partyId}/friend` - Пригласить друга
- `DELETE /api/v1/party/{partyId}/friend` - Удалить друга
- `POST /api/v1/party/{partyId}/expense` - Добавить расход
- `DELETE /api/v1/party/{partyId}/expense` - Удалить расход
- `POST /api/v1/friend` - Зарегистрировать друга

## Примеры использования

### Создание вечеринки:
```bash
curl -X POST http://localhost:9000/api/v1/party \
  -H "Content-Type: application/json" \
  -d '{"name": "Моя вечеринка"}'
```

### Получение списка вечеринок:
```bash
curl http://localhost:9000/api/v1/parties
```

## Структура проекта

```
src/main/kotlin/com/partymakers/shareparty/
├── application/          # Конфигурация приложения
├── friends/             # Модуль управления друзьями
│   ├── data/           # Слой данных
│   ├── domain/         # Бизнес-логика
│   └── presentation/   # Контроллеры
├── party/              # Модуль управления вечеринками
│   ├── data/           # Слой данных
│   ├── domain/         # Бизнес-логика
│   └── presentation/   # Контроллеры
└── SharePartyApplication.kt
```

## Устранение неполадок

### 🐳 **Docker проблемы**

#### Ошибка "Cannot connect to the Docker daemon"
```bash
# Запустите Docker Desktop
open -a Docker

# Или через командную строку
sudo launchctl start com.docker.docker
```

#### Ошибка "port already in use"
```bash
# Остановите существующие контейнеры
docker-compose down

# Или найдите и остановите процесс на порту 9000
lsof -ti:9000 | xargs kill -9
```

#### Ошибка "image not found"
```bash
# Пересоберите образ
docker-compose build --no-cache
```

### 🔧 **Локальные проблемы**

#### Ошибка "role postgres does not exist"
```bash
createuser -s postgres
```

#### Ошибка "type long does not exist"
База данных была создана с неправильными типами. Удалите и создайте заново:
```bash
dropdb shareparty
createdb shareparty
```

#### Ошибка "Java version"
Убедитесь, что используется Java 17:
```bash
java -version
```

## Управление Docker контейнерами

### Полезные команды:

```bash
# Запуск проекта
docker-compose up -d

# Остановка проекта
docker-compose down

# Перезапуск проекта
docker-compose restart

# Просмотр логов приложения
docker-compose logs -f app

# Просмотр логов базы данных
docker-compose logs -f postgres

# Просмотр статуса контейнеров
docker-compose ps

# Очистка всех данных (включая базу данных)
docker-compose down -v
```

### Остановка приложения

#### Для Docker версии:
```bash
docker-compose down
```

#### Для локальной версии:
Для остановки приложения нажмите `Ctrl+C` в терминале, где оно запущено.

Для остановки PostgreSQL:
```bash
brew services stop postgresql@14
```