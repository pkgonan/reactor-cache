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

public class SpringMonoCacheTest {

    private SpringMonoCache<String> springMonoCache;

    @Before
    public void init() {
        final String cacheName = "Mono-cache-test";
        final Cache cache = new ConcurrentMapCache(cacheName);
        final Class<String> cacheRegionType = String.class;

        this.springMonoCache = new SpringMonoCache<>(cache, cacheRegionType);
    }

    @Test
    public void find() {
        final StringBuilder expected = new StringBuilder("Value");
        final StringBuilder result = new StringBuilder();

        final Mono<String> retriever = Mono.just("Value");
        final String key = "Key";

        final Mono<String> stringMono = springMonoCache.find(retriever, key).doOnSuccess(result::append);

        StepVerifier.create(stringMono)
                .expectNext("Value")
                .expectComplete()
                .verify();

        assertEquals(expected.toString(), result.toString());
    }
}