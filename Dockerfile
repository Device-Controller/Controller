FROM java:8
ARG myArg
ENV myArg 0.2.0
COPY target/VislabController-$myArg.jar /vislab/VislabController.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController.jar"]
