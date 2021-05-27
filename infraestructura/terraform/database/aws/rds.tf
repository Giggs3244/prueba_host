resource "aws_db_subnet_group" "db_subnet_group" {
  name       = "db-subnet-${var.appname}-${var.ambiente}"
  subnet_ids = ["subnet-08717305aa27a51d8", "subnet-08fba8ce018249007"] # tengo duda con estos IDs
  tags = {
    Name = "${var.appname}-${var.ambiente} subnet group"
  }
}

resource "aws_db_parameter_group" "db_parameter_group" {
  name   = "db-prm-group-${var.appname}-${var.ambiente}"
  family = "postgres11"
}

resource "aws_db_instance" "db_instance" {
  allocated_storage    = 50
  identifier           = "${var.appname}-${var.ambiente}"
  storage_type         = "gp2"
  engine               = "postgres"
  multi_az             = "true" # necesitamos esto para QA?
  engine_version       = "11.2"
  instance_class       = "db.t2.small"
  name                 = "${var.appname}-${var.ambiente}"
  username             = "${var.appname}cxn2"
  password             = "${var.appname}_2019*"
  parameter_group_name = "db-prm-group-${var.appname}-${var.ambiente}"
  db_subnet_group_name = "db-subnet-${var.appname}-${var.ambiente}"
  vpc_security_group_ids = ["sg-0046e751ddb334af8"] # tengo dudas con este ID
#  security_group_names = ["sg-0046e751ddb334af8"] 
  tags = {
     Creado   = "${var.creadopor}"
     Aprobado = "${var.aprobadopor}"
     CentroDeCostos = "${var.cencos}"
     Proyecto = "${var.proyecto}"
     Analista = "${var.analista}"
  }
}

terraform {
  backend "s3" {
    bucket = "respaldos3htp"
    key    = "Terraform/${var.appname}-${var.ambiente}"
    region = "${var.zona}"
  }
}



