#!/bin/bash

echo "Тест 1: Корректный запрос температуры для Москвы"
response=$(curl -s "http://localhost:8080/api/weather/temperature?latitude=55.7558&longitude=37.6173")
echo "Ответ: $response"

if echo "$response" | grep -q '"temperature"'; then
    echo "API вернул температуру, правильно"
else
    echo "Некорректный ответ, ошибка"
    exit 1
fi