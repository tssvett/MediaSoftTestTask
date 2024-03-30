# Тестовое задание Mediasoft

Этот проект является тестовым заданием от Mediasoft и представляет собой CRUD-сервис для учета товаров на складе.

## Используемые технологии
- Spring
- Hibernate
- Postgresql

# Инструкция по запуску Maven проекта

1. Откройте командную строку.
2. Откройте директорию, которая будет являться корневой директорией проекта используя команду `cd <путь до директории>`.
3. Склонируйте этот репозиторий используя команду `git clone https://github.com/tssvett/MediaSoftTestTask.git`

4. Соберите проект командой `mvn clean package`, чтобы удалить предыдущие собранные файлы(если они были) и создать jar файл проекта в директории `target`.

5. Создайте базу данных для работы проекта.

6. Отредактируйте файл `application.properties`, который находится в папке `src/main/resources`, добавив информацию для подключения к вашей базе данных.

Пример:
```
spring.datasource.url=jdbc:mysql://localhost:3306/mydbname
spring.datasource.username=mydbusername
spring.datasource.password=mydbpassword
```

7. Теперь можно запустить проект, используя команду `java -jar target/MediaSoftTestTask-0.0.1-SNAPSHOT.jar`.

В итоге должен собраться и запустится Maven проект для учета товаров на складе.
