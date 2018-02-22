# Use official Maven runtime as parent image
FROM maven:3.5.2-jdk-8

# Set working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
ADD . /app

ENV MAVEN_VERSION 3.5.2

RUN mkdir -p /usr/share/maven \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1
#  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

VOLUME /root/.m2

# Make port 8080 available to the world outside this container
#EXPOSE 8080

CMD ["mvn"]