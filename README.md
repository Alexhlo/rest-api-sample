# Sample RESTful API

Provides basic methods to work with Student service.

### Using
- Spring framework 5
- Spring-boot 2
- Project Lombok
- Swagger (springdoc-openapi)
- PostgreSQL
- Docker-compose
- Junit-jupiter
- Mockito
- Prometheus
- Grafana
- Spring-sleuth

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
    "application": {
      "name": "postgres-student-api"
    },
    "jpa": {
      "hibernate": {
        "ddl-auto": "update"
      },
      "database-platform": "org.hibernate.dialect.PostgreSQLDialect",
      "show-sql": false,
      "open-in-view": false
    },
    "datasource": {
      "url": "jdbc:postgresql://localhost:5432/university",
      "hikari": {
        "maximumPoolSize": 2,
        "connectionTestQuery": "SELECT 1",
        "maxLifetime": 180000
      },
      "username": "postgres",
      "password": "123"
    }
  },
  "springdoc": {
    "swagger-ui": {
      "disable-swagger-default-url": false
    }
  },
  "management": {
    "endpoints": {
      "web": {
        "exposure": {
          "include": "health,prometheus"
        }
      }
    },
    "metrics": {
      "export": {
        "prometheus": {
          "enabled": true
        }
      },
      "distribution": {
        "percentiles-histogram": {
          "[http.server.requests]": true
        }
      }
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


  student_app:
    image: rest-api-test:latest
    container_name: student_app
    build:
      context: ./services/.
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/university
      - SPRING_DATASOURCE_USERNAME=testuser
      - SPRING_DATASOURCE_PASSWORD=123
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
    depends_on:
      - postgres


  prometheus:
    image: prom/prometheus
    container_name: prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus:/prometheus
      - ./prometheus/config/prometheus.yml:/etc/prometheus/prometheus.yml


  grafana:
    image: grafana/grafana
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_USER=admin
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - ./grafana/data:/var/lib/grafana
```
- Create file - **psql-test-db-create.sql** (init test DB)
```postgresql
CREATE DATABASE university_test
    WITH
        OWNER = testuser
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TABLESPACE = pg_default
        CONNECTION LIMIT = -1;
```
- Create directory - **grafana/data**
- Create directory - **prometheus/config** and create file **prometheus.yml** inside it
```yml
global:
  scrape_interval: 5s

  external_labels:
    monitor: 'codelab-monitor'

scrape_configs:
  - job_name: 'rest-api-test'
    scrape_interval: 5s
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8080']
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
- check prometheus metrics - http://localhost:8080/actuator/prometheus
   - expected: ~~not 404~~ `many different text`
---

## Config prometheus in grafana
- login `http://localhost:3000`
- Configuration -> Data sources -> add Prometheus
   - set HTTP url `http://host.docker.internal:9090`
   - Save&Test
   - Expected `OK`
- Downloads base metrics from:
   - JVM (Micrometer) - `https://grafana.com/grafana/dashboards/4701-jvm-micrometer`
   - Micrometer Srping throughput - `https://grafana.com/grafana/dashboards/5373-micrometer-spring-throughput`
- Create your own dashbord:
   - Create panels 5min cycle:
      - requests per minute postgres/students/get/all - (metric browser) `increase(studentRepositoryFindAllCount_total[1m])`
      - query execution time in 1 minute - (metric browser) `increase(studentRepositoryFindAllTime_seconds_sum[1m]) / increase(studentRepositoryFindAllTime_seconds_count[1m])`

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
        "ddl-auto": "create-drop"
      },
      "database-platform": "org.hibernate.dialect.PostgreSQLDialect"
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


