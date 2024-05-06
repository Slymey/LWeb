package LWeb.Common;


@FunctionalInterface
public interface TriFunction<T, U, F, R> {

    
    R apply(T t, U u, F f);

    
}
