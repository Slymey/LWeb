package LWeb.Common;

//from SimpleEntry
public class Quintuple<K,V,T,F,E>{
    public K first;
    public V second;
    public T third;
    public F fourth;
    public E fifth;
    
    public Quintuple(K first, V second, T third, F fourth, E fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
    }
    public static <K,V,T,F,E> Quintuple<K,V,T,F,E> Quintuple(K first, V second, T third, F fourth, E fifth) {
        return new Quintuple(first, second, third, fourth, fifth);
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
    public E getFifth() {
        return fifth;
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
    public E setFifth(E fifth) {
        E oldFifth = this.fifth;
        this.fifth = fifth;
        return oldFifth;
    }
    public boolean equals(Object o) {
        if (!(o instanceof Quintuple))
            return false;
        Quintuple<?,?,?,?,?> e = (Quintuple<?,?,?,?,?>)o;
        return eq(first, e.getFirst()) && eq(second, e.getSecond()) &&  eq(third, e.getThird()) && eq(fourth, e.fourth) && eq(fifth, e.getFifth());
    }
    public int hashCode() {
        return (first   == null ? 0 :   first.hashCode()) ^
               (second == null ? 0 : second.hashCode()) ^
               (third == null ? 0 : third.hashCode()) ^
               (fourth == null ? 0 : fourth.hashCode()) ^
               (fifth == null ? 0 : fifth.hashCode());
    }
    public String toString() {
        return first + "=" + second + "=" + third + "=" + fourth + "=" + fifth;
    }
    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }
}
