package LWeb.Common;

import java.util.Objects;

import java.util.Objects;


@FunctionalInterface
public interface TriConsumer<T, U, P> {

    
    void accept(T t, U u, P p);

    
    default TriConsumer<T, U, P> andThen(TriConsumer<? super T, ? super U, ? super P> after) {
        Objects.requireNonNull(after);
        return (l, r, p) -> {
            accept(l, r, p);
            after.accept(l, r, p);
        };
    }
}

