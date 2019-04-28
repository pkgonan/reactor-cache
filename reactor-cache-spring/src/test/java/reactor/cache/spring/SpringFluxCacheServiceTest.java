package reactor.cache.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import reactor.cache.exception.NotSupportException;
import reactor.core.publisher.Flux;

public class SpringFluxCacheServiceTest {

    private SpringFluxCacheService<String> springFluxCacheService;

    @Before
    public void init() {
        final String cacheName = "Flux-cache-test";
        final Cache cache = new ConcurrentMapCache(cacheName);
        final Class<String> cacheRegionType = String.class;

        this.springFluxCacheService = new SpringFluxCacheService<>(cache, cacheRegionType);
    }

    @Test(expected = NotSupportException.class)
    public void find() {
        springFluxCacheService.find(Flux.empty(), "");
    }
}