package reactor.cache.service;

import reactor.core.publisher.Mono;

/**
 * Mono cache service
 *
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
public interface MonoCacheService<T> extends CacheService<Mono<T>, T> {

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