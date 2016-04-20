FROM java:openjdk-8-jdk-alpine

# add directly the jar
ADD target/*.jar /app.jar

# to create a modification date
RUN sh -c 'touch /app.jar'

# creates a mount point
VOLUME /tmp

CMD ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]

EXPOSE 8080
