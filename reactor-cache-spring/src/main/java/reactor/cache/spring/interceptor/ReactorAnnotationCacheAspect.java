package reactor.cache.spring.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Base class for reactor caching aspects,
 * such as the {@link org.springframework.cache.aspectj.AnnotationCacheAspect}.
 *
 * @author Minkiu Kim
 */
@Aspect
@Component
class ReactorAnnotationCacheAspect {

    private static final Logger LOG = LoggerFactory.getLogger(ReactorAnnotationCacheAspect.class.getName());
    private final ReactorCacheAspectSupport reactorCacheAspectSupport;

    ReactorAnnotationCacheAspect(final CacheManager cacheManager) {
        this.reactorCacheAspectSupport = new ReactorCacheAspectSupport(cacheManager);
    }

    @Around("annotationOfAnyMonoCacheable() && " +
            "executionOfAnyPublicMonoMethod()")
    final Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Method method = methodSignature.getMethod();
        final Object[] args = joinPoint.getArgs().clone();

        final CacheOperationInvoker aspectJInvoker = () -> {
            try {
                return joinPoint.proceed(args);
            } catch (Throwable ex) {
                throw new CacheOperationInvoker.ThrowableWrapper(ex);
            }
        };

        try {
            return reactorCacheAspectSupport.execute(aspectJInvoker, method, args);
        } catch (CacheOperationInvoker.ThrowableWrapper th) {
            LOG.error("Failure to proceed reactor cache. method : {}, arguments : {}", method.getName(), args, th.getOriginal());
        } catch (Exception e) {
            LOG.error("Failure to execute reactor cache aspect support. method : {}, arguments : {}", method.getName(), args, e);
        }

        return joinPoint.proceed(args);
    }

    @Pointcut(value = "@annotation(reactor.cache.spring.annotation.MonoCacheable)")
    private void annotationOfAnyMonoCacheable() {
    }

    @Pointcut(value = "execution(public reactor.core.publisher.Mono *(..))")
    private void executionOfAnyPublicMonoMethod() {
    }
}