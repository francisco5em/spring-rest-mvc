# spring-rest-mvc
## English
REST json api using Spring Framework 6 of a database containing a beer products and customers.

### Tools used
- Spring Framework 6
    - Spring Boot 3.1.5
- Maven
- Git 
- Java Version 17
- Thymeleaf
- Lombok
- Hibernate
- Flyway
- Rest
- MySQL
- Mapstruct
- OpenCSV


### Maven Dependencies
- Spring Data JPA
- Spring Web
- H2 DataBase
- lombok
- spring-boot-devtools
- mysql-connector-java
- mapstruct
- opencsv

### Detailed project description
The project uses Spring Data with MySQL database to save the example data wtih an url of: "http://localhost:8081/api/v1/" for the API.

### Security
Security is done through the use of the Oauth2 Authentication Server(spring-auth-server).

### Testing
 There are two types of test of Rest API controller:
- Testing with H2 in memory Data Base, BeerControllerTest and CustomerControllerTest.
- Testing against the MySQL Data Base, BeerControllerIT and CustomerControllerIT.



___________________________________________________



## Español
REST json api usando Spring Framework 6 de una base de datos de productos de cervezas y clientes.

### Herramientas utilizadas
- Spring Framework 6
    - Spring Boot 3.1.5
- Maven 3.9.5
- Git 2.42.0.windows.2
- Java Version 17
- Thymeleaf
- Hibernate
- Flyway
- Rest
- MySQL
- Mapstruct
- OpenCSV

### Dependencias de Maven 
- Spring Data JPA
- Spring Web
- H2 DataBase
- lombok
- spring-boot-devtools
- mysql-connector-java
- mapstruct
- opencsv

### Descripcion detallada del proyecto
El proyecto utiliza Spring Data con una base de datos de MySQL para guardar los datos de ejemplo, utilizando la url de  "http://localhost:8081/api/v1/" para la API.

### Seguridad
La seguridad se maneja mediante un Oauth2 Server the Authentication(spring-auth-server).

### Testing
Existen dos tipos de pruebas del controllador Rest API:
- Pruega contra una base de datos en memoria H2 en BeerControllerTest y CustomerControllerTest.
- Prueba contra una base de datos MySQL en BeerControllerIT y CustomerControllerIT.
