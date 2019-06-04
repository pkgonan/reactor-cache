package reactor.cache.spring.interceptor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import reactor.cache.spring.annotation.MonoCacheable;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

public class ReactorCacheAspectSupportTest {

    private CacheManager cacheManager;
    private ReactorCacheAspectSupport reactorCacheAspectSupport;

    @Before
    public void init() {
        this.cacheManager = new ConcurrentMapCacheManager();
        this.reactorCacheAspectSupport = new ReactorCacheAspectSupport(cacheManager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invokerNotNull() {
        reactorCacheAspectSupport.execute(null, EMPTY_CACHE_NAME_METHOD, new Object[]{1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void methodNotNull() {
        reactorCacheAspectSupport.execute(INVOKER, null, new Object[]{1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void argsNotNull() {
        reactorCacheAspectSupport.execute(INVOKER, EMPTY_CACHE_NAME_METHOD, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void argsNotLessThanOne() {
        reactorCacheAspectSupport.execute(INVOKER, EMPTY_CACHE_NAME_METHOD, new Object[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void argsNotGreaterThanOne() {
        reactorCacheAspectSupport.execute(INVOKER, EMPTY_CACHE_NAME_METHOD, new Object[]{1, 2});
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidAnnotation() {
        reactorCacheAspectSupport.execute(INVOKER, INVALID_ANNOTATION_METHOD, new Object[]{1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyCacheName() {
        reactorCacheAspectSupport.execute(INVOKER, EMPTY_CACHE_NAME_METHOD, new Object[]{1});
    }

    @Test
    public void success() {
        final Object executed = reactorCacheAspectSupport.execute(INVOKER, VALID_ANNOTATION_METHOD, new Object[]{1});

        Assert.assertTrue(executed instanceof Mono);
    }

    private static class InvalidAnnotation {
        public Mono<String> find(String name) {
            return Mono.fromCallable(() -> name);
        }
    }

    private static class EmptyCacheName {
        @MonoCacheable(value = "")
        public Mono<String> find(String name) {
            return Mono.fromCallable(() -> name);
        }
    }

    private static class ValidAnnotation {
        @MonoCacheable(value = "CACHE_NAME")
        public Mono<String> find(String name) {
            return Mono.fromCallable(() -> name);
        }
    }

    private static final Method INVALID_ANNOTATION_METHOD = InvalidAnnotation.class.getMethods()[0];
    private static final Method EMPTY_CACHE_NAME_METHOD = EmptyCacheName.class.getMethods()[0];
    private static final Method VALID_ANNOTATION_METHOD = ValidAnnotation.class.getMethods()[0];
    private static final CacheOperationInvoker INVOKER = () -> Mono.fromCallable(() -> "INVOKED");
}