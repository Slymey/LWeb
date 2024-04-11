package Common;

import java.util.Map;
//from SimpleEntry
public class Pair<K,V> /*implements Map.Entry<K,V>*/{
    private K first;
    private V second;
    
    public Pair(K first, V second) {
        this.first   = first;
        this.second = second;
    }
    public static <K,V> Pair<K,V> Pair(K first, V second) {
        return new Pair(first, second);
    }
    public Pair(Map.Entry<? extends K, ? extends V> entry) {
        this.first   = entry.getKey();
        this.second = entry.getValue();
    }
    public K getFirst() {
        return first;
    }
    public V getSecond() {
        return second;
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
    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry<?,?> e = (Map.Entry<?,?>)o;
        return eq(first, e.getKey()) && eq(second, e.getValue());
    }
    public int hashCode() {
        return (first   == null ? 0 :   first.hashCode()) ^
               (second == null ? 0 : second.hashCode());
    }
    public String toString() {
        return first + "=" + second;
    }
    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }
}
