#!/bin/bash

# Скрипт для запуска ShareParty проекта через Docker Compose

echo "🐳 Запуск ShareParty проекта через Docker Compose..."

# Проверяем, установлен ли Docker
if ! command -v docker &> /dev/null; then
    echo "❌ Docker не установлен. Установите Docker Desktop для macOS"
    exit 1
fi

# Проверяем, установлен ли Docker Compose
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose не установлен"
    exit 1
fi

echo "✅ Docker и Docker Compose доступны"

# Собираем JAR файл
echo "🔨 Сборка JAR файла..."
./gradlew build -x test

if [ $? -ne 0 ]; then
    echo "❌ Ошибка при сборке проекта"
    exit 1
fi

echo "✅ JAR файл собран"

# Останавливаем существующие контейнеры
echo "🛑 Остановка существующих контейнеров..."
docker-compose down

# Запускаем проект
echo "🚀 Запуск проекта через Docker Compose..."
docker-compose up --build -d

if [ $? -ne 0 ]; then
    echo "❌ Ошибка при запуске Docker Compose"
    exit 1
fi

echo "✅ Проект запущен!"
echo ""
echo "📱 Приложение доступно по адресу: http://localhost:9000"
echo "📚 Swagger UI: http://localhost:9000/swagger-ui/index.html"
echo "📋 API документация: http://localhost:9000/v3/api-docs"
echo ""
echo "🐳 Полезные команды:"
echo "  docker-compose logs -f app    # Просмотр логов приложения"
echo "  docker-compose logs -f postgres # Просмотр логов базы данных"
echo "  docker-compose down           # Остановка проекта"
echo "  docker-compose restart        # Перезапуск проекта"
echo ""
echo "⏳ Ожидание запуска приложения..."
sleep 10

# Проверяем статус
if curl -s http://localhost:9000/v3/api-docs > /dev/null; then
    echo "✅ Приложение успешно запущено!"
else
    echo "⚠️  Приложение еще запускается. Проверьте логи: docker-compose logs -f app"
fi 