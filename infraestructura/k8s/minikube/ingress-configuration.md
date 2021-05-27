# Configurar Ingress con TLS en Minikube

## Pre-requisitos

Esta guia supone que tiene instalado: 

- [Minikube](https://minikube.sigs.k8s.io/docs/start/).

## Installacion Ingress

1. Activar el add-on
   ```   
   > minikube addons enable ingress
   ```

2. Verificar que el add-on fue activado

   ```   
   > minikube addons list
   
   - addon-manager: enabled
   - dashboard: enabled
   - default-storageclass: enabled
   - efk: disabled
   - freshpod: disabled
   - gvisor: disabled
   - heapster: disabled
   - ingress: enabled  <<<---
   - metrics-server: disabled
   - nvidia-driver-installer: disabled
   - nvidia-gpu-device-plugin: disabled
   - registry: disabled
   - registry-creds: disabled
   - storage-provisioner: enabled
   ```
   
3. Validar que se crea el pod con el NGINX ingress

   ```
   kubectl get pods -n kube-system | grep 'NAME\|ingress'
   NAME                                        READY   STATUS    RESTARTS   AGE
   nginx-ingress-controller-6958898f8f-w7948   0/1     Running   0          5s
   ```
   
## Aseguramiento TLS

En los siguientes pasos se va a crear un certificado TLS auto-firmado para el Ingress, de manera que se pueda acceder
los servicios de manera segura y encriptada por un canal HTTPS.

1. Crear un certificado y una llave:
   
   ```
    export DOMAIN=$(minikube ip).nip.io
    openssl req -x509 -newkey rsa:4096 -sha256 -nodes -keyout tls_self.key -out tls_self.crt -subj "/CN=*.${DOMAIN}" -days 365
   ```
   
   Hemos escogido crear un certificado autofirmado con el dominio "/CN=*.${DOMAIN}". Esto nos servira para reusar ese certificado
   con mas de una aplicaci&oacute;n.
   
    Este comando produce dos archivos: `tls_self.key` (llave) y `tls_self.crt` (certificado).
   
2. Crear un secreto de Kubernetes:
  
   Se debe crear un secreto en el cluster con la informaci&oacute;n del certificado y la llave.
   
   ```
   > SECRET_NAME=$(echo $DOMAIN | sed 's/\./-/g')-tls; echo $SECRET_NAME 
   116-203-44-29-nip-io-tls
   
   > kubectl create secret tls $SECRET_NAME --cert=tls_self.crt --key=tls_self.key --namespace arq-ref-demo
   secret/116-203-44-29-nip-io-tls created
   ```

   **IMPORTANTE**: Note que el `SECRET_NAME` va a quedar con el nombre del dominio `$(minikube ip).nip.io`, y el valor de 
   $(minikube ip) depende de su ambiente.
