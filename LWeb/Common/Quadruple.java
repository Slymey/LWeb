package LWeb.Common;

//from SimpleEntry
public class Quadruple<K,V,T,F>{
    public K first;
    public V second;
    public T third;
    public F fourth;
    
    public Quadruple(K first, V second, T third, F fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }
    public static <K,V,T,F> Quadruple<K,V,T,F> Quadruple(K first, V second, T third, F fourth) {
        return new Quadruple(first, second, third, fourth);
    }
    public K getFirst() {
        return first;
    }
    public V getSecond() {
        return second;
    }
    public T getThird() {
        return third;
    }
    public F getFourth() {
        return fourth;
    }
    public K setFirst(K first) {
        K oldFirst= this.first;
        this.first = first;
        return oldFirst;
    }
    public V setSecond(V second) {
        V oldSecond = this.second;
        this.second = second;
        return oldSecond;
    }
    public T setThird(T third) {
        T oldThird= this.third;
        this.third = third;
        return oldThird;
    }
    public F setFourth(F fourth) {
        F oldFourth= this.fourth;
        this.fourth = fourth;
        return oldFourth;
    }
    public boolean equals(Object o) {
        if (!(o instanceof Quadruple))
            return false;
        Quadruple<?,?,?,?> e = (Quadruple<?,?,?,?>)o;
        return eq(first, e.getFirst()) && eq(second, e.getSecond()) &&  eq(third, e.getThird()) && eq(fourth, e.fourth);
    }
    public int hashCode() {
        return (first   == null ? 0 :   first.hashCode()) ^
               (second == null ? 0 : second.hashCode()) ^
               (third == null ? 0 : third.hashCode()) ^
               (fourth == null ? 0 : fourth.hashCode());
    }
    public String toString() {
        return first + "=" + second + "=" + third + "=" + fourth;
    }
    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }
}
