resource "google_service_account" "cuentaservicio" {
  account_id   = var.name
  display_name = "cuenta de servicio para ${var.name}"
  project = var.gcp_project
}

resource "google_service_account_key" "cuentaservicio-key" {
  count = var.create_iam_access_key ? 1 : 0
  service_account_id = "${google_service_account.cuentaservicio.name}"
  public_key_type = "TYPE_X509_PEM_FILE"
}

resource "google_project_iam_member" "iam-member" {
  project = var.gcp_project
  role    = "roles/pubsub.viewer"
  member  = "serviceAccount:${google_service_account.cuentaservicio.email}"
}

/*data "google_iam_policy" "cuentaservicio-policy" {
  binding {
    role = "roles/pubsub.publisher"
    members = [
      "serviceAccount:${google_service_account.cuentaservicio.email}",
    ]
  }

  binding {
    role = "roles/pubsub.subscriber"
    members = [
      "serviceAccount:${google_service_account.cuentaservicio.email}",
    ]
  }

  binding {
    role = "roles/pubsub.viewer"
    members = [
      "serviceAccount:${google_service_account.cuentaservicio.email}",
    ]
  }
}

resource "google_service_account_iam_policy" "cuentaservicio-iam" {
  service_account_id = "${google_service_account.cuentaservicio.name}"
  policy_data = "${data.google_iam_policy.cuentaservicio-policy.policy_data}"
}*/

/*resource "google_service_account_iam_binding" "cuentaservicio-iam" {
  service_account_id = "${google_service_account.cuentaservicio.name}"
  role               = "roles/pubsub.publisher"
  members = [
      "serviceAccount:${google_service_account.cuentaservicio.email}"
  ]
}*/

resource "google_project_iam_binding" "project" {
  project = var.gcp_project
  role    = "roles/pubsub.publisher"
  members = [
    "serviceAccount:${google_service_account.cuentaservicio.email}",
  ]
}
