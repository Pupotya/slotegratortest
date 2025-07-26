FROM eclipse-temurin:22-jdk

RUN apt-get update && apt-get install -y \
    curl unzip zip wget git && \
    apt-get clean


WORKDIR /app

COPY . .

VOLUME /home/gradle/.gradle
RUN ./gradlew --no-daemon --version
RUN ./gradlew build -x test --no-daemon

CMD ["./gradlew", "test"]
