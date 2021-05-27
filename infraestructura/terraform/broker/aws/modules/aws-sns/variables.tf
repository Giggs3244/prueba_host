variable "topic_name" {
  description = "topico de aplicacion"
  type        = string
}

variable "topic_err_name" {
  description = "topico de error aplicacion"
  type        = string
}

variable "queue_name" {
  description = "cola de aplicacion"
  type        = string
}

variable "queue_err_name" {
  description = "cola de error de aplicacion"
  type        = string
}

variable "queue_splunk_name" {
  description = "cola de splunk"
  type        = string
}

variable "queue_err_splunk_name" {
  description = "cola de error de aplicacion para splunk"
  type        = string
}

# #########

variable "aws_account" {
  description = "El identificador de la cuenta donde se crean los recursos"
}

variable "arnkey" {
  default= "arn:aws:kms:us-east-1:068039659953:key/ae3e9974-b56c-43bf-818f-40ff071f7ab2"
  description = "para qa valor (arn:aws:kms:us-east-1:068039659953:key/ae3e9974-b56c-43bf-818f-40ff071f7ab2) para prod (arn:aws:kms:us-east-1:671579160235:key/45f8cd4b-a002-4cb0-be63-ecd9c282906f) "
}

variable "keysqs_name" {
  default= "ae3e9974-b56c-43bf-818f-40ff071f7ab2"
  description = "para qa valor (ae3e9974-b56c-43bf-818f-40ff071f7ab2) para prod (45f8cd4b-a002-4cb0-be63-ecd9c282906f) "
}


variable "environment_prefix" {
  description = "El ambiente (qa, prepdn, pdn)"
}

variable "zone" {
  description = "La zona de AWS donde se crean los recursos"
}

variable "arn_user" {
  description = "Nombre del usuario aws aprovisionado para la aplicacion."
}

# #########

variable "tags" {
  description = "A map of tags to add to all resources."
  type        = map(string)
  default     = {}
}