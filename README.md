1. run docker compose
```
docker compose up -d
```
2. start the service
```
./gradlew bootRun
```

3. Test that the service is running
```
curl localhost:8080/swagger-ui/index.html#/
```

4. OpenAPI documentation available here
```
http://localhost:8080/swagger-ui/index.html#/
```