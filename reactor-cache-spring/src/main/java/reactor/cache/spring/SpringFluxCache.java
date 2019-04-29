package reactor.cache.spring;

import org.springframework.cache.Cache;
import reactor.cache.core.FluxCache;
import reactor.cache.exception.NotSupportException;
import reactor.core.publisher.Flux;

/**
 * Flux cache implementation for spring cache
 *
 * @param <T> The Flux type of return value
 * @author Minkiu Kim
 */
public class SpringFluxCache<T> extends AbstractSpringCache<T> implements FluxCache<T> {

    /**
     * Constructor
     *
     * @param cache The spring cache
     * @param type  The Class of region cache type
     */
    public SpringFluxCache(Cache cache, Class<T> type) {
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
        throw new NotSupportException("FluxCache is not support");
    }
}
