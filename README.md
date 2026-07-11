Run postgres

docker run --name postgres-db \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=inventory_db \
-p 5432:5432 \
-d postgres:17

validate postgres container is running
docker ps

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

Verify database
docker exec -it postgres-db psql -U postgres -d inventory_db

select * from product;