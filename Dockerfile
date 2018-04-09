FROM java:8
FROM mysql:latest
ARG version
ENV version ${version}
COPY target/VislabController-$version.jar /vislab/VislabController.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController.jar"]
