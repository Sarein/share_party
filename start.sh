#!/bin/bash

# Скрипт для запуска ShareParty проекта

echo "🚀 Запуск ShareParty проекта..."

# Проверяем, установлена ли Java 17
if ! command -v java &> /dev/null; then
    echo "❌ Java не установлена"
    exit 1
fi

# Устанавливаем Java 17
export JAVA_HOME=/Users/a.s.smirnov/Library/Java/JavaVirtualMachines/jbr-17.0.8/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

echo "✅ Java 17 настроена"

# Проверяем, запущен ли PostgreSQL
if ! pg_isready -q; then
    echo "🔄 Запуск PostgreSQL..."
    brew services start postgresql@14
    sleep 3
fi

echo "✅ PostgreSQL запущен"

# Проверяем, существует ли база данных
if ! psql -lqt | cut -d \| -f 1 | grep -qw shareparty; then
    echo "🔄 Создание базы данных shareparty..."
    createdb shareparty
fi

echo "✅ База данных готова"

# Проверяем, существует ли пользователь postgres
if ! psql -t -c '\du' | cut -d \| -f 1 | grep -qw postgres; then
    echo "🔄 Создание пользователя postgres..."
    createuser -s postgres
fi

echo "✅ Пользователь postgres готов"

# Запускаем приложение
echo "🚀 Запуск Spring Boot приложения..."
echo "📱 Приложение будет доступно по адресу: http://localhost:9000"
echo "📚 Swagger UI: http://localhost:9000/swagger-ui/index.html"
echo "📋 API документация: http://localhost:9000/v3/api-docs"
echo ""
echo "Для остановки приложения нажмите Ctrl+C"
echo ""

./gradlew bootRun 