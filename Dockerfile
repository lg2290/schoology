FROM openjdk:13
COPY . /usr/src/autocomplete
WORKDIR /usr/src/autocomplete
CMD ["./gradlew", "bootRun"]