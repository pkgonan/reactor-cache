package reactor.cache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

public class MonoCacheServiceTest {

    private MonoCacheService<String> monoCacheService;

    @Before
    public void init() {
        final String cacheName = "Mono-cache-test";
        final CacheManager cacheManager = new ConcurrentMapCacheManager(cacheName);
        final Class<String> cacheClassType = String.class;

        this.monoCacheService = new MonoCacheService<>(cacheManager, cacheName, cacheClassType);
    }

    @Test
    public void find() {
        final StringBuilder expected = new StringBuilder("Value");
        final StringBuilder result = new StringBuilder();

        final Mono<String> retriever = Mono.just("Value");
        final String key = "Key";

        monoCacheService.find(retriever, key).subscribe(result::append);

        assertEquals(expected.toString(), result.toString());
    }
}