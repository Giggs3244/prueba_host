#########################################
# Crear topicos
#########################################
resource "aws_sns_topic" "app_topic" {
  name = var.topic_name
  tags = var.tags
  kms_master_key_id = var.keysqs_name
}

resource "aws_sns_topic" "app_err_topic" {
  name = var.topic_err_name
  tags = var.tags
  kms_master_key_id = var.keysqs_name
}

#########################################
# Crear Colas
#########################################

resource "aws_sqs_queue" "app_queue" {
  name = var.queue_name
  tags = var.tags
  kms_master_key_id = var.keysqs_name
  kms_data_key_reuse_period_seconds = 86400
}

resource "aws_sqs_queue" "splunk_queue" {
  name = var.queue_splunk_name
  tags = var.tags
  kms_master_key_id = var.keysqs_name
  kms_data_key_reuse_period_seconds = 86400
}

resource "aws_sqs_queue" "app_err_queue" {
  name = var.queue_err_name
  tags = var.tags
  kms_master_key_id = var.keysqs_name
  kms_data_key_reuse_period_seconds = 86400
}

resource "aws_sqs_queue" "splunk_err_queue" {
  name = var.queue_err_splunk_name
  tags = var.tags
  kms_master_key_id = var.keysqs_name
  kms_data_key_reuse_period_seconds = 86400
}

#########################################
# Crear suscripciones
#########################################

resource "aws_sns_topic_subscription" "app_topic_2_app_queue" {
  topic_arn = "${aws_sns_topic.app_topic.arn}"
  protocol  = "sqs"
  endpoint  = "${aws_sqs_queue.app_queue.arn}"
  raw_message_delivery = true
}

resource "aws_sns_topic_subscription" "app_topic_2_splunk_queue" {
  topic_arn = "${aws_sns_topic.app_topic.arn}"
  protocol  = "sqs"
  endpoint  = "${aws_sqs_queue.splunk_queue.arn}"
  raw_message_delivery = true
}

resource "aws_sns_topic_subscription" "app_err_topic_2_app_err_queue" {
  topic_arn = "${aws_sns_topic.app_err_topic.arn}"
  protocol  = "sqs"
  endpoint  = "${aws_sqs_queue.app_err_queue.arn}"
  raw_message_delivery = true
}

resource "aws_sns_topic_subscription" "app_err_topic_2_splunk_err_queue" {
  topic_arn = "${aws_sns_topic.app_err_topic.arn}"
  protocol  = "sqs"
  endpoint  = "${aws_sqs_queue.splunk_err_queue.arn}"
  raw_message_delivery = true
}

# El topico '', y la cola 'q-proteccion-ui-mercurio-${var.ambiente}' ya deben debe existir con antelacion
# a la ejecucion de este script.
resource "aws_sns_topic_subscription" "app_topic_2_mercurio_queue" {
  topic_arn = "${aws_sns_topic.app_topic.arn}"
  protocol  = "sqs"
  endpoint  = "arn:aws:sqs:${var.zone}:${var.aws_account}:q-proteccion-ui-mercurio-${var.environment_prefix}"
  filter_policy = "{ \"scope\": [ \"SEND_TO_UI\", \"SEND_TO_ALL\" ] }"
  raw_message_delivery = true
}

#########################################
# Crear policies para el usuario
#########################################
resource "aws_iam_user_policy" "topics_policy1" {
  name = "sns_policy"
  user = var.arn_user

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "rule1",
            "Effect": "Allow",
            "Action": [
                "sns:ListSubscriptionsByTopic",
                "sns:Publish"
            ],
            "Resource": [
                "${aws_sns_topic.app_topic.arn}",
                "${aws_sns_topic.app_err_topic.arn}"
            ]
        },
        {
            "Sid": "rule2",
            "Effect": "Allow",
            "Action": "sns:ListTopics",
            "Resource": "*"
        }
    ]
}
EOF
}

