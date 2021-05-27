# Crear usuario de conexion a GCP para la aplicacion

## Pre-requisitos:
 - Tener instalado terraform cli
 - Tener instalado Google Cloud SDK
     - Tener configurado google sdk con la informacion de la cuenta a usar para acceder a GCP.
     
## Pasos

1. Ejecutar ```terraform init``` en la carpera donde estan los archivos ```.tf```, para instalar el plugin de terraform gcp.
3. Ejecutar ```terraform plan``` para validar el script y los recursos que se crearian o actualizarian
4. Ejecutar ```terraform apply``` para ejecutar el plan.

## Variables

Las siguientes son las variables requeridas para el script:

- **proyecto-gcp**, acutualmente tiene valor por defecto `proteccion-davinci-iaas`.
- **iamuser**: El nombre de usuario para asignar a esta app. *Por favor use un nombre corto, sin espacios y en minusculas.*