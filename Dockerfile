FROM java:8
ENV ver = latest
COPY vislab/ /vislab/VislabController-${ver}.jar

WORKDIR /

CMD ["java", "-jar", "/vislab/VislabController-latest.jar"]
