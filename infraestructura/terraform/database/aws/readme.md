# Como aprovisionar Base de Datos en AWS RDS

## Pre-requisitos:
 - Tener instalado terraform cli
 - Tener instalado aws cli
     - Tener configurado aws cli con la informacion de la cuenta a usar para acceder a amazon

## Pasos

1. Ejecutar ```terraform init``` en la carpera donde estan los archivos ```.tf```, para instalar el plugin de terraform gcp.
3. Ejecutar ```terraform plan``` para validar el script y los recursos que se crearian o actualizarian
4. Ejecutar ```terraform apply``` para ejecutar el plan.

## Variables

Las siguientes son las variables requeridas para el script:

- **cuenta**, acutualmente tiene valor por defecto `068039659953` (cuenta de pruebas). Debe cambiarse para ejecutar en
  otra cuenta o ambiente.
- **aws_access_key**: key de acceso  de amazon (se puede configurar via variable de entorno tambien)
- **aws_secret_key**: secret key de acceso  de amazon (se puede configurar via variable de entorno tambien)
- **ambiente**: debe ser `qa` o `prepdn` o `pdn`
- **zona**: La zona donde se van a aprovisionar los destinos sns/sqs. Este script por defecto tiene `us-east-1`.
- **appname**: El nombre de la app que usa los destinos sns/sqs. Este valor va a hacer parte del nombre de los destinos,
por lo tanto: **Por favor use un nombre corto, sin espacios y en minusculas.**