package LWeb.Common;

import java.util.Objects;


public interface UbFunction<T, R> {
    R apply(T... t);
}
