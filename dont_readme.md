my english is very capital of Great Britain, consequently:
# Awesome app
в моем представлении это действительно великолепное приложение для проверки возможности создания "около-энтерпрайз" приложения, в котором используется не привычный большинству Spring-boot, а более легковесный фрейворк micronaut.

### описание происходящего внутри:
Ленивая реализация паттерна "message outbox":
 - получение сообщения по шине
 - логирование его в "сыром" виде в бд
 - отправка далее по шине 
  
Так же пытаемся в ходе неимоверной любви к Spring добавить его стартеры, который нам ~~достались по наследству~~ нужен в работе

### стек проекта:
    - micronaut 4.4.2
    - posgres 10.2 (merge'ть не буду, обещаю)
    - kafka - zookeeper в комплекте, kafka-ui что бы мониторить кластер глазами из коробки
### ожидается:

    - swagger - что бы лениво тыкать апишки в браузере

### build
###### старт обвязки: docker-compose up --build 
###### старт приклада: локально