FROM openjdk:13
COPY . /usr/src/autocomplete
WORKDIR /usr/src/autocomplete
#RUN javac Main.java
CMD ["./gradlew", "bootRun"]