FROM java:8

COPY VislabController-0.2.0.jar /home/VislabController-0.2.0.jar

WORKDIR /

CMD ["java", "-jar", "/home/VislabController-0.2.0.jar"]
