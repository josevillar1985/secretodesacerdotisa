FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

# Permisos para mvnw (Windows → Linux)
RUN chmod +x mvnw

# Compilar
RUN ./mvnw clean package -DskipTests

# Copiamos el jar con nombre fijo
RUN cp target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
