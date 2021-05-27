# Shell para la ejecucion de la VM en el contenedor. Este shell se referencia como entrypoint en el Dockerfile.

# Memoria en JVM con JDK1.8 en contenedores
# ver https://medium.com/adorsys/jvm-memory-settings-in-a-container-environment-64b0840e1d9e

java -XX:MaxRAMFraction=1 \
  -XX:MaxRAM=$(( $(cat /sys/fs/cgroup/memory/memory.limit_in_bytes) * 67 / 100 )) \
  -jar /app/app.jar