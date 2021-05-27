#########################################
# IAM user, login profile and access key
#########################################
module "iam_user" {
  source = "modules\/iam-user"

  name                  = "${var.iamuser}"
  create_iam_access_key = true
  gcp_project           = var.proyecto-gcp
  
}