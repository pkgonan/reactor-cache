# Reactor Cache
* Reactor Cache Core
* Reactor Cache Spring


## How to use ?
* Just write @MonoCacheable annotation with cache name.
* Support only Mono type public method. (Flux not support now)
* Support only one method parameter.


## Example

    @EnableCaching
    @Configuration
    class Example {
    
        @MonoCacheable(value="CACHE_NAME_1")
        public Mono<String> find(long id) {
            ...
        }
        
        @MonoCacheable(value="CACHE_NAME_2")
        public Mono<YourObject> find(String id) {
            ...
        }
    }
    