FROM java:8
ARG ver
COPY target/VislabController-${ver}.jar /vislab/VislabController.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController.jar"]
