package reactor.cache;

import org.springframework.cache.CacheManager;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Mono cache service implementation for spring cache
 *
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
public class MonoCacheService<T> extends SpringCacheService<Mono<T>, T> {

    /**
     * Constructor
     *
     * @param cacheManager  The spring cache manager
     * @param cacheName     The cache name
     * @param type          The Class of region cache type
     */
    public MonoCacheService(CacheManager cacheManager, String cacheName, Class<T> type) {
        super(cacheManager, cacheName, type);
    }

    /**
     * Find Mono cache entity for the given key.
     *
     * @param retriever The Mono type retriever
     * @param key The key to find
     *
     * @return The Mono type cache entity
     */
    @Override
    public Mono<T> find(Mono<T> retriever, String key) {
        return CacheMono.lookup(reader, key).onCacheMissResume(retriever).andWriteWith(writer);
    }

    /**
     * Mono Cache reader function
     */
    private Function<String, Mono<Signal<? extends T>>> reader = k -> Mono
            .justOrEmpty(cacheManager.getCache(cacheName).get(k, type)).map(Signal::next);

    /**
     * Mono Cache writer function
     */
    private BiFunction<String, Signal<? extends T>, Mono<Void>> writer = (k, signal) -> Mono
            .fromRunnable(() -> Optional.ofNullable(signal.get()).ifPresent(o -> cacheManager.getCache(cacheName).put(k, o)));
}