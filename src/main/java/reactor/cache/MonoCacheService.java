package reactor.cache;

import org.springframework.cache.CacheManager;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Spring Mono cache service implementation for Reactor
 *
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
public class MonoCacheService<T> extends AbstractCacheService<Mono<T>, T> {

    public static <T> MonoCacheService of(CacheManager cacheManager, String cacheName, Class<T> tClass) {
        return new MonoCacheService(cacheManager, cacheName, tClass);
    }

    private MonoCacheService(CacheManager cacheManager, String cacheName, Class<T> tClass) {
        super(cacheManager, cacheName, tClass);
    }

    @Override
    public Mono<T> find(Mono<T> retriever, String key) {
        return CacheMono.lookup(reader, key).onCacheMissResume(retriever).andWriteWith(writer);
    }

    private Function<String, Mono<Signal<? extends T>>> reader = k -> Mono
            .justOrEmpty(cacheManager.getCache(cacheName).get(k, tClass)).map(Signal::next);

    private BiFunction<String, Signal<? extends T>, Mono<Void>> writer = (k, signal) -> Mono
            .fromRunnable(() -> Optional.ofNullable(signal.get()).ifPresent(o -> cacheManager.getCache(cacheName).put(k, o)));
}