package reactor.cache;

/**
 * Reactor cache service
 *
 * @param <R> Flux or Mono
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
interface CacheService<R, T> {

    /**
     * Find reactor cache entity for the given key.
     *
     * @param retriever     The retriever
     * @param key           The key to find
     *
     * @return Cache entity
     */
    R find(R retriever, String key);

}
