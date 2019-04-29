package reactor.cache.core;

import reactor.core.publisher.Mono;

/**
 * Mono cache service
 *
 * @param <T> The Mono type of return value
 * @author Minkiu Kim
 */
public interface MonoCache<T> extends ReactorCache<Mono<T>, T> {

    /**
     * Find Mono cache entity for the given key.
     *
     * @param retriever The Mono type retriever
     * @param key       The key to find
     * @return The Mono type cache entity
     */
    @Override
    Mono<T> find(Mono<T> retriever, String key);

}