resource "aws_iam_user_policy" "queues_policy1" {
  name = "sqs_policy"
  user = var.arn_user

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "rule1",
            "Effect": "Allow",
            "Action": [
                "sqs:DeleteMessage",
                "sqs:GetQueueUrl",
                "sqs:ChangeMessageVisibility",
                "sqs:SendMessageBatch",
                "sqs:ReceiveMessage",
                "sqs:SendMessage",
                "sqs:GetQueueAttributes",
                "sqs:ListQueueTags",
                "sqs:ListDeadLetterSourceQueues",
                "sqs:DeleteMessageBatch",
                "sqs:PurgeQueue",
                "sqs:ChangeMessageVisibilityBatch",
                "sqs:SetQueueAttributes"
            ],
            "Resource": [
                "${aws_sqs_queue.app_queue.arn}",
                "${aws_sqs_queue.app_err_queue.arn}"
            ]
        },
        {
            "Sid": "rule2",
            "Effect": "Allow",
            "Action": "sqs:ListQueues",
            "Resource": "*"
        },
        {
            "Sid": "kms2cmk",
            "Action": [
                "kms:Encrypt",
                "kms:Decrypt",
                "kms:ReEncrypt*",
                "kms:GenerateDataKey*",
                "kms:DescribeKey"
            ],
            "Resource": [
                "${var.arnkey}"
            ],
            "Effect": "Allow"
        }
    ]
}
EOF
}

#########################################
# Crear policy para colas
#########################################
//politica que permite bajar los mensajes del topico de aplicacion a la cola de aplicacion.
resource "aws_sqs_queue_policy" "app_queue_policy" {
  queue_url = "${aws_sqs_queue.app_queue.id}"

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Id": "${aws_sqs_queue.app_queue.arn}/SQSDefaultPolicy",
  "Statement": [
    {
      "Sid": "First",
      "Effect": "Allow",
      "Principal": {
        "AWS": [
          "*"
        ]
      },
      "Action": "SQS:SendMessage",
      "Resource": "${aws_sqs_queue.app_queue.arn}",
      "Condition": {
        "ArnEquals": {
          "aws:SourceArn": "${aws_sns_topic.app_topic.arn}"
        }
      }
    }
  ]
}
POLICY
}

//politica que permite bajar los mensajes del topico de aplicacion a la cola de splunk.
resource "aws_sqs_queue_policy" "splunk_queue_policy" {
  queue_url = "${aws_sqs_queue.splunk_queue.id}"

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Id": "${aws_sqs_queue.splunk_queue.arn}/SQSDefaultPolicy",
  "Statement": [
    {
      "Sid": "First",
      "Effect": "Allow",
      "Principal": {
        "AWS": [
          "*"
        ]
      },
      "Action": "SQS:SendMessage",
      "Resource": "${aws_sqs_queue.splunk_queue.arn}",
      "Condition": {
        "ArnEquals": {
          "aws:SourceArn": "${aws_sns_topic.app_topic.arn}"
        }
      }
    }
  ]
}
POLICY
}

//politica que permite bajar los mensajes del topico de error aplicacion a la cola de error de la aplicacion.
resource "aws_sqs_queue_policy" "app_err_queue_policy" {
  queue_url = "${aws_sqs_queue.app_err_queue.id}"

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Id": "${aws_sqs_queue.app_err_queue.arn}/SQSDefaultPolicy",
  "Statement": [
    {
      "Sid": "First",
      "Effect": "Allow",
      "Principal": {
        "AWS": [
          "*"
        ]
      },
      "Action": "SQS:SendMessage",
      "Resource": "${aws_sqs_queue.app_err_queue.arn}",
      "Condition": {
        "ArnEquals": {
          "aws:SourceArn": "${aws_sns_topic.app_err_topic.arn}"
        }
      }
    }
  ]
}
POLICY
}

//politica que permite bajar los mensajes del topico de error aplicacion a la cola de error de splunk.
resource "aws_sqs_queue_policy" "splunk_err_queue_policy" {
  queue_url = "${aws_sqs_queue.splunk_err_queue.id}"

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Id": "${aws_sqs_queue.splunk_err_queue.arn}/SQSDefaultPolicy",
  "Statement": [
    {
      "Sid": "First",
      "Effect": "Allow",
      "Principal": {
        "AWS": [
          "*"
        ]
      },
      "Action": "SQS:SendMessage",
      "Resource": "${aws_sqs_queue.splunk_err_queue.arn}",
      "Condition": {
        "ArnEquals": {
          "aws:SourceArn": "${aws_sns_topic.app_err_topic.arn}"
        }
      }
    }
  ]
}
POLICY
}

