# ================================
# Stage 1: Build JavaFX app with Maven
# ================================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests


# ================================
# Stage 2: Runtime Image with JavaFX + X11
# ================================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Install GUI dependencies required by JavaFX
RUN apt-get update && \
    apt-get install -y wget unzip libgtk-3-0 libgbm1 libx11-6 && \
    apt-get clean

# Install JavaFX SDK
RUN wget https://download2.gluonhq.com/openjfx/21/openjfx-21_linux-x64_bin-sdk.zip -O /tmp/javafx.zip \
    && unzip /tmp/javafx.zip -d /opt \
    && rm /tmp/javafx.zip

ENV JAVAFX_HOME=/opt/javafx-sdk-21
ENV DISPLAY=host.docker.internal:0

# Copy built jar from stage 1
COPY --from=build /app/target/ShoppingCart-1.0-SNAPSHOT.jar app.jar

# Run the JavaFX application
CMD ["java", "--module-path=/opt/javafx-sdk-21/lib", "--add-modules=javafx.controls,javafx.fxml", "-jar", "app.jar"]