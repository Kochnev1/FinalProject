# Дипломный проект профессии «Тестировщик ПО»

# Приложение "Путешествие дня"

Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

Приложение — это веб-сервис, который предлагает купить тур по определённой цене двумя способами:

Обычная оплата по дебетовой карте.
Уникальная технология: выдача кредита по данным банковской карты.


## Начало работы

- Сделать `git clone` данного [репозитория](https://github.com/Kochnev1/FinalProject)
- Открыть проект в IntelliJ IDEA
- Запустить приложение Docker Desktop

  ### Установка и запуск
1) Запускаем контейнеры из файла **docker-compose.yml** командой в терминале:

```
docker-compose up
```

и проверяем, что контейнеры запустились в прилложении Docker Desktop:

![Снимок экрана 2023-11-29 205407](https://github.com/Kochnev1/FinalProject/assets/134865182/21fd4b14-ce9f-4c47-86fc-aa54d879acc9)

2) Запускаем SUT командой в терминале:

- для MySQL:

```
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar aqa-shop.jar
```

- для PostgreSQL:

```
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar aqa-shop.jar
```

3) Запускаем авто-тесты командой в терминале:

```
./gradlew clean test
```
4) Генерируем отчёт по итогам тестирования с помощью **Allure**. Отчёт автоматически откроется в браузере с помощью команды в терминале:

```
./gradlew allureServe
```

По окончанию работы можно отключить **allureServe** в терминале сочетанием клавиш _CTRL + C_ и
подтверждаем действие в терминале вводом _Y_.

## Документация

[План тестирования](https://github.com/Kochnev1/FinalProject/blob/main/Plan.md)

[Отчет о тестировании](https://github.com/Kochnev1/FinalProject/blob/main/Report.md)

[Итоги работы](https://github.com/Kochnev1/FinalProject/blob/main/Summary.md)










