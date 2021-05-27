variable "cuenta" {
  default = "068039659953"
  description = "El identificador de la cuenta"
}

variable "ambiente" {
  description = "El ambiente (qa, prepdn, pdn)"
}

variable "zona" {
  default = "us-east-1"
  description = "La zona de AWS donde se crean los recursos"
}

variable "proyecto" {
  description = "Nombre del proyecto/contexto en el que existira esta aplicacion"
}

variable "appname" {
  description = "Nombre de la aplicacion"
}

variable "creadopor" {
  description = "Quien aprovisiona la bd?"
}

variable "aprobadopor" {
  description = "Quien aprueba la bd?"
}

variable "analista" {
  description = "Analista Soluciones"
}

variable "cencos" {
  description = "Numero de centro de costos"
}