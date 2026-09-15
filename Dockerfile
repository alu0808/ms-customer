# ETAPA 1: Construcción (Usamos una imagen pesada con Maven y Java 21)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
# Creamos una carpeta de trabajo dentro del contenedor
WORKDIR /app
# Copiamos el archivo pom.xml y descargamos dependencias primero (caché)
COPY pom.xml .
RUN mvn dependency:go-offline
# Copiamos el código fuente
COPY src ./src
# Compilamos el proyecto creando el archivo .jar
RUN mvn clean package -DskipTests

# ETAPA 2: Empaquetado final (Usamos una imagen súper ligera solo con Java)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiamos ÚNICAMENTE el .jar generado en la Etapa 1
COPY --from=builder /app/target/ms-customer-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto del microservicio
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor arranque
ENTRYPOINT ["java", "-jar", "app.jar"]