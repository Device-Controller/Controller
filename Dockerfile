FROM java:8
ARG myArg
RUN echo 1 $myArg 2 ${myArg}
COPY target/VislabController-${myArg}.jar /vislab/VislabController.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController.jar"]
