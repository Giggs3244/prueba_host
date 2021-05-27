FROM openjdk:8u212-jdk-alpine3.9
LABEL MANTAINER=proteccion@proteccion.com.co

RUN apk add tzdata
RUN cp /usr/share/zoneinfo/America/Bogota /etc/localtime
RUN echo "America/Bogota" > /etc/timezone
RUN apk del tzdata

# Install tini
RUN apk add --no-cache tini

VOLUME /tmp
WORKDIR /app

# cambie 'arq*' por una expresion que haga match con su aplicacion compilada.
ADD build/libs/arq*.jar .
RUN mv arq*.jar app.jar

# No se necesitan estas dos lineas si va a inyectar la configuracion como un configmap o un secret (Kubernetes)
# RUN mkdir config
# ADD build/resources/main/application-mq.yml config/application.yml

EXPOSE 8443

ADD entrypoint.sh .
ENTRYPOINT ["/sbin/tini", "--", "sh", "entrypoint.sh"]