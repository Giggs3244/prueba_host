#!/bin/sh

# depende de haber generado el certificado intermedio. Ejecutar script en 'infraestructura/ssl-4-dev' primero.

# genera certificado para servidor 'localhost'
keytool -genkeypair -keyalg RSA -keysize 3072 -alias 192.168.99.100.nip.io \
  -dname "CN=192.168.99.100.nip.io,OU=DEXTI DEV CERTIFICATE,O=PROTECCION,C=CO" \
  -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:192.168.99.100.nip.io,IP:192.168.99.100" \
  -validity 720 -keystore ./server.p12 -storepass secret -keypass secret -deststoretype pkcs12

keytool -certreq -keystore ./server.p12 -storepass secret -alias 192.168.99.100.nip.io \
  -keypass secret -file ./server.csr

keytool -gencert -keystore ../../../ssl-4-dev/root-ca/ca.p12 -storepass secret -infile ./server.csr \
  -alias root-ca -keypass secret -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:192.168.99.100.nip.io,IP:192.168.99.100" \
  -validity 720 -rfc -outfile ./server.pem

openssl pkcs12 -in ./server.p12 -nocerts -out privateKey.pem
openssl rsa -in privateKey.pem -out key.pem

rm privateKey.pem server.csr