variable "project_id" {
  #proyecto para QA y PrePdn
  default = "proteccion-davinci-iaas"
  description = "El Id del proyecto GCP"
}

variable "appname" {
  description = "El nombre de la aplicacion (En minusculas)"
}

variable "ambiente" {
  description = "El ambiente [qa, prepdn, pdn] (En minusculas)"
}