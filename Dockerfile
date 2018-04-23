FROM java:8
COPY VislabController.jar /vislab/VislabController.jar
COPY /lib /vislab/lib
COPY /plugins /vislab/plugins

WORKDIR /vislab/

CMD ["java", "-cp", "lib/*:plugins/*:VislabController.jar", "no.ntnu.vislab.vislabcontroller.Application"]
