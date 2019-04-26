package reactor.cache;

import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

/**
 * Spring cache service abstraction for Reactor
 *
 * @param <R> Flux or Mono
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
abstract class AbstractCacheService<R, T> implements CacheService<R, T> {

    protected CacheManager cacheManager;
    protected String cacheName;
    protected Class<T> tClass;

    protected AbstractCacheService(CacheManager cacheManager, String cacheName, Class<T> tClass) {
        Assert.notNull(cacheManager, "CacheManager must not null");
        Assert.notNull(cacheName, "CacheName must not null");
        Assert.notNull(tClass, "Class information must not null");

        this.cacheManager = cacheManager;
        this.cacheName = cacheName;
        this.tClass = tClass;
    }
}
