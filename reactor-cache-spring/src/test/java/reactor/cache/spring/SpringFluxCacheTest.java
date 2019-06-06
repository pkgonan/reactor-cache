package reactor.cache.spring;

import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import reactor.cache.exception.NotSupportException;
import reactor.cache.spring.core.SpringFluxCache;
import reactor.core.publisher.Flux;

public class SpringFluxCacheTest {

    @Test(expected = NotSupportException.class)
    public void find() {
        final String cacheName = "Flux-cache-test";
        final Cache cache = new ConcurrentMapCache(cacheName);
        final Class<String> cacheRegionType = String.class;

        new SpringFluxCache<>(cache, cacheRegionType);
    }
}