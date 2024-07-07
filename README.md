<h1 align="center">HotelLand</h1>
<h3 align="center">Contents:</h3>
1. Resume<br/>
2. Basic business functions<br/>
3. Minor functions of logic<br/>
4. Technologies used<br/>
5. Launching the application

____
### I. RESUME

A hotel booking service in which you can 
find such functionality as: creating and 
changing various application entities, obtaining 
data for a selected entity, booking a selected 
room for a desired hotel, obtaining statistics on 
registration and reservations, restrictions by roles 
and basic protection of application authentication.

And now in more detail...

==================

### II. BASIC BUSINESS FUNCTIONS

1. **HOTELS**
   * Repository: JPA
   * Using services: HotelService
   * Roles: ADMIN, AUTHENTICATED USER
   
   Endpoints api: 

   1.1 Find all hotel from bd:
   * url: http://localhost:8080/hotelland/hotel/
   * method: GET
   * pagination requests params: Integer pageSize, pageNumber
   * role: ADMIN
   
   1.2 Find hotel by unique identificator
   * url: http://localhost:8080/hotelland/hotel/{identificator}
   * method: GET
   * path requests params: Long hotelId
   * role: AUTHENTICATED USER
   
   1.3 Create hotel in BD
   * url: http://localhost:8080/hotelland/hotel
   * method: POST
   * body requests: String name, headline, city, address; Long distanceToCenter
   * role: ADMIN
   
   1.4 Update hotel by unique identificator
   * url: http://localhost:8080/hotelland/hotel/{identificator}
   * method: PUT
   * body requests: String name, headline, city, address; Long distanceToCenter
   * path requests params: Long hotelId
   * role: ADMIN
   
   1.5 Delete hotel by unique identificator from BD
   * url: http://localhost:8080/hotelland/hotel/{identificator}
   * method: DELETE
   * path requests params: Long hotelId
   * role: ADMIN
   
   1.6 Filter hotels by params
   * url: http://localhost:8080/hotelland/hotel/filter-by
   * method: GET
   * filter requests params (one or more): Long id, String name, headline, city, address; 
   Long distanceToCenter, numberRatings; Double hotelRating;
   * role: AUTHENTICATED USER

2. **ROOMS**
   * Repository: JPA
   * Using services: RoomService
   * Roles: ADMIN, AUTHENTICATED USER

   Endpoints api:

   2.1 Find all rooms from bd:
   * url: http://localhost:8080/hotelland/room/
   * method: GET
   * role: AUTHENTICATED USER

   2.2 Find room by unique identificator
   * url: http://localhost:8080/hotelland/room/{identificator}
   * path requests params: Long roomId
   * role: AUTHENTICATED USER

   2.3 Create room by hotel in BD
   * url: http://localhost:8080/hotelland/room
   * body requests: String name, description; Long number, price, maxPeople, hotelId
   * role: ADMIN

   2.4 Update room by unique identificator
   * url: http://localhost:8080/hotelland/room/{identificator}
   * body requests: String name, description; Long number, price, maxPeople, hotelId
   * path requests params: Long roomId
   * role: ADMIN

   2.5 Delete room by unique identificator from BD
   * url: http://localhost:8080/hotelland/room/{identificator}
   * path requests params: Long roomId
   * role: ADMIN

   2.6 Filter hotel by params
   * url: http://localhost:8080/hotelland/room/filter-by
   * method: GET
   * filter requests params (one or more): Long id, String description; Long hotelId maxPrice, minPrice, maxPeople;
     LocaleDate arrival, departure;
   * role: AUTHENTICATED USER

3. **VISITORS**
   * Repository: JPA
   * Using services: VisitorService
   * Roles: AUTHENTICATED USER

   Endpoints api:

   3.1 Find all visitors from bd:
   * url: http://localhost:8080/hotelland/visitor
   * method: GET

   3.2 Find visitor by unique identificator
   * url: http://localhost:8080/hotelland/visitor/{identificator}
   * method: GET
   * path requests params: Long visitorId

   3.3 Create (registration) visitor in BD
   * url: http://localhost:8080/hotelland/visitor
   * method: POST
   * body requests: String name, password, email
   * request param: RoleType type (ADMIN, USER)

   3.4 Update visitor by unique identificator
   * url: http://localhost:8080/hotelland/visitor/{identificator}
   * method: PUT
   * body requests: String name, password, email
   * path requests params: Long visitorId

   3.5 Delete visitor by unique identificator from BD
   * url: http://localhost:8080/hotelland/visitor/{identificator}
   * method: DELETE
   * path requests params: Long visitorId

4. **RESERVATION ROOM BY HOTEL**
   * Repository: JPA
   * Using services: VisitorService, RoomService, ReservationService
   * Roles: ADMIN, AUTHENTICATED USER

   Endpoints api:

   4.1 Find reservations from bd:
   * url: http://localhost:8080/hotelland/reservation
   * method: GET
   * role: ADMIN

   4.3 Create reservation by room and hotel in BD
   * url: http://localhost:8080/hotelland/reservation
   * method: POST
   * body requests: String arrival, departure; Long visitorId, roomId
   * role: AUTHENTICATED USER

5. **OBTAINING STATISTICS ON REGISTRATION AND BOOKING**
   * Repository: MONGO
   * Using services: RegistrationVisitor, ReservationRecord, StatisticCSV
   * Roles: ADMIN

     Endpoints api:

   5.1 Get statistic:
   * url: http://localhost:8080/hotelland/statistic
   * method: GET
   * role: ADMIN

     ==========================

### III. MINOR FUNCTIONS OF LOGIC

   1. The mapstruct lib is used to convert objects into transfers and back
   2. Kafka is used to send and listen to events when creating (registering) a visitor and booking a room
   3. To protect the application and unauthorized access, basic protection is used using the spring security lib.
   4. to obtain statistics, a mongo 
repository is used in which 
data is saved as events when 
listening using kafka, the result of which is a generated csv file
   5. For the convenience of updating and creating a database, database migration using liquidbase is provided
   6. The application provides validation of incoming data

==========================

### IV. TECHNOLOGIES USED

The application used technologies and versions such as:

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

### V. LAUNCHING THE APPLICATION

To run the application, Docker must be pre-installed, 
and the launch is done using the `docker compose up console` command.

==========================