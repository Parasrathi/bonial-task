From gradle:jdk11

EXPOSE 8080

RUN mkdir /project

COPY . /project/

WORKDIR /project

RUN gradle clean build
RUN gradle bootJar
ENTRYPOINT java -jar /project/build/libs/bonial-task-*.jar