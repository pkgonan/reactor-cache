package reactor.cache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import reactor.core.publisher.Flux;

public class FluxCacheServiceTest {

    private FluxCacheService<String> fluxCacheService;

    @Before
    public void init() {
        final String cacheName = "Flux-cache-test";
        final Cache cache = new ConcurrentMapCache(cacheName);
        final Class<String> cacheRegionType = String.class;

        this.fluxCacheService = new FluxCacheService<>(cache, cacheRegionType);
    }

    @Test(expected = NotSupportException.class)
    public void find() {
        fluxCacheService.find(Flux.empty(), "");
    }
}