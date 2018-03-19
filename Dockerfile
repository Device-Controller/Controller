FROM java:8
COPY vislab/ /vislab/VislabController-${ver}.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController-latest.jar"]
