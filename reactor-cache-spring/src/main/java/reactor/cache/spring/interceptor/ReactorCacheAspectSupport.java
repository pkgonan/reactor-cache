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
        this.cacheManager = cacheManager;
        this.cacheResolverMap = new ConcurrentHashMap<>();
        this.cacheNameMap = new ConcurrentHashMap<>();
        this.cacheTypeMap = new ConcurrentHashMap<>();
    }

    Object execute(CacheOperationInvoker invoker, Method method, Object[] args) {
        final String cacheName = getCacheName(method);
        final Cache cache = cacheManager.getCache(cacheName);
        final String key = getCacheKey(args);
        final Class<?> returnType = getCacheType(method);

        Assert.notNull(cache, "Cache should be not null");
        Assert.notNull(key, "Cache key should be not null");
        Assert.notNull(returnType, "Cache type should be not null");
        Assert.hasLength(cacheName, "Cache name should be not empty");
        Assert.isTrue(args.length == 1, "Method argument size should be 1");

        return execute(cache, invoker.invoke(), key, returnType);
    }

    @SuppressWarnings("unchecked")
    private Object execute(Cache cache, Object proceed, String key, Class<?> type) {
        final ReactorCache cacheResolver = getCacheResolver(cache, type);
        return cacheResolver.find(proceed, key);
    }

    @SuppressWarnings("unchecked")
    private ReactorCache getCacheResolver(Cache cache, Class<?> type) {
        return cacheResolverMap.computeIfAbsent(cache, c -> new SpringMonoCache(c, type));
    }

    private String getCacheName(Method method) {
        return cacheNameMap.computeIfAbsent(method, m -> m.getAnnotation(MonoCacheable.class).value());
    }

    private Class<?> getCacheType(Method method) {
        return cacheTypeMap.computeIfAbsent(method, m -> {
            final ParameterizedType parameterizedType = (ParameterizedType) m.getGenericReturnType();
            return (Class<?>) parameterizedType.getActualTypeArguments()[0];
        });
    }

    private String getCacheKey(Object[] args) {
        return Objects.toString(args[0]);
    }
}
