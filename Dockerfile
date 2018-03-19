FROM java:8
COPY vislab/ /vislab/VislabController.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController.jar"]
