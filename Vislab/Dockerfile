FROM java:8
COPY VislabController.jar /vislab/VislabController.jar
COPY application.properties /vislab/application.properties
COPY ssl-server.jks /vislab/ssl-server.jks
COPY /plugins /vislab/plugins

WORKDIR /vislab/

CMD ["java", "-jar", "VislabController.jar"]
