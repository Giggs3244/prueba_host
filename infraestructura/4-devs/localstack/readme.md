# LocalStack cheat sheet

Hoja de comandos basicos de localstack

## Buckets

**Crear un bucket**

```shell script
aws --endpoint-url=http://localhost:4572 s3 mb s3://mytestbucket
```

**Copiar un archivo al bucket**

```shell script
aws --endpoint-url=http://localhost:4572 s3 cp /tmp/mongo.log s3://mytestbucket
```

**Listar archivos del bucket**

```shell script
aws --endpoint-url=http://localhost:4572 s3 ls s3://mytestbucket
```

**Borrar el archivo**

```shell script
aws --endpoint-url=http://localhost:4572 s3 rm s3://mytestbucket/mongo.log
```



## SNS

**Listar los topicos creados**

```shell script
aws --endpoint-url=http://localhost:4575 sns list-topics
```

**Crear un tpico**

```shell script
aws --endpoint-url=http://localhost:4575 sns create-topic --name test-topic
```

**Publicar un mensaje en el topico**

```shell script
aws --endpoint-url=http://localhost:4575 sns publish  --topic-arn arn:aws:sns:us-east-1:123456789012:test-topic --message 'Test Message!'
```


## SQS

**Crear una cola**

```shell script
aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name test_queue
```

**Listar Colas**

```shell script
aws --endpoint-url=http://localhost:4576 sqs list-queues
```

**Enviar un mensaje a la cola**

```shell script
aws --endpoint-url=http://localhost:4576 sqs send-message --queue-url http://localhost:4576/123456789012/test_queue --message-body 'Test Message!'
```

