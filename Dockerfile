FROM java:8
COPY vislab/ /vislab/VislabController-latest.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController-latest.jar"]
