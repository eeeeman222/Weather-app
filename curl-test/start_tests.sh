#!/bin/bash

echo "---------------------------"

chmod +x *.sh

./success_test.sh && \
./err_test.sh && \
./missing_params_test.sh

if [ $? -eq 0 ]; then
    echo "---------------------------"
    echo "Все тесты прошли успешно"
else
    echo "---------------------------"
    echo "Некоторые тесты не прошли"
    exit 1
fi