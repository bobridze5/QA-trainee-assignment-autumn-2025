FROM maven:3.9.11-amazoncorretto-21-alpine
WORKDIR /app

COPY Task2.1/pom.xml .
COPY Task2.1/src ./src

CMD ["mvn", "clean", "test"]