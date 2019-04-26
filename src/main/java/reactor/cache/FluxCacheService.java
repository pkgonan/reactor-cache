package reactor.cache;

import org.springframework.cache.CacheManager;
import reactor.core.publisher.Flux;

/**
 * Spring Flux cache service implementation for Reactor
 *
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
public class FluxCacheService<T> extends AbstractCacheService<Flux, T> {

    public static <T> FluxCacheService of(CacheManager cacheManager, String cacheName, Class<T> tClass) {
        return new FluxCacheService(cacheManager, cacheName, tClass);
    }

    private FluxCacheService(CacheManager cacheManager, String cacheName, Class<T> tClass) {
        super(cacheManager, cacheName, tClass);
    }

    @Override
    public Flux<T> find(Flux retriever, String key) {
        throw new NotSupportException();
    }
}
