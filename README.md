# Sample RESTful API

Provides basic methods to work with Student service.

### Using
- Spring framework 5
- Spring-boot 2
- Project Lombok
- Swagger
- PostgreSQL
- Docker-compose
- Junit-jupiter
- Mockito

### HttpMethods
##### Students controller (/postgres):
- GET /students/get/{id} - **get student by id**;
- GET /students/get/{lastName} - **get student by lastName**;
- GET /students/get/all - **get all students**;
- POST /students/get - **get students by ids as request body**

- POST /students/save - **save student as request body**
- POST /students/save/all - **save all students in request body**

- DELETE /students/delete/{id} - **delete student by id**

---

## Run application

- ### As single application
##### Start PostgreSQL as docker container:
`
docker run 
--name postgres_test
-e POSTGRES_USER=testuser
-e POSTGRES_PASSWORD=123
-e POSTGRES_DB=university
-p 5432:5432 
-d 
postgres
`
##### Setup spring-boot-application config: **application.yaml** or **SPRING_APPLICATION_JSON** 
```json
{
  "spring": {
    "jpa": {
      "hibernate": {
        "ddl-auto": "update",
        "dialect": "org.hibernate.dialect.PostgreSQLDialect"
      }
    },
    "datasource": {
      "url": "jdbc:postgresql://localhost:5432/university",
      "hikari": {
        "maximumPoolSize": 2,
        "connectionTestQuery": "SELECT 1",
        "maxLifetime": 180000
      },
      "username": "testuser",
      "password": "123"
    }
  }
}
```
##### Check application by url: http://localhost:8080/actuator/health expected response `{"status":"UP"}`

- ### As docker-compose application

Prepare the environment:
   - `cd /Documents`
   - `mkdir docker-compose-test`
   - `cd docker-compose-test`
   - `mkdir services`
   - run maven command into spring-boot app `mvn clean package -DskipTests`

##### Into main directory (docker-compose-test) 
- Create file **docker-compose.yml**
```yml
version: '3.8'

services:
  postgres:
    image: postgres
    container_name: university_db
    build:
      context: .
    ports:
      - "5432:5432"
    environment:
      - DATABASE_HOST=127.0.0.1
      - POSTGRES_USER=testuser
      - POSTGRES_PASSWORD=123
      - POSTGRES_DB=university
      - PGDATA=/var/lib/postgresql/data/pgdata
    volumes:
      - .:/var/lib/postgresql/data
      - ./psql-test-db-create.sql:/docker-entrypoint-initdb.d/psql-test-db-create.sql

  app:
    image: spring-boot-test-1.0.0
    container_name: student_app
    build:
      context: /services/.
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/university
      - SPRING_DATASOURCE_USERNAME=testuser
      - SPRING_DATASOURCE_PASSWORD=123
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
    depends_on:
      - postgres
```
- Create file - **psql-test-db-create.sql** (init test DB)
```sql
CREATE DATABASE university_test
    WITH
        OWNER = testuser
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TABLESPACE = pg_default
        CONNECTION LIMIT = -1;
```

##### Into services directory:
- copy **spring-boot-test-1.0.0.jar-file** into directory
- create file with name - "Dockerfile"
```
FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} service.jar
ENTRYPOINT ["java", "-jar", "/service.jar"]
```

### Environment is complete.

---

## Run next bash commands:
- `cd /Documents/docker-compose-test`
- `docker-compose up`

## After:
- check actuator health - http://localhost:8080/actuator/health 
- expected: `{"status":"UP"}`
---

## Testing application (need DB connection)
#### Run some bash commands
- `cd /Documents/docker-compose-test`
- `docker-compose up -d postgres`

### Create application-test.yaml configuration file in test/resources
```yaml
{
  "spring": {
    "jpa": {
      "hibernate": {
        # need to create and drop entities tables
        "ddl-auto": "create-drop", 
        "dialect": "org.hibernate.dialect.PostgreSQLDialect"
      }
    },
    "datasource": {
      # connect to test DB
      "url": "jdbc:postgresql://localhost:5432/university_test", 
      "hikari": {
        "maximumPoolSize": 2,
        "connectionTestQuery": "SELECT 1",
        "maxLifetime": 180000
      },
      "username": "testuser",
      "password": "123"
    }
  }
}
```

### Start test classes:
- [ApplicationContextBootTest.java](src/test/java/alex/hlo/springboot/test/ApplicationContextBootTest.java)
- [PostgresStudentsControllerTest.java](src/test/java/alex/hlo/springboot/test/PostgresStudentsControllerTest.java)


