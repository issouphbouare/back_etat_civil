# Utiliser l'image de base OpenJDK 11
FROM openjdk:11-jre-slim

# Argument pour spécifier le fichier JAR à copier
ARG JAR_FILE=target/*.jar

# Copier le fichier JAR généré dans l'image Docker
COPY ${JAR_FILE} app.jar

# Exposer le port 8080 (ou le port sur lequel votre application écoute)
EXPOSE 8080

# Commande pour démarrer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]
