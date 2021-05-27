provider "aws" {
  region     = "${var.zona}"

  # Esta es una forma de especificar las credenciales de acceso
  shared_credentials_file = "/Users/gjmartin/.aws/credentials"
  profile                 = "default"

  # Otra forma es indicar directamente aqui las credenciales, o mediante la definicion de
  # variables en 'main.tf'
  #access_key = "anaccesskey" o si se tiene una variable: "${var.aws_access_key}"
  #secret_key = "asecretkey" o si se tiene una variable: "${var.aws_secret_key}"

  # Otra forma es tenerlas definidas como variables de entorno
  /*
  shell:
  $ export AWS_ACCESS_KEY_ID="anaccesskey"
  $ export AWS_SECRET_ACCESS_KEY="asecretkey"
  $ export AWS_DEFAULT_REGION="us-xxxx-1"
  */
}