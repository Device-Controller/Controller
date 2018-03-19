FROM java:8
ARG version
ENV version ${version}
COPY target/VislabController-$version.jar /vislab/VislabController.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController.jar"]
