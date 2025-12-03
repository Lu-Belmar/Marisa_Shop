# Usamos una imagen base con Java 17 oficial (Eclipse Temurin es muy estable)
FROM eclipse-temurin:17-jdk-jammy

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos todos los archivos del proyecto al contenedor
COPY . .

# Damos permisos de ejecución al wrapper de Maven (esto arregla el error "command not found")
RUN chmod +x mvnw

# Compilamos el proyecto (esto genera el archivo .jar)
RUN ./mvnw clean package -DskipTests

# Exponemos el puerto (Spring Boot usa 8080 por defecto)
EXPOSE 8080

# Comando para iniciar la aplicación (busca cualquier .jar generado en target)
CMD java -jar target/*.jar
