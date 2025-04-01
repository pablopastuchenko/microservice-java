FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia o código-fonte para dentro do contêiner
COPY . .

# Compila o projeto dentro do contêiner
RUN ./mvnw package -DskipTests

# Copia o JAR gerado
RUN cp target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
