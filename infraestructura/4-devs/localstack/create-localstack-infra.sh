#!/usr/bin/env bash

#
# Scripts para uso con localstack.
#
# Para una referencia al ambiente que puede usar como semilla para acondicionar un ambiente de desarrollo, vaya a:
# https://vostpmde37.proteccion.com.co:10443/ARQ_Referencia/plantilla-localstack
#
# Tenga en cuenta que al hacer uso de esta utilidad, el equipo de desarrollo acepta que:

# - Protección no esta en la obligación de proveer ambientes de desarrollo para que el (los) proveedor(es)
#   y sus realicen la implementacióon de artefactos de software.
#
# - Protección, en la figura del Equipo de Excelencia en Servicios de TI, no dará ningún tipo de
#   soporte a estos scripts, o artefactos relacionados.
#
#   - Errores de instalación o configuración de Docker o Docker-compose, Localstack, Pyhton o cualquier dependencia de
#     software / hardware o Sistema Operativo, relacionada al uso de esos productos.
#
#   - Errores relacionadas al uso y configuracion de  Docker o Docker-compose, Localstack.
#     Para ello el equipo de desarrollo dispondrá de la pagina de 'Issues' del producto Localstack en github,
#     o del conocido google.com.
#
# - Tampoco asumirá responsabilidades derivadas por el mal funcionamiento / mala configuración que se haga de
#   esta herramienta, tales como:
#
#   - Remoción de impedimentos técnicos relacionados a Docker, Docker-compose o Localstack.
#   - Items de backlog relacionados a Docker, Docker-compose, localstack.
#   - Retrasos o demoras en la implementacion del sprint derivado de los items anteriores.
#

export AWS_DEFAULT_REGION=us-east-1

# Crear topicos
aws --endpoint-url=http://localhost:4575 sns create-topic --name t-topico1-dev
aws --endpoint-url=http://localhost:4575 sns create-topic --name t-topico1-err-dev

# crear la cola
aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name q-cola1-dev

# crear la cola de mercurio
aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name q_global_ui

# Subscribir cola 'q-cola1-dev' al topico 't-topico1-dev'
aws --endpoint-url=http://localhost:4575 sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:t-topico1-dev \
 --protocol sqs --notification-endpoint arn:aws:sns:us-east-1:000000000000:q-cola1-dev \
 --attributes "{\"RawMessageDelivery\":\"true\"}"

# Subscribir cola 'q_global_ui' (de mercurio) al topico 't-topico1-dev'
aws --endpoint-url=http://localhost:4575 sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:t-topico1-dev \
 --protocol sqs --notification-endpoint arn:aws:sns:us-east-1:000000000000:q_global_ui \
 --attributes "{\"RawMessageDelivery\": \"true\", \"FilterPolicy\": \"{\\\"scope\\\": [\\\"SEND_TO_UI\\\", \\\"SEND_TO_ALL\\\"]}\"}"

# Crear Bucket S3 (Opcional si va a trabajar con S3)
# NOTA: Debe crear una entrada en su archivo /etc/hosts asi:
#     127.0.0.1   demobucket.localhost
# para evitar un error que dice que no se encuentra ese servidor
#
aws --endpoint-url=http://localhost:4572 s3 mb s3://demobucket