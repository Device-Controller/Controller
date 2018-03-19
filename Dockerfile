FROM java:8

COPY maven/ /home/VislabController-latest.jar

WORKDIR /

CMD ["java", "-jar", "/tar/VislabController-latest.jar"]
