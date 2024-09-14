#####################################
#Configuracion imagen
#####################################
FROM maven:3.8.6-openjdk-8-slim AS build-env
LABEL maintainer="GGCH"

#####################################
#Creación directorio de trabajo
#####################################
WORKDIR /tmp/webapplegacy
COPY . .
RUN mvn clean install

#####################################
#Configuracion imagen final
#####################################
FROM tomcat:9.0.20-jre8-slim
LABEL maintainer="GGCH"

COPY --from=build-env /tmp/webapplegacy/target/*.war /usr/local/tomcat/webapps/

ENV TZ="America/Santiago"

#####################################
#Configuracion JAVA_OPTS
#####################################
ENV JAVA_OPTS="-XX:+UseG1GC -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UnlockDiagnosticVMOptions"

#####################################
#Exponer Servidor Tomcat
#####################################
EXPOSE $TOMCAT_PORT

#####################################
#Configuracion no necesaria
#####################################   
#CMD ["catalina.sh", "run"]