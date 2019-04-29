package reactor.cache.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import reactor.cache.exception.NotSupportException;
import reactor.core.publisher.Flux;

public class SpringFluxCacheTest {

    private SpringFluxCache<String> springFluxCache;

    @Before
    public void init() {
        final String cacheName = "Flux-cache-test";
        final Cache cache = new ConcurrentMapCache(cacheName);
        final Class<String> cacheRegionType = String.class;

        this.springFluxCache = new SpringFluxCache<>(cache, cacheRegionType);
    }

    @Test(expected = NotSupportException.class)
    public void find() {
        springFluxCache.find(Flux.empty(), "");
    }
}