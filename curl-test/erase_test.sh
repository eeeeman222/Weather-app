#!/bin/bash

echo "Запрос без параметров"
response=$(curl -s -w "%{http_code}" "http://localhost:8080/api/weather/temperature")
echo "Ответ: $response"

if [[ "$response" == *"400"* ]]; then
    echo "API вернул 400 Bad Request, правильно"
else
    echo "Ожидался статус 400, ошибка"
    exit 1
fi