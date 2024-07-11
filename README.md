<h1 align="center">HotelLand</h1>
<h3 align="center">Содержание:</h3>
1. Краткое описание приложения<br/>
2. Защита аутентификации приложения<br/>
3. Валидность приходящих данных<br/>
4. Передача данных<br/>
5. Отправка и прослушивание событий<br/>
6. Миграция базы данных<br/>
7. Обработка исключений<br/>
8. Запускаем приложение<br/>
9. API взаимодействие с приложением<br/>
10. Использованные технологии и их версии

____
### I. Краткое описание приложения

Перед вами сервис бронирования отелей, в котором вы можете
найти такой функционал, как: создание и
изменение различных ключевых сущностей приложения, получение
данные по выбранной сущности, бронирование выбранного
номера в желаемом отеле, получение статистики по
регистрации и бронировании, ограничения функционала по ролям
и базовая защита аутентификации приложений.

А теперь более подробно...

==================

### II. Защита аутентификации приложения
   Приложение защищено базовой защитой аутентификации пользователя по имени и паролю. 
   Пользователю доступны два уровня доступа либо USER либо ADMIN. 
   Настройка осуществляется при помощи встроенных инструментов сервиса Spring Security в классе конфигурации.
   Поэтому перед тестированием и использованием функционала, целесообразно 
   создать пользователя, в противном случае доступ к остальным частям функционала будет ограничен.

### III. Валидность приходящих данных
   Валидность входящих данных осуществляется при помощи штатного валидатора Spring.
   Как пример вы можете заметить, что нельзя создать двух одинаковых пользователей имеющих одинаковые имя или пароль.
   Так же вам встретится ограничение по оценке отеля от 1 до 5. 
   В эндпоинтах где нужна пагинация так же возникнет ограничение доступа при отсутствии базовых переменных пагинации. 
   Встречаются и другие ограничения валидации.

### IV. Передача данных
   Для удобной, поддерживаемой и понятной передачи данных между слоями приложения используется ряд преобразующих интерфейсов 
   из которых автоматически генерируются объекты обеспечивающие необходимый функционал для преобразования. 
   Автоматическая генерация используется при помощи подключенной библиотеки Mapstruct.
   Преобразования используются как из входящих данных так и в финальные данные отдаваемые пользователю.

### V. Отправка и прослушивание событий
   В приложении успешно подключен брокер сообщений kafka. Он нам нужен, что бы во время 
   регистрации пользователя или бронирования комнаты мы слушали данные события и записывали 
   их в нереляционную базу данных mongo. Ведь вы можете в последующем 
   выгрузить статистику вышеуказанных событий получив данные из БД.

### VI. Миграция базы данных
   Так как встроенный в Spring ORM Hibernate в определенный момент времени может неисправно обновлять или создавать данные в БД, 
   в приложении предусмотрена удобная миграция БД при помощи библиотеки liquibase. 

### VII. Обработка исключений
   Что бы пользователю были отправлены понятные данные в случае возникновения ошибок (неправильные введенные данные, 
   совпадения данных, ошибки сервера и т.п.) предусмотрен контроллер по отлову ошибок [RestResponseEntityExceptionHandler.java], 
   который предоставит пользователю преобразованный понятный объект-ответ.
   
### VIII. Запускаем приложение
1. Клонируем проект в среду разработки
2. Убедитесь что Java 21, docker compose 3.x
3. Запустить приложение можно терминальной командой "docker composer up"
4. При успешном запуске приложения, можно пользоваться подключенными API

### IX. API взаимодействие с приложением

