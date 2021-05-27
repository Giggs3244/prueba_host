variable "cuenta" {
  default = "068039659953"
  description = "El identificador de la cuenta donde se crean los recursos"
}

variable "zona" {
  default = "us-east-1"
  description = "La zona de AWS donde se crean los recursos"
}

variable "ambiente" {
  default = "qa"
  description = "Ambiente"
}

variable "aplicacion" {
  description = "Nombre de la aplicacion"
}

variable "usuario_aplicacion" {
  description = "Nombre del usuario aws aprovisionado para la aplicacion."
}

variable "proyecto" {
  description = "Nombre del proyecto"
}

variable "analista" {
  description = "Analista Soluciones responsable"
}

variable "arnkey" {
  default= "arn:aws:kms:us-east-1:068039659953:key/ae3e9974-b56c-43bf-818f-40ff071f7ab2"
  description = "para qa valor (arn:aws:kms:us-east-1:068039659953:key/ae3e9974-b56c-43bf-818f-40ff071f7ab2) para prod (arn:aws:kms:us-east-1:671579160235:key/45f8cd4b-a002-4cb0-be63-ecd9c282906f) "
}

variable "keysqs_name" {
  default= "ae3e9974-b56c-43bf-818f-40ff071f7ab2"
  description = "para qa valor (ae3e9974-b56c-43bf-818f-40ff071f7ab2) para prod (45f8cd4b-a002-4cb0-be63-ecd9c282906f) "
}


