# Manifiestos para despliegue en Amazon EKS

Esta carpeta contiene los manifiestos para desplegar en el servicio de Kubernetes de Amazon (EKS).

**Aclaracion**: Estos manifiestos se entregan en este proyecto para que sirvan de GUIA al proveedor para interactuar
con el equipo de operacion de Protecci&oacute;n y/o para aprovisionar en ambientes propios.

En ning&uacute;n caso el proveedor aprovisionar&aacute; o crear&aacute; directamente estos recursos en las cuentas AWS de 
Protecci&oacute;n.

## Pre-requisitos

Esta guia supone que tiene: 

- [eksctl](https://eksctl.io/) Herramienta de gestion de cluster EKS.
- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/).
- Ademas, que tiene las credenciales y permisos necesarios de AWS.


## Nombres usados en los manifiestos

Para este **DEMO**, los manifiestos crean los siguientes recursos, cuyos nombres son solo de referencia. 
Para su aplicaci&oacute;n deben ser reemplazados por nombres mas apropiados.

| Elemento  | Nombre en Cl&uacute;ster | Archivo  |
|---|---|---|
| Namespace  | `arq-ref-demo`  | namespace.yaml  |
| Configmap  | `arq-ref-demo-config`  | configmap.yaml  |
| Deployment  | `arq-ref-demo-back`  | deployment.yaml  |
| Service  | `arq-ref-demo-back-svc`  | service.yaml  |
| Ingress  | `arqdemo-ingress`  | ingress.yaml  |

## Compilar el demo y construir contenedor

Antes de desplegar el demo en EKS debe:

1. Compilar la microaplicaci&oacute;n:

   ```
   cd configuration/microservice
   
   ./gradlew clean build -x test
   ```

2. Construir imagen localmente.

   ```
   docker build . -t arq-ref-demo:1
   ```
   
3.    
   
## Desplegar el demo

1. Crear el _namespace_ `arq-ref-demo`:
   
   Como pr&aacute;ctica todos los elementos de una aplicaci&oacute;n se deben desplegar en un namespace.
   
   ```
    kubectl apply -f namespace.yaml
    ```
      
   Este namespace solo se debe crear la primera vez.

2. Crear el _configmap_ `arq-ref-demo-config`:
  
   Como pr&aacute;ctica cualquier archivo de configuraci&oacute;n que use la aplicacion se debe crear como un `configmap` en
   el cluster.
   
   ```
    kubectl apply -f configmap.yaml
    ```
   
   El configmap tiene la informacion del archivo `application.yml`, que contiene toda la parametrizaci&oacute;n de la 
   aplicaci&oacute;n que trabaja con arquitectura de referencia.
  
   Este configmap se puede crear cada vez que se desee hacer un cambio en dicha configuraci&oacute;n.

3. Desplegar el _contenedor_ `arq-ref-demo-back` :
  
   El archivo *deployment.yaml* describe el montaje de los recursos que se deben armar en el cl&uacute;ster para poner
   a correr la aplicaci&oacute;n.
   
   ```
    kubectl apply -f deployment.yaml
    ```
   
   En este archivo se define, entre otros:
   
   - La imagen del contenedor que se va a usar:
   
     ```
     ...
     image: arq-ref-demo:1
     ...
     ```
   - Montaje del (los) configmap(s)
   - Establecimiento de recursos minimos, maximos de CPU y RAM
   
4. Crear _servicio_ `arq-ref-demo-back-svc` :
  
   El archivo *service.yaml* describe el montaje de los recursos que se deben armar en el cl&uacute;ster poder exponer
   el _contenedor_ por un puerto espec&iacute;fico.
   
   ```
   kubectl apply -f service.yaml
   ```
   
   El servicio queda con una IP privada del cluster:
   
   ```
   > kubectl get svc --namespace arq-ref-demo
   
   NAME                    TYPE       CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
   arq-ref-demo-back-svc   NodePort   10.102.107.15   <none>        8443:31911/TCP   134m
   ```
   
   Pero para poderla acceder hay que conocer la IP del nodo de kubernetes, 
   
   ```
   > kubectl describe nodes
   
   TODO: explicar
   
   ```
      
5. Crear _Ingress_ `arqdemo-ingress`:

   El ingress es el que expone el servicio.
   
   ```
   kubectl apply -f ingress.yaml
   ```
