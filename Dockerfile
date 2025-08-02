# Используем официальный образ OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы сборки
COPY build/libs/*.jar app.jar

# Открываем порт
EXPOSE 9000

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"] 