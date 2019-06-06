package reactor.cache.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import reactor.cache.spring.core.SpringMonoCache;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SpringMonoCacheTest {

    private final static String cacheName = "Mono-cache-test";
    private final static Class<String> cacheRegionType = String.class;

    private Cache cache;

    @Before
    public void init() {
        this.cache = new ConcurrentMapCache(cacheName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cacheNotNull() {
        new SpringMonoCache<>(null, cacheRegionType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cacheTypeNotNull() {
        new SpringMonoCache<>(cache, null);
    }

    @Test
    public void getAndPut() {
        final SpringMonoCache<String> springMonoCache = new SpringMonoCache<>(cache, cacheRegionType);
        final Mono<String> retriever = Mono.just("Value");
        final String key = "Key";
        final Mono<String> stringMono = springMonoCache.find(retriever, key);

        assertEquals(null, cache.get(key, cacheRegionType));

        StepVerifier.create(stringMono)
                .expectNext("Value")
                .expectComplete()
                .verify();

        assertEquals("Value", cache.get(key, cacheRegionType));
    }
}