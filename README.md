###### METRO APPLICATION
	
	
# How to build and run project
1. `mvn clean package docker:build`
2. from folder "docker" execute command "docker-compose up"



# Documentation springdoc-openapi
**You can reach Swagger definition at:**
http://localhost:8080/swagger-ui.html

**You can reach Open Api definition at**
http://localhost:8080/api-docs/

Once app is started db schema will be created(schema.sql) and 2 products will be initially saved to db(data.sql).
I didnt put code comment because I was time boxed for this task.

Example of calls:

1.http://localhost:8080/product?page=0&size=5

2.http://localhost:8080/order?from=1970-01-01&to=2020-11-01&page=0&size=10

Considerations:
1. authentication can be done with spring-boot-security+JWT. OAuth2 is overkill for this kind of simple rest api.
2. How can you make the service redundant? What considerations should you do?
This question I dont understand what is intention? To replace this Rest service with something else without work?
There are some rest api-s that can work as a layer over database to store and fetch data e.g. PostgREST.
Also some NoSQL data store can be used like Elasticsearch which already have Rest Api and built-in processors, pipelines, scripting languages...