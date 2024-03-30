# Тестовое задание Mediasoft

Этот проект является тестовым заданием от Mediasoft и представляет собой CRUD-сервис для учета товаров на складе.

## Используемые технологии
- Spring
- Hibernate
- Postgresql

# Инструкция по запуску Maven проекта

1. Откройте командную строку.

2. Перейдите в желаемую директорию используя команду `cd <путь до директории>`, внутри которой будет создана корневая директория `MediaSoftTestTask` проекта.

3. Склонируйте этот репозиторий используя команду `git clone https://github.com/tssvett/MediaSoftTestTask.git`

4. Перейдите в коревую директорию используя команду `cd MediaSoftTestTask`

5. Соберите проект командой `mvn clean package`, чтобы удалить предыдущие собранные файлы(если они были) и создать jar файл проекта в директории `target`.

6. Создайте базу данных для работы проекта.

7. Отредактируйте файл `application.properties`, который находится в папке `src/main/resources`, добавив информацию для подключения к вашей базе данных.

Пример:
```
spring.datasource.url=jdbc:mysql://localhost:3306/mydbname
spring.datasource.username=mydbusername
spring.datasource.password=mydbpassword
```

8. Теперь можно запустить проект, используя команду `java -jar target/MediaSoftTestTask-0.0.1-SNAPSHOT.jar`.

В итоге должен собраться и запуститься Maven проект для учета товаров на складе.
