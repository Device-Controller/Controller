FROM java:8
ARG myArg
COPY target/VislabController-$myArg.jar /vislab/VislabController.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController.jar"]
