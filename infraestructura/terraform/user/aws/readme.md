# Crear usuario de conexion a AWS para la aplicacion

## Pre-requisitos:
 - Tener instalado terraform cli
 - Tener instalado aws cli
     - Tener configurado aws cli con la informacion de la cuenta a usar para acceder a amazon
     - las credenciales para ejecutar este script se toman por defecto de ```/Users/<usuario>/.aws/credentials``` y del 
       profile ```[default]```. Para modificar la inyecci&oacute;n de las credenciales edite el archivo ```main.tf```
## Pasos

1. Ejecutar ```terraform init``` en la carpera donde estan los archivos ```.tf```, para instalar el plugin de terraform gcp.
3. Ejecutar ```terraform plan``` para validar el script y los recursos que se crearian o actualizarian
4. Ejecutar ```terraform apply``` para ejecutar el plan.

## Variables

Las siguientes son las variables requeridas para el script:

- **cuenta**, acutualmente tiene valor por defecto `671579160235`.
- **zona**: La zona donde se van a aprovisionar los destinos sns/sqs. Este script por defecto tiene `us-east-1`.
- **nombreusuario**: El nombre de usuario para asignar a esta app. *Por favor use un nombre corto, sin espacios y en minusculas.*
- **proyecto**: El nombre del Proyecto para crear un ```tag``` al usuario.