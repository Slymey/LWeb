package LWeb.Common;

import static LWeb.Common.Pair.Pair;
import java.util.Objects;

@FunctionalInterface
public interface IndexedPredicate<T> {
    Pair<Boolean, Integer> test(T t,int index);

    default IndexedPredicate<T> and(IndexedPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t,ind) -> {
            Pair<Boolean, Integer> t1= test(t,ind);
            Pair<Boolean, Integer> t2= other.test(t,ind);
            return Pair(t1.getFirst()&&t2.getFirst(), Math.max(t1.getSecond(), t2.getSecond()));
        };
    }
    default IndexedPredicate<T> negate() {
        return (t,ind) -> {
            Pair<Boolean, Integer> t1= test(t,ind);
            return Pair(!t1.getFirst(),t1.getSecond());
        };
    }
    default IndexedPredicate<T> or(IndexedPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t,ind) -> {
            Pair<Boolean, Integer> t1= test(t,ind);
            Pair<Boolean, Integer> t2= other.test(t,ind);
            int val = (t1.getFirst()&&t2.getFirst())
                    ?Math.max(t1.getSecond(), t2.getSecond())
                    :(t1.getFirst())
                        ?t1.getSecond()
                        :t2.getSecond();
            return Pair(t1.getFirst()||t2.getFirst(),val);
        };
    }

}
