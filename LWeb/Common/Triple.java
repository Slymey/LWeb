package LWeb.Common;

//from SimpleEntry
public class Triple<K,V,T>{
    private K first;
    private V second;
    private T third;
    
    public Triple(K first, V second, T third) {
        this.first   = first;
        this.second = second;
        this.third = third;
    }
    public static <K,V,T> Triple<K,V,T> Triple(K first, V second, T third) {
        return new Triple(first, second, third);
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
    public boolean equals(Object o) {
        if (!(o instanceof Triple))
            return false;
        Triple<?,?,?> e = (Triple<?,?,?>)o;
        return eq(first, e.getFirst()) && eq(second, e.getSecond()) &&  eq(third, e.getThird());
    }
    public int hashCode() {
        return (first   == null ? 0 :   first.hashCode()) ^
               (second == null ? 0 : second.hashCode()) ^
               (third == null ? 0 : third.hashCode());
    }
    public String toString() {
        return first + "=" + second + "=" + third;
    }
    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }
}
