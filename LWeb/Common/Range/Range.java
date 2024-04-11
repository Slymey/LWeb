package Common.Range;


import java.util.Iterator;



public class Range implements Iterable<Integer>, Iterator<Integer>, Comparable<Integer>{
    int min=0;
    int max=0;
    private Iterator itr=null;
    private int current=0;
    private int step =1;
    public Range(int min, int max){
        if(min>max){this.step=-1;}
        this.min=min;
        this.max=max;
        current=min;
    }
    public Range(int min, int max, int step){
        step = Math.abs(step);
        this.step = (min>max)?-step:step;
        this.min=min;
        current=min;
        this.max=max;
    }
    public Range(int min, int max, int step, boolean num){
        if(num){
            max--;
            this.min=min;
            this.step=step;
            current=min;
            this.max=min+max*step;
            if(max<1){
                this.min=0;
                this.max=0;
                this.step=0;
            }
        }else{
            step = Math.abs(step);
            this.step = (min>max)?-step:step;
            this.min=min;
            current=min;
            this.max=max;
        }
    }

    @Override
    public boolean hasNext() {
        boolean b=false;
        if(step<0){
            b=current>=max;
        }else{
            b=current<=max;
        }
        if(!b)current=min;
        return b;
    }

    @Override
    public Integer next() {
        int ret=current;
        current+=step;
        return ret;
    }

    @Override
    public int compareTo(Integer o) {
        int r=0;
        r=(o<min)?-1:r;
        r=(o>max)?1:r;
        return r;
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
    public boolean equals(int i) {
        return this.compareTo(i)==0;
    }
    
    public int random(){
        return (int)(Math.random()*(max-min+1)/step)*step+min;
    }
    
    public static int random(int min, int max){
        return (int)(Math.random()*(max-min+1)+min);
    }
    
    public static int random(int min, int max, int step){
        return (int)(Math.random()*(max-min+1)/step)*step+min;
    }

}
