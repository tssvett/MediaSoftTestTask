# Тестовое задание Mediasoft

Этот проект является тестовым заданием от Mediasoft и представляет собой CRUD-сервис для учета товаров на складе.

## Используемые технологии
- Spring
- Hibernate
- Postgresql

# Инструкция по запуску Maven проекта

1. Откройте командную строку.

2. Перейдите в корневую директорию вашего Maven проекта, используя команду `cd <путь до директории>`.

3. Перед сборкой проекта выполните команду `mvn clean package`, чтобы удалить предыдущие собранные файлы и создать jar файл проекта в директории `target`.

4. Создайте базу данных для работы проекта.

5. Отредактируйте файл `application.properties`, который находится в папке `src/main/resources`, добавив информацию для подключения к вашей базе данных.

Пример:
```
spring.datasource.url=jdbc:mysql://localhost:3306/mydbname
spring.datasource.username=mydbusername
spring.datasource.password=mydbpassword
```

6. Теперь можно запустить проект, используя команду `java -jar target/MediaSoftTestTask-0.0.1-SNAPSHOT.jar`.

В итоге должен собраться и запустится Maven проект для учета товаров на складе.