1. **Работа с отелями**
   * Используемый репозиторий: JPA [HotelRepository.java]
   * Используемые сервисы: HotelService [HotelService.java]
   * Используемые Роли/Полномочия: ADMIN, AUTHENTICATED USER (пользователь должен быть авторизован)
      
   Доступные эндпоинты: 

   1.1 Найти и получить все доступные в БД отели:
   * URL: http://localhost:8080/hotelland/hotel/
   * Метод: GET
   * Параметры запроса для пагинации: Integer pageSize, pageNumber
   * Доступно только для: ADMIN
   * Метод сервиса: findAll()
   
   1.2 Найти и получить отель по уникально идентификатору
   * URL: http://localhost:8080/hotelland/hotel/{identificator}
   * Метод: GET
   * Параметр пути: Long hotelId (индентификатор отеля который нужно найти)
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: findById()
   
   1.3 Создание отеля в БД
   * URL: http://localhost:8080/hotelland/hotel
   * Метод: POST
   * Параметры тела запроса: String name, headline, city, address; Long distanceToCenter
   * Доступно только для: ADMIN
   * Метод сервиса: create(@body)
   
   1.4 Обновление описания отеля по уникальному идентификатору
   * URL: http://localhost:8080/hotelland/hotel/{identificator}
   * Метод: PUT
   * Параметры тела запроса: String name, headline, city, address; Long distanceToCenter
   * Параметр пути: Long hotelId (индентификатор отеля который нужно обновить)
   * Доступно только для: ADMIN
   * Метод сервиса: update(@params, @body)
   
   1.5 Удаление упоминание отеля по уникальному идентификатору из БД
   * URL: http://localhost:8080/hotelland/hotel/{identificator}
   * Метод: DELETE
   * Параметр пути: Long hotelId (индентификатор отеля который нужно удалить)
   * Доступно только для: ADMIN
   * Метод сервиса: addHotelRating(@params)
   
   1.6 Поставить оценку отелю
   * URL: http://localhost:8080/hotelland/hotel/rating/{identificator}
   * Метод: PATCH
   * Параметры запроса: Long newMark - оценка
   * Параметр пути: Long hotelId (индентификатор отеля который нужно оценить)
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: addHotelRating(@params)

   1.7 Получить все отели по определенному фильтру или полю
   * URL: http://localhost:8080/hotelland/hotel/filter-by
   * Метод: GET
   * Параметры по которым нужно получить от фильтрованный список: Long id, String name, headline, city, address;
     Long distanceToCenter, numberRatings; Double hotelRating, Integer pageSize, pageNumber;
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: filterBy(@params)

2. **Работа с комнатами отеля**
   * Используемый репозиторий: JPA [RoomRepository.java], [ReservationRepository.java]
   * Используемые сервисы: RoomService [RoomService.java]
   * Используемые Роли/Полномочия: ADMIN, AUTHENTICATED USER (пользователь должен быть авторизован)

   Доступные эндпоинты:

   2.1 Найти и получить все сущности комнаты в БД:
   * URL: http://localhost:8080/hotelland/room/
   * Метод: GET
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: findAll()

   2.2 Найти и получить сущность комнату по уникальному идентификатору из БД
   * URL: http://localhost:8080/hotelland/room/{identificator}
   * Метод: GET
   * Параметр пути: Long roomId
   * Доступно только для: AUTHENTICATED USER 
   * Метод сервиса: findByIdForResponse()

   2.3 Создание сущности комнаты в базе данных
   * URL: http://localhost:8080/hotelland/room
   * Метод: POST
   * Параметры тела запроса: String name, description; Long number, price, maxPeople, hotelId
   * Доступно только для: ADMIN
   * Метод сервиса: create(@body)

   2.4 Обновление сущности комнаты по уникальному идентификатору
   * URL: http://localhost:8080/hotelland/room/{identificator}
   * Метод: PUT
   * Параметры тела запроса: String name, description; Long number, price, maxPeople, hotelId
   * Параметр пути: Long roomId
   * Доступно только для: ADMIN
   * Метод сервиса: update(@param, @body)

   2.5 Удаление сущности комнаты по уникальному идентификатору из БД
   * URL: http://localhost:8080/hotelland/room/{identificator}
   * Метод: DELETE
   * Параметр пути: Long roomId
   * Доступно только для: ADMIN
   * Метод сервиса: delete(@param)

   2.6 Получить отфильтрованный список комнат в базе данных по определенному парамметру(-ам)
   * URL: http://localhost:8080/hotelland/room/filter-by
   * Метод: GET
   * Параметры по которым нужно получить от фильтрованный список: Long id, String description;
   Long hotelId maxPrice, minPrice, maxPeople; LocaleDate arrival, departure;
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: filterBy(@params)

