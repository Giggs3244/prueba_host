package co.com.proteccion.todo.microservice;

//TODO por favor cambie el paquete co.com.proteccion.todo.* que aparece en todo el demo por un paquete apropiado
//TODO para su aplicacion.

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.text.NumberFormat;

@SpringBootApplication
@EnableWebFlux
@EnableWebFluxSecurity
@EnableCaching
@EnableAutoConfiguration
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    /**
     * Metodo que imprime la informacion de memoria que inicia usando la aplicacion en el
     * contenedor.
     */
    public static void printInfo() {
        Runtime runtime = Runtime.getRuntime();
        final NumberFormat format = NumberFormat.getInstance();
        final long maxMemory = runtime.maxMemory();
        final long allocatedMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long mb = 1024L * 1024L;
        final String mega = " MB";
        System.out.println("========================== Memory Info ==========================");
        System.out.println("Free memory: " + format.format(freeMemory / mb) + mega);
        System.out.println("Allocated memory: " + format.format(allocatedMemory / mb) + mega);
        System.out.println("Max memory: " + format.format(maxMemory / mb) + mega);
        System.out.println("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
        System.out.println("=================================================================");
    }

    @Bean
    public CommandLineRunner memInfoRunner() {
        return args -> printInfo();
    }
}
