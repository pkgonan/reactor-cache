package reactor.cache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import reactor.core.publisher.Flux;

public class FluxCacheServiceTest {

    private FluxCacheService<String> fluxCacheService;

    @Before
    public void init() {
        final String cacheName = "Flux-cache-test";
        final CacheManager cacheManager = new ConcurrentMapCacheManager(cacheName);
        final Class<String> cacheClassType = String.class;

        this.fluxCacheService = new FluxCacheService<>(cacheManager, cacheName, cacheClassType);
    }

    @Test(expected = NotSupportException.class)
    public void find() {
        fluxCacheService.find(Flux.empty(), "");
    }
}