# TODO Spring Angular

A demo of my recent tech stack, thanks to [JHipster](http://jhipster.github.io/)
and [generator-gulp-angular](https://github.com/Swiip/generator-gulp-angular).

## Technologies

* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Maven](http://maven.apache.org/)
* [Spring Security](http://projects.spring.io/spring-security/)
* [Spring Security OAuth](http://projects.spring.io/spring-security-oauth/)
* [Spring MVC REST](http://spring.io/guides/gs/rest-service/)
* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
* [PostgreSQL](http://www.postgresql.org/) (Production, Development)
* [H2 Database Engine](http://www.h2database.com/) (Test)
* [Flyway Database Migration](http://flywaydb.org/)
* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/)
* [Gulp](http://gulpjs.com/)
* [AngularJS](https://angularjs.org/)
* ...

## Deploy

```
# Build the project
mvn package

# build the images
docker build -t todo-rest todo-rest/.
docker build -t todo-nginx todo-frontend/.

# run the containers
docker run -d --name todo-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=todo postgres:9.4.5
docker run -d --name todo-rest --link todo-db:todo-db todo-rest
docker run --name todo-nginx -p 8082:80 --link todo-rest:todo-rest -d todo-nginx

# or just use docker-compose
# for building and running
docker-compose up

```

## License

Released under [the MIT license](LICENSE).
