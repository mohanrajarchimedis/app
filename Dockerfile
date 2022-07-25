
FROM ubuntu
MAINTAINER mohan333dx@gmail.com

RUN apt-get update
RUN apt-get install curl -y
RUN apt-get install openjdk-11-jdk -y
RUN mkdir /home/new
COPY apache-tomcat-8.5.81.tar.gz /home/new/
RUN mkdir /opt/tomcat
RUN tar xzvf /home/new/apache-tomcat-8.5.81.tar.gz -C /opt/tomcat --strip-components=1
COPY server.xml /opt/tomcat/conf/server.xml
COPY tomcat.service /etc/systemd/system/
CMD ["/opt/tomcat/bin/startup.sh","run"]
CMD ["/opt/tomcat/bin/catalina.sh","run"]
COPY target/java-1.war /opt/tomcat/webapps/

EXPOSE 8081
