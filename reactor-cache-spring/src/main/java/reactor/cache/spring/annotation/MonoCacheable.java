package reactor.cache.spring.annotation;

import java.lang.annotation.*;

/**
 * Annotation indicating that the result of invoking a {@link reactor.core.publisher.Mono}
 * type method can be cached.
 *
 * @author Minkiu Kim
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MonoCacheable {

    /**
     * Names of the caches in which method invocation results are stored.
     * <p>Names may be used to determine the target cache (or caches), matching
     * the qualifier value or bean name of a specific bean definition.
     */
    String value() default "";

}
