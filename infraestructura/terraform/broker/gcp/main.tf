provider "google" {
  project = "${var.project_id}"
  region  = "us-east1"
  zone    = "us-east1-b"
}
