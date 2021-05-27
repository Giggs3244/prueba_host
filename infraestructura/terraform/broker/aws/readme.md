# Como aprovisionar destinos SNS/SQS en AWS con terraform

## Pre-requisitos:
 - Tener instalado terraform cli
 - Tener instalado aws cli
     - Tener configurado aws cli con la informacion de la cuenta a usar para acceder a amazon
     - las credenciales para ejecutar este script se toman por defecto de ```/Users/<usuario>/.aws/credentials``` y del 
       profile ```[default]```. Para modificar la inyecci&oacute;n de las credenciales edite el archivo ```main.tf```
- Ya se debe tener un usuario de aplicacion (Conexion) creado en IAM(AWS).

## Pasos

1. Ejecutar ```terraform init``` en la carpera donde estan los archivos ```.tf```, para instalar el plugin de terraform gcp.
3. Ejecutar ```terraform plan``` para validar el script y los recursos que se crearian o actualizarian
4. Ejecutar ```terraform apply``` para ejecutar el plan.

## Variables

Las siguientes son las variables requeridas para el script:

- **cuenta**, acutualmente tiene valor por defecto `068039659953` (cuenta de pruebas). Debe cambiarse si aplica.
- **ambiente**: debe ser `qa` o `prepdn` o `pdn`
- **zona**: La zona donde se van a aprovisionar los destinos sns/sqs. Este script por defecto tiene `us-east-1`.
- **aplicacion**: El nombre de la app que usa los destinos sns/sqs. Este valor va a hacer parte del nombre de los destinos,
  por lo tanto: *Por favor use un nombre corto, sin espacios y en minusculas.*
- **usuario_aplicacion**: El nombre del usuario creado para la aplicacion en IAM (AWS) al que se le van a dar los permisos
  para publicar y consumir mensajes en los destinos que se van a crear. Nota: Es solo el username, NO el Arn.