# ── Etapa 1: Build com Maven ──────────────────────────
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências primeiro (cache eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código e compila
COPY src ./src
RUN mvn clean package -DskipTests

# ── Etapa 2: Imagem final leve ────────────────────────
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia apenas o JAR gerado
COPY --from=build /app/target/SiteLinkinPark-0.0.1-SNAPSHOT.jar

# Porta que o Spring Boot usa
EXPOSE 8081

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]