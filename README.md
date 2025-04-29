# NoteS

Приложение для создания заметок. Существует два бэкенда на java spring boot и C# asp.NET.
Для фронтенда клиент на Angular. Две базы данных: PostgreSQL для хранения метаданных, ElasticSearch для хранения содержания. Брокер сообщений 
Kafka для транзакционной целостности (Event Sourcing + CQRS + Saga). Для миграций используется Liquibase. Для генерации документации Doxygen.
Для авторизации и аутентификации поддерживается OAuth2 и сервер авторизации Keycloak. Все это инкапсулированию в Docker контейнеры. 


## Startup

Предварительно собрать через `npm` фронтенд.

```zsh
cd Client
npm install
```

Запустить окружение:

```zsh
docker compose up
```

Запустить бэкенд на java или C#:

```zsh
cd Backend/csharp
dotnet run
```

```zsh
cd Backend/java
mvn package
docker compose up
```

## Общие мысли по поводу проекта

[Тык](wiki/problems.md)

## Десигн в фигме

[Тык](https://www.figma.com/design/fnDv5W0H5QJdaQoFunDDA7/NoteS?node-id=0-1&t=RCA0qge5pxpyP6s4-1)

## Документация

[Тык](https://anywaythanks.github.io/NoteS/)