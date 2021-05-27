# Este script es solo una plantilla, provista por el equipo de arquitectura
# servira para el 90% de los casos y se podra adaptar segun la necesidad.

resource "google_pubsub_topic" "topico_app" {
  name = "t-${var.appname}-${var.ambiente}"
  project = "${var.project_id}"
  labels = {
    env = "${var.ambiente}"
    proyecto = "${var.appname}"
  }
}

# El 'name' de este topico es el errorDestination en application.yml
resource "google_pubsub_topic" "topico_err" {
  name = "t-${var.appname}-err-${var.ambiente}"
  project = "${var.project_id}"
  labels = {
    env = "${var.ambiente}"
    proyecto = "${var.appname}"
  }
}

# El 'name' de esta subscripcion es el subscribeDestination en application.yml
resource "google_pubsub_subscription" "subapp" {
   project = "${var.project_id}"
   name  = "s-${var.appname}-${var.ambiente}"
   topic = "${google_pubsub_topic.topico_app.name}"
   labels = {
     env = "${var.ambiente}"
     proyecto = "${var.appname}"
   }
   message_retention_duration = "1200s"
   retain_acked_messages = false
   ack_deadline_seconds = 20
}

# sub de uso exclusivo splunk
resource "google_pubsub_subscription" "subappsplunk" {
   project = "${var.project_id}"
   name  = "s-${var.appname}-splunk-${var.ambiente}"
   topic = "${google_pubsub_topic.topico_app.name}"
   labels = {
     env = "${var.ambiente}"
     proyecto = "${var.appname}"
   }
   # 20 minutes
   message_retention_duration = "1200s"
   retain_acked_messages = false
   ack_deadline_seconds = 20
}

resource "google_pubsub_subscription" "suberrapp" {
  project = "${var.project_id}"
  name  = "s-${var.appname}-err-${var.ambiente}"
  topic = "${google_pubsub_topic.topico_err.name}"
  labels = {
    env = "${var.ambiente}"
    proyecto = "${var.appname}"
  }
  # 20 minutes
  message_retention_duration = "1200s"
  retain_acked_messages = false
  ack_deadline_seconds = 20
}

# sub de uso exclusivo splunk
resource "google_pubsub_subscription" "suberrsplunk" {
  project = "${var.project_id}"
  name  = "s-${var.appname}-splunk-err-${var.ambiente}"
  topic = "${google_pubsub_topic.topico_err.name}"
  labels = {
    env = "${var.ambiente}"
    proyecto = "${var.appname}"
  }
  # 20 minutes
  message_retention_duration = "1200s"
  retain_acked_messages = false
  ack_deadline_seconds = 20
}

# Ya debe existir el topico y suscripcion para mercurio, los cuales
# son uno para todas las aplicaciones. No se crea por cada aplicacion.
# y deben existir en GCP con este nombre:

# - topico = t-proteccion-ui-topic-<ambiente>
# - sub    = s-proteccion-ui-topic-mercurio-<ambiente>

# El nombre del topico es el que se configura en application.yml en el item 'uiDestination'