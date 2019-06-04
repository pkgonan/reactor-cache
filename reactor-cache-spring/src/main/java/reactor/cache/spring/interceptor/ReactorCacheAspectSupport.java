package reactor.cache.spring.interceptor;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.util.Assert;
import reactor.cache.core.ReactorCache;
import reactor.cache.spring.annotation.MonoCacheable;
import reactor.cache.spring.core.SpringMonoCache;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class for reactor caching supports,
 * such as the {@link org.springframework.cache.interceptor.CacheAspectSupport}.
 *
 * @author Minkiu Kim
 */
class ReactorCacheAspectSupport {

    private final CacheManager cacheManager;
    private final ConcurrentHashMap<Cache, ReactorCache> cacheResolverMap;
    private final ConcurrentHashMap<Method, String> cacheNameMap;
    private final ConcurrentHashMap<Method, Class<?>> cacheTypeMap;

    ReactorCacheAspectSupport(final CacheManager cacheManager) {
        Assert.notNull(cacheManager, "CacheManager should be not null");

        this.cacheManager = cacheManager;
        this.cacheResolverMap = new ConcurrentHashMap<>();
        this.cacheNameMap = new ConcurrentHashMap<>();
        this.cacheTypeMap = new ConcurrentHashMap<>();
    }

    Object execute(final CacheOperationInvoker invoker, final Method method, final Object[] args) {
        Assert.notNull(invoker, "CacheOperationInvoker should be not null");
        Assert.notNull(method, "Method should be not null");
        Assert.notEmpty(args, "Method argument should be not empty");

        final String key = getCacheKey(args);
        final String cacheName = getCacheName(method);
        final Cache cache = getCache(cacheName);
        final Class<?> returnType = getCacheType(method);

        return execute(cache, invoker.invoke(), key, returnType);
    }

    @SuppressWarnings("unchecked")
    private Object execute(final Cache cache, final Object proceed, final String key, final Class<?> type) {
        Assert.notNull(cache, "Cache should be not null");
        Assert.notNull(proceed, "Proceed object should be not null");
        Assert.notNull(key, "Cache key should be not null");
        Assert.notNull(type, "Cache type should be not null");

        final ReactorCache cacheResolver = getCacheResolver(cache, type);
        return cacheResolver.find(proceed, key);
    }

    @SuppressWarnings("unchecked")
    private ReactorCache getCacheResolver(final Cache cache, final Class<?> type) {
        return cacheResolverMap.computeIfAbsent(cache, c -> new SpringMonoCache(c, type));
    }

    private String getCacheName(final Method method) {
        try {
            return cacheNameMap.computeIfAbsent(method, m -> m.getAnnotation(MonoCacheable.class).value());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Annotation class");
        }
    }

    private Cache getCache(final String cacheName) {
        Assert.hasLength(cacheName, "Cache name should be not empty");
        return cacheManager.getCache(cacheName);
    }

    private Class<?> getCacheType(final Method method) {
        try {
            return cacheTypeMap.computeIfAbsent(method, m -> {
                final ParameterizedType parameterizedType = (ParameterizedType) m.getGenericReturnType();
                return (Class<?>) parameterizedType.getActualTypeArguments()[0];
            });
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid return type");
        }
    }

    private String getCacheKey(final Object[] args) {
        Assert.isTrue(args.length == 1, "Method argument size should be 1");
        return Objects.toString(args[0]);
    }
}
