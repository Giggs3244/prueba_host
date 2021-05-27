#########################################
# IAM user, login profile and access key
#########################################
module "iam_user" {
  source = "modules\/iam-user"

  name          = "${var.iamuser}"
  force_destroy = true

  create_iam_user_login_profile = false
  create_iam_access_key = true
  password_reset_required = false

  tags = {
    proyecto = "${var.proyecto}",
    analista = "${var.analista}"
  }
}