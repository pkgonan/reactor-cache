package reactor.cache.spring;

import org.springframework.cache.Cache;
import reactor.cache.exception.NotSupportException;
import reactor.cache.service.FluxCacheService;
import reactor.core.publisher.Flux;

/**
 * Flux cache service implementation for spring cache
 *
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
public class SpringFluxCacheService<T> extends AbstractSpringCacheService<T> implements FluxCacheService<T> {

    /**
     * Constructor
     *
     * @param cache The spring cache
     * @param type  The Class of region cache type
     */
    public SpringFluxCacheService(Cache cache, Class<T> type) {
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
