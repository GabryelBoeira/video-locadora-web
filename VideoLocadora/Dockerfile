FROM eclipse-temurin:25-jdk AS build
WORKDIR /build

COPY [".mvn/", ".mvn/"]
COPY ["mvnw", "mvnw"]
COPY ["pom.xml", "pom.xml"]

RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

COPY ["src/", "src/"]
RUN ./mvnw -q clean package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /build/target/*.jar ./application.jar

EXPOSE 5005
EXPOSE 8080

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "application.jar"]