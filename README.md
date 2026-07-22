Run postgres

docker run --name postgres-db \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=inventory_db \
-p 5432:5432 \
-d postgres:17

validate postgres container is running
docker ps
docker rm <container-id>

mvn clean compile
mvn spring-boot:run

swagger UI
http://localhost:8080/swagger-ui/index.html

POST /api/v1/products
{
"sku":"IPHONE16",
"name":"iPhone 16",
"description":"128GB",
"category":"Mobile",
"price":90000,
"availableQuantity":100
}

PUT /api/v1/products/1
{
"name":"iPhone 16 Pro",
"description":"256GB Black",
"category":"Mobile",
"price":99999,
"availableQuantity":50
}


Verify database
docker exec -it postgres-db psql -U postgres -d inventory_db

select * from product;

## Running Locally

Start dependencies:

```bash
Redis
docker compose up -d
docker ps

docker compose down

//delete named volumes of database
docker compose down -v

mvn spring-boot:run

verify redis
docker exec -it inventory-redis redis-cli
 KEYS *
 TYPE products::1
 GET products::1
 
 docker stop redis
 docker start redis
PING
```

Check Circuit breaker details
GET http://localhost:8080/actuator
GET http://localhost:8080/actuator/circuitbreakerevents
GET http://localhost:8080/actuator/circuitbreakers
GET /actuator/metrics
