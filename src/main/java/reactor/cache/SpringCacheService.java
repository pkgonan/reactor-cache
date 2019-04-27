package reactor.cache;

import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

/**
 * Base Reactor cache service implementation for spring cache
 *
 * @param <R> Flux or Mono
 * @param <T> the type of return value
 *
 * @author Minkiu Kim
 */
abstract class SpringCacheService<R, T> implements CacheService<R, T> {

    /** Spring Cache Manager */
    protected CacheManager cacheManager;

    /** Cache name */
    protected String cacheName;

    /** Class of region cache type */
    protected Class<T> type;

    /**
     * Constructor
     *
     * @param cacheManager      The spring cache manager
     * @param cacheName         The cache name
     * @param type              The Class of region cache type
     */
    protected SpringCacheService(CacheManager cacheManager, String cacheName, Class<T> type) {
        Assert.notNull(cacheManager, "CacheManager must not be null");
        Assert.notNull(cacheName, "CacheName must not be null");
        Assert.notNull(type, "Class of region cache type must not be null");

        this.cacheManager = cacheManager;
        this.cacheName = cacheName;
        this.type = type;
    }
}
