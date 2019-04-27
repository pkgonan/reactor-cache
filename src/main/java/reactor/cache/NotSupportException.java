package reactor.cache;

/**
 * Not Support Exception
 *
 * @author Minkiu Kim
 */
class NotSupportException extends RuntimeException {

    /**
     * Constructs a NotSupportException.
     *
     * @param message           Message explaining the exception condition
     */
    NotSupportException(String message) { super(message); }
}
