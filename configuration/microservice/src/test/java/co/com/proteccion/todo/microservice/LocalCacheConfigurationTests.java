package co.com.proteccion.todo.microservice;

import co.com.proteccion.jano.core.providers.cache.JanoBaseCacheProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.CacheManager;

public class LocalCacheConfigurationTests {

    LocalCacheConfiguration localCacheConfiguration;

    @Before
    public void prepare() {
        this.localCacheConfiguration = new LocalCacheConfiguration();
    }

    @Test
    public void testConfiguration() {
        CacheManager scm = localCacheConfiguration.cacheManager();
        Assert.assertNotNull(scm);

        JanoBaseCacheProvider janoBaseCacheProvider = localCacheConfiguration.janoBaseCacheProvider();
        Assert.assertNotNull(janoBaseCacheProvider);
        Assert.assertNotNull(janoBaseCacheProvider.flushCaches());
    }
}
