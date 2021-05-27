module "aws_sns" {
  source = "./modules/aws-sns"

  topic_name = "t-${var.aplicacion}-${var.ambiente}"
  topic_err_name = "t-${var.aplicacion}-err-${var.ambiente}"

  queue_name = "q-${var.aplicacion}-${var.ambiente}"
  queue_err_name = "q-${var.aplicacion}-err-${var.ambiente}"

  queue_splunk_name = "q-${var.aplicacion}-splunk-${var.ambiente}"
  queue_err_splunk_name = "q-${var.aplicacion}-splunk-err-${var.ambiente}"

  aws_account = var.cuenta
  environment_prefix = var.ambiente
  zone = var.zona
  arn_user = var.usuario_aplicacion

  tags = {
    proyecto = var.proyecto,
    analista = var.analista,
    aplicacion = var.aplicacion
  }
}