package co.com.proteccion.todo.microservice;

//TODO por favor cambie el paquete co.com.proteccion.todo.* que aparece en todo el demo por un paquete apropiado
//TODO para su aplicacion.

import co.com.proteccion.jano.core.providers.cache.JanoBaseCacheProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Esta clase es para proveer cache local en la JVM, pero lo ideal seria usar REDIS.
 *
 * Para usar un cache Redis:
 *
 * 1. Adicionar dependencia al proyecto
 *
 *    compile 'co.com.proteccion.jano:jano-cache-repository:4.X.X'
 *
 * 2. Configurar datos del cache en application.yml
 *
 *      spring:
 *        cache:
 *          type: redis
 *        redis:
 *          host: [host o IP de redis]
 *          port: [puerto]
 *          password: [contrasena]
 *
 * 3. Borrar la definicion de los beans de esta clase configuracion.
 *
 */
@Configuration
public class LocalCacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("JanoUsersCache"),
                new ConcurrentMapCache("JanoRolesCache"),
                new ConcurrentMapCache("JanoTokensCache"),
                new ConcurrentMapCache("JanoAppsCache"),
                new ConcurrentMapCache("JanoCertsCache"),
                new ConcurrentMapCache("JanoCarteraCache"),
                new ConcurrentMapCache("JanoNvlSegCache"),
                new ConcurrentMapCache("JanoAMEmprCache")
        ));
        cacheManager.initializeCaches();
        return cacheManager;
    }

    @Bean
    public JanoBaseCacheProvider janoBaseCacheProvider(){
        return () -> Mono.just(new ArrayList<>());
    }

}
