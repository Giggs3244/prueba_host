provider "google" {
  project = "${var.proyecto-gcp}"
//  credentials = "${file("/ruta/al/archivo/credenciales.json")}"
  region  = "us-east1"
  zone    = "us-east1-b"
}
