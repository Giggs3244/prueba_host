# Demo de Aplicaci&oacute;n Java con Arquitectura de Referencia de 
  Protecci&oacute;n S.A.

Este repositorio es una plantilla para desarrollar aplicaciones estilo 
microservicios basado en una una arquitectura inspirada en el manifiesto Reactivo. 

Vaya a la **[Wiki de la arquitectura de referencia](https://vostpmde37.proteccion.com.co:10443/ARQ_Referencia/arq_referencia_proteccion/wikis/home)** para mas Informaci&oacute;n.

Este repositorio esta representado el **BACKEND JAVA**.

## Modulos de la aplicacion DEMO

Como la aplicacion esta construida siguiendo los principios de la 
[Arquitectura Limpia](https://github.com/mattia-battiston/clean-architecture-example), 
se han dispuesto los siguientes modulos:  

Domain
 - **Model**: Modulo que representa las entidades de dominio.
 - **Usecase**: Modulo que representa los casos de uso del demo.

Entrypoints
 - **reactive-web**: Modulo donde se implementan los controladores REST que su 
   aplicacion va a exponer diferentes al endpoint ```/commands``` de la 
   arquitectura base.

Providers
 - **data/jpa-repository**: Modulo que representa la capa de persistencia del demo.

Helpers
 - Modulos helpers
 
Adapters
 - Modulos adaptadores.
 
Configuration
 - **microservice**: Modulo que configura toda la infraestructura, entry-points 
   y servicios.

*NOTA*: los modulos tienen la autoconfiguracion de Spring activada. Ver mas info en 
[Spring Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html).

### QuickStart

  - Clonar este Repositorio
  
    ```
    git clone --depth 1 https://vostpmde37.proteccion.com.co:10443/ARQ_Referencia/arq_referencia_proteccion.git
    ```
    
  - Compilar y Correr la aplicacion con broker por defecto (ibm mq)
    
    ```
    gradlew clean build
    gradlew bootRun
    ```
    
  - Enviar peticiones al backend
  
    - Peticiones GET al endpoint REST de consulta incluido en el demo
    
      ```
      curl -X GET \
        https://localhost:8443/tasks \
        -H 'Accept: application/json' \
        -H 'Accept-Encoding: gzip, deflate' \
        -H 'Authorization: Bearer <INSERTE UN TOKEN DE AZURE>' \
        -H 'Cache-Control: no-cache' \
        -H 'ClienteDNI: CC-0000' \
        -H 'Connection: keep-alive' \
        -H 'Host: localhost:8443'
      ```
  
    - Enviar un Comando 
    
      ```
      curl -X POST \
        https://localhost:8443/commands \
        -H 'Accept: application/json' \
        -H 'Accept-Encoding: gzip, deflate' \
        -H 'Authorization: Bearer <INSERTE UN TOKEN DE AZURE>' \
        -H 'Cache-Control: no-cache' \
        -H 'Connection: keep-alive' \
        -H 'Content-Length: 494' \
        -H 'Content-Type: application/json' \
        -H 'Host: localhost:8443' \
        -d '{
          "comando": {
              "nombre": "task.save",
              "payload": {
                "name": "Aprender a usar la arq de referencia de proteccion",
                "done": false
              }
          }
      }
      '
      ```

## Personalizar

El demo funciona? Cool, Ahora cambiemos algunos datos para que su 
aplicaci&oacute;n empiece a tener personalidad propia.

- En **gradle.properties**
 
  - version 
    ```
    version=1.0.0-SNAPSHOT 
    ```
    El valor de la version lo sigue administrando el equipo de desarrollo y 
    debe incrementarse por cada release.

  - Nombre y Descripci&oacute;n
  
    ```
    microservice_name= MyCoolApplication
    description = "Esta app hace cosas..."
    ```  
    Por favor bautice su aplicacion con un nombre apropiado, y ademas proporcione 
    una corta descripci&oacute;n.
  
  - Info para Sonarqube
    ```
    sonar_project_name=MyCoolApplication 
    ```
    Indique cual sera el nombre del proyecto de cara a sonar, se recomienda que 
    sea el mismo `microservice_name`.
    
    ```
    sonar_project_key=co.com.proteccion.<root_package>
    ```
    El key para sonar puede ser el mismo `sonar_project_name`, o tambien una 
    indicacion del paquete raiz de su aplicacion.


## Incompatibilidades

La arquitectura esta basada en `Spring Webflux` para crear aplicaciones web 
reactivas. Esto supone que existen incompatibilidades con librerias 
o componentes de terceros que esten basadas por ejemplo en `Servlets`.

Por el momento hemos detectado problemas con el uso de las siguientes librerias:

- Swagger: Al parecer el fabricante planea darle soporte a Spring-webflux en la 
  [3.0.0](https://elstarit.nl/?p=1213).


## Autenticaci&oacute;n

La autenticaci&oacute;n con AZURE no es responsabilidad del Backend java. 
Ese flujo es responsabilidad del FrontEnd. 

En el siguienre repositorio existen demos en Angular para guiar a los 
desarrolladores Front en la implementacion de los flujos de 
Autenticaci&oacute;n con `Azure` o `Azure B2C`.

[GIT Demos Autenticacion con Azure](https://vostpmde37.proteccion.com.co:10443/ARQ_Referencia/arq_referencia_proteccion_ui)
 