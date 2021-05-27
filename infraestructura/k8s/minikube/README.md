# Manifiestos para despliegue en Minikube

Minikube es un entorno para correr kubernetes de manera local, espeficicamente para temas de aprendizaje o 
desarrollo. 

## Pre-requisitos

Esta guia supone que tiene instalado: 

- [Minikube](https://minikube.sigs.k8s.io/docs/start/).
- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/).

## Nombres usados en los manifiestos

Para este **DEMO**, los manifiestos crean los siguientes recursos, cuyos nombres son solo de referencia. 
Para su aplicaci&oacute;n deben ser reemplazados por nombres mas apropiados.

| Elemento  | Nombre en Cl&uacute;ster | Archivo  |
|---|---|---|
| Namespace  | `arq-ref-demo`  | namespace.yaml  |
| Secret  | `arq-ref-demo-config`  | configsecret.yaml  |
| Deployment  | `arq-ref-demo-back`  | deployment.yaml  |
| Service  | `arq-ref-demo-back-svc`  | service.yaml  |

## Compilar el demo y construir contenedor

Antes de desplegar el demo en Minikube debe:

1. Compilar la microaplicaci&oacute;n:

   ```
   cd configuration/microservice
   
   # ./gradlew clean build -x test
   ```

2. Construir imagen localmente.

   ```
   # docker build . -t arq-ref-demo:LATEST
   ```
   
## Desplegar el demo en minikube

1. Crear el _namespace_ `arq-ref-demo`:
   
   Como pr&aacute;ctica todos los elementos de una aplicaci&oacute;n se deben desplegar en un namespace.
   
   ```
   # kubectl apply -f namespace.yaml
   ```
      
   Este namespace solo se debe crear la primera vez.

2. Crear el _secret_ `arq-ref-demo-config`:
  
   Como pr&aacute;ctica cualquier archivo de configuraci&oacute;n que use la aplicacion se debe crear como un `secret` en
   el cluster.
   
   ```
   # kubectl apply -f configsecret.yaml
   ```
   
   El secret tiene la informacion del archivo `application.yml`, que contiene toda la parametrizaci&oacute;n de la 
   aplicaci&oacute;n que trabaja con arquitectura de referencia.
  
   Este secret se puede actualizar cada vez que se desee hacer un cambio en dicha configuraci&oacute;n.

3. Desplegar el _contenedor_ `arq-ref-demo-back` :
  
   El archivo *deployment.yaml* describe el montaje de los recursos que se deben armar en el cl&uacute;ster para poner
   a correr la aplicaci&oacute;n.
   
   ```
   # kubectl apply -f deployment.yaml
   ```
   
   En este archivo se define, entre otros:
   
   - La imagen del contenedor que se va a usar:
   
     ```
     ...
     image: arq-ref-demo:1
     ...
     ```
   - Montaje del (los) secrets(s)
   - Establecimiento de recursos minimos, maximos de CPU y RAM
   
4. Exponer _servicio_ `arq-ref-demo-back-svc` :
  
   El archivo *service.yaml* describe el montaje de los recursos que se deben armar en el cl&uacute;ster poder exponer
   el _contenedor_ por un puerto espec&iacute;fico.
   
   ```
   # kubectl apply -f service.yaml
   ```
   
   El servicio queda con una IP del cluster:
   
   ```
   # kubectl get svc --namespace arq-ref-demo
   
     NAME                TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
     arq-ref-demo-back   ClusterIP   10.98.169.251   <none>        8443/TCP   3m11s
   ```
   
   Como el servicio se creo con tipo `clusterIP` usted no puede acceder directamente al backend con esa IP. 
   
   **Servicio expuesto con NodePort**
   
   Si el servicio se crease on tipo `NodePort` si podria accederla de manera local, asi: 
   
   ```
   # minikube ip
   192.168.99.100
   ```
   
   o tambien se puede preguntar directamente por el servicio:
   
   ```
   # minikube service arq-ref-demo-back-svc --namespace arq-ref-demo
   |--------------|-----------------------|-----------------------------|
   |  NAMESPACE   |       NAME            |             URL             |
   |--------------|-----------------------|-----------------------------|
   | arq-ref-demo | arq-ref-demo-back-svc | http://192.168.99.100:31911 |
   |--------------|-----------------------|-----------------------------|
   Opening kubernetes service  arq-ref-demo/arq-ref-demo-back-svc in default browser...
   ```
   
   El servicio se puede acceder con la IP `192.168.99.100`, puerto `31911`.
   
   **IMPORTANTE**: 
   - Para el resto de la guia se supone que el servicio se cre&oacute; con tipo **ClusterIP**.
   - Puede exponer el servicio tipo 'ClusterNode', si asi lo desea. Omite el paso 5. Pero la aplicación
     debe exponerse con protocolo HTTPS.
   
   
5. Crear _ingress_ `arqdemo-ingress`:
   
   Como vio en el paso #4, no es posible acceder al backend directamente (cuando se crea con tipo ClusterIP), para exponer el servicio
   al mundo exterior se requiere un _ingress_ que reciba el tr&aacute;fico, y lo enrute al _service_.
   
   **NOTA:** El ingress es lo mas parecido a como se va a exponer la aplicaci&oacute;n en el mundo real.. Por eso antes de continuar, siga la guia 
   [Configuraci&oacute;n de ingress en Minikube](ingress-configuration.md). y luego contin&uacute;e en manual.
   
   - Cuando ya aprovision&oacute; el ingress. Ud debio haber quedado con unas variables de entorno:
   
     - ```SECRET_NAME=$(echo $DOMAIN | sed 's/\./-/g')-tls```
     - ```DOMAIN=$(minikube ip).nip.io```
  
   - Necesita tener en cuenta una variable adicional:
   
     - ```INGRESS_HOST=minikube.$(minikube ip).nip.io```
     
   - Ahora se debe reemplazar en el archivo `ingress.yaml`, los placeholders con la informaci&oacute;n contenida
     en esas variables.
   
     ```yaml
     apiVersion: extensions/v1beta1
     kind: Ingress
     metadata:
       namespace: arq-ref-demo
       name: ingress-demo
     spec:
       tls:
         - secretName: ${SECRET_NAME}
           hosts:
             - '*.${DOMAIN}'
       rules:
         - host: ${INGRESS_HOST}
           http:
             paths:
               - backend:
                   serviceName: arq-ref-demo-back
                   servicePort: 8443
       ```
   
   - Cuando haga los reemplazos, guarde el archivo y aplique la configuracion en Minikube
   
     ```
     # kubectl apply -f ingress.yml
     ingress.extensions/ingress-demo created

     ```
     
   - Validar la ruta del ingress
   
     Usted puede acceder al contenedor con la URL definida en la variable ${INGRESS_HOST}
     
     ```
     curl -k https://$INGRESS_HOST
     {"timestamp":1566334398399,"path":"/","status":403,"error":"Forbidden","message":"Access Denied" }
     ```
     
     Esa es la respuesta normal del backend cuando no se envia un token. Es decir, ya pudimos llegar hasta el backend
     via el ingress.
   
    
