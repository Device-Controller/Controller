FROM java:8

COPY maven/ /home/VislabController-latest.jar

WORKDIR /

CMD ["java", "-jar", "/home/VislabController-latest.jar"]
