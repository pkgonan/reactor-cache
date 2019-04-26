package reactor.cache;

/**
 * Spring cache service for Reactor
 *
 * @param <R> Flux or Mono
 * @param <T> the type of return value
 * @author Minkiu Kim
 */
public interface CacheService<R, T> {

    R find(R retriever, String key);

}
