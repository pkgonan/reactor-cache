package reactor.cache.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import reactor.core.publisher.Mono;

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

        springMonoCache.find(retriever, key).subscribe(result::append);

        assertEquals(expected.toString(), result.toString());
    }
}