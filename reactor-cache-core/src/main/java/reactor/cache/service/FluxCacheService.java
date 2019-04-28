package reactor.cache.service;

import reactor.core.publisher.Flux;

/**
 * Flux cache service
 *
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
public interface FluxCacheService<T> extends CacheService<Flux<T>, T> {

    /**
     * Find Flux cache entity for the given key.
     *
     * @param retriever The Flux type retriever
     * @param key       The key to find
     * @return The Flux type cache entity
     */
    @Override
    Flux<T> find(Flux<T> retriever, String key);

}
