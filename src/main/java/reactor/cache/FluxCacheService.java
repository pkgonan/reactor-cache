package reactor.cache;

import org.springframework.cache.Cache;
import reactor.core.publisher.Flux;

/**
 * Flux cache service implementation for spring cache
 *
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
public class FluxCacheService<T> extends SpringCacheService<Flux<T>, T> {

    /**
     * Constructor
     *
     * @param cache     The spring cache
     * @param type      The Class of region cache type
     */
    public FluxCacheService(Cache cache, Class<T> type) {
        super(cache, type);
    }

    /**
     * Find Flux cache entity for the given key.
     *
     * @param retriever The Flux type retriever
     * @param key       The key to find
     * @return The Flux type cache entity
     */
    @Override
    public Flux<T> find(Flux<T> retriever, String key) {
        throw new NotSupportException("FluxCacheService is not support");
    }
}
