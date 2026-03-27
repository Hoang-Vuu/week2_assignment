# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package

# Stage 2: Run the application
FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

COPY --from=build /app .

CMD ["sh", "-c", "echo ShoppingCart Docker Image is running"]