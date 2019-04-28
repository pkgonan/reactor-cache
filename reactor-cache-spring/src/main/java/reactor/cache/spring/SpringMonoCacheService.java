package reactor.cache.spring;

import org.springframework.cache.Cache;
import reactor.cache.CacheMono;
import reactor.cache.service.MonoCacheService;
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
public class SpringMonoCacheService<T> extends AbstractSpringCacheService<T> implements MonoCacheService<T> {

    /**
     * Constructor
     *
     * @param cache The spring cache
     * @param type  The Class of region cache type
     */
    public SpringMonoCacheService(Cache cache, Class<T> type) {
        super(cache, type);
    }

    /**
     * Find Mono cache entity for the given key.
     *
     * @param retriever The Mono type retriever
     * @param key       The key to find
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
            .justOrEmpty(cache.get(k, type)).flatMap(t -> Mono.justOrEmpty(Signal.next(t)));

    /**
     * Mono Cache writer function
     */
    private BiFunction<String, Signal<? extends T>, Mono<Void>> writer = (k, signal) -> Mono
            .fromRunnable(() -> Optional.ofNullable(signal.get())
                    .ifPresent(o -> cache.put(k, o)));
}