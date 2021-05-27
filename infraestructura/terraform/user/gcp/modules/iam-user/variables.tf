variable "gcp_project" {
  description = "GCP project ID"
  type        = string
}

variable "create_iam_access_key" {
  description = "Whether to create IAM access key"
  type        = bool
  default     = true
}

variable "name" {
  description = "Desired name for the IAM user"
  type        = string
}
