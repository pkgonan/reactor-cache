package reactor.cache.spring;

import org.springframework.cache.Cache;
import org.springframework.util.Assert;

/**
 * Base Reactor cache service implementation for spring cache
 *
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
abstract class AbstractSpringCacheService<T> {

    /**
     * Spring Cache
     */
    protected Cache cache;

    /**
     * Class of region cache type
     */
    protected Class<T> type;

    /**
     * Constructor
     *
     * @param cache The spring cache
     * @param type  The Class of region cache type
     */
    protected AbstractSpringCacheService(Cache cache, Class<T> type) {
        Assert.notNull(cache, "Cache must not be null");
        Assert.notNull(type, "Class of region cache type must not be null");

        this.cache = cache;
        this.type = type;
    }
}
