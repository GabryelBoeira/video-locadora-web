# --- Stage 1: Build ---
FROM eclipse-temurin:25-jdk AS build
WORKDIR /build

# 1. Copia o wrapper e arquivos de configuração do Gradle primeiro (para cache de camadas)
COPY ["gradlew", "gradlew"]
COPY ["gradle/", "gradle/"]
COPY ["build.gradle*", "settings.gradle*", "gradle.properties*", "./"]

# Corrigi quebras de linha estilo Windows (\r) no script gradlew e dá permissão de execução
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Baixa as dependências e o próprio Gradle Wrapper (cache das dependências)
RUN ./gradlew dependencies --no-daemon || true

# 2. Copia o código-fonte da aplicação
COPY ["src/", "src/"]

# Gera o arquivo JAR ignorando os testes unitários
RUN ./gradlew bootJar -x test --no-daemon

# --- Stage 2: Runtime ---
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copia o JAR gerado no estágio de build (geralmente fica em build/libs/)
COPY --from=build /build/build/libs/*.jar ./application.jar

EXPOSE 8082
EXPOSE 50082

ENTRYPOINT ["java", "--enable-native-access=ALL-UNNAMED", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5082", "-jar", "application.jar"]