#!/bin/bash

echo "Запрос с невалидными координатами"
response=$(curl -s "http://localhost:8080/api/weather/temperature?latitude=999&longitude=999")
echo "Ответ: $response"

if echo "$response" | grep -q '"error"'; then
    echo "API вернул ошибку для невалидных координат, правильно"
else
    echo "Ожидалась ошибка сервиса, но ее не произошло."
    exit 1
fi