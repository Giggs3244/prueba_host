# Guia aprovisionamiento con Terraform

## Pre-requisitos:
 - Tener instalado terraform cli
 - Tener una credencial de acceso a GCP (es un arhivo json)

## Antes de iniciar

1. Establecer la variable de entorno ```GOOGLE_APPLICATION_CREDENTIALS``` con la ruta completa y nombre del archivo json que contiene la credencial
de acceso a GCP.
2. Ejecutar ```terraform init``` en la carpera donde estan los archivos ```.tf```, para instalar el plugin de terraform gcp.

## Revisar el plan (Opcional)

Esta es una forma de decirle a terraform que revise todo el plan e indique que recursos
va a ser creados, actualizados, borrados.

- Ejecutar ```terraform plan``` 
   - Se le va a solicitar un nombre de aplicacion, el cual ser usar&aacute; como variable ```app_name```.
   - Al terminar el script le mostrara un plan de lo que se va a aprovisionar.   
   
## Ejecutar el plan

Esta ya es la ejecucion como tal del script para crear/actualizar/borrar recursos.

- Ejecutar ```terraform apply```
   - Se le va a solicitar un nombre de aplicacion, el cual se usar&aacute; como variable ```app_name```.

## Variables

Las siguientes son las variables requeridas para el script:

- **project_id**, El proyecto donde se crean los topicos y colas. Acutualmente tiene valor por defecto `proteccion-davinci-iaas`. Debe cambiarse si aplica.
- **ambiente**: debe ser `qa` o `prepdn` o `pdn`
- **appname**: El nombre de la app que usa los topics/subs. Este valor va a hacer parte del nombre de los destinos,
por lo tanto: **Por favor use un nombre corto, sin espacios y en minusculas.**