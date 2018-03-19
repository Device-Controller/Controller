FROM java:8
ARG ver
COPY vislab/ /vislab/VislabController-${ver}.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController-latest.jar"]
