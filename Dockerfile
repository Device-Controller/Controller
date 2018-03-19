FROM java:8
ARG myArg
ENV myArg ${myArg}
COPY target/VislabController-$myArg.jar /vislab/VislabController.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController.jar"]