3. **Работа с посетителями отеля**
   * Используемый репозиторий: JPA [VisitorRepository.java]
   * Используемые сервисы: VisitorService [VisitorService.java]
   * Используемые Роли/Полномочия: AUTHENTICATED USER (пользователь должен быть авторизован)

   Доступные эндпоинты:

   3.1 Найти всех посетителей в БД:
   * URL: http://localhost:8080/hotelland/visitor
   * Метод: GET
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: findAll()

   3.2 Найти и получить сущность посетителя по уникальному идентификатору из БД
   * URL: http://localhost:8080/hotelland/visitor/{identificator}
   * Метод: GET
   * Параметр пути: Long visitorId
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: findById(@params) 

   3.3 Создать(зарегистрировать) посетителя в отеле и БД
   * URL: http://localhost:8080/hotelland/visitor
   * Метод: POST
   * Параметры тела запроса: String name, password, email
   * Параметры запроса: RoleType type (ADMIN, USER) - здесь при регистрации выбирается какая будет роль или полномочия доступа
   * Доступно только для: ALL USERS - для всех заходящих на сайт
   * Метод сервиса: create(@body,@params)

   3.4 Обновление сущности комнаты по уникальному идентификатору
   * URL: http://localhost:8080/hotelland/visitor/{identificator}
   * Метод: PUT
   * Параметры тела запроса: String name, password, email
   * Параметры пути запроса: Long visitorId
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: update(@body,@params)

   3.5 Удаление сущности пользователя по уникальному идентификатору из БД
   * URL: http://localhost:8080/hotelland/visitor/{identificator}
   * Метод: DELETE
   * Параметры пути запроса: Long visitorId
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: delete(@params)

4. **Бронирование комнаты пользователем**
   * Используемый репозиторий: JPA [ReservationRepository.java]
   * Используемые сервисы: ReservationService [ReservationService.java], [RoomService.java], [VisitorService.java]
   * Используемые Роли/Полномочия: ADMIN, AUTHENTICATED USER (пользователь должен быть авторизован)

   Доступные эндпоинты:

   4.1 Получить все брони которые осуществлялись за все время из БД:
   * URL: http://localhost:8080/hotelland/reservation
   * Метод: GET
   * Доступно только для: ADMIN
   * Метод сервиса: findAll()

   4.2 Забронировать комнату и создать запись в БД
   * URL: http://localhost:8080/hotelland/reservation
   * Метод: POST
   * Параметры тела запроса: String arrival, departure; Long visitorId, roomId
   * Доступно только для: AUTHENTICATED USER
   * Метод сервиса: findAll()

5. **Выгрузить статистику по зарегистрированным пользователям и осуществленным броням комнат**
   * Используемый репозиторий:: MONGO [RegistrationVisitorRepository.java], [ReservationRecordRepository.java]
   * Используемые сервисы:  RegistrationVisitorService [RegistrationVisitorService.java],
     ReservationRecordService [ReservationRecordService.java], ReservationService [ReservationService.java],
     StatisticCSVService [StatisticCSVService.java]
   * Используемые Роли/Полномочия: ADMIN

     Доступные эндпоинты:

   5.1 Получить статистику, статистика будет выгружена и передана пользователю в виде файла в формате csv:
   * URL: http://localhost:8080/hotelland/statistic
   * Метод: GET
   * Доступно только для: ADMIN
   * Метод сервиса: getStatisticsInCSV()
   
   
==========================

### X. Использованные технологии и их версии

* Framework Spring - 6 v.
* Spring boot - 3.3.1 v.
* Java - 21 v.
* Kafka - 3.3.1 v.
* Postgresql - 42.7.3
* Mongo - 3.3.1
* Liquibase - 4.28.0
* Mapstruct - 1.5.3.Final
* Docker - 3 v.

==========================
