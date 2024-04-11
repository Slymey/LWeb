package LWeb.Common.Range;


import java.util.Iterator;



public class RangeFloat implements Iterable<Double>, Iterator<Double>, Comparable<Double>{
    double min=0;
    int num=0;
    private double max=0;
    double step=0;
    private Iterator itr=null;
    private double current=0;
    public RangeFloat(double min, double max, double step){
        step = Math.abs(step);
        this.step = (min>max)?-step:step;
        this.min=min;
        current=min;
        this.max=max;
    } 
    public RangeFloat(double min, int num, double step){
        num--;
        this.min=min;
        this.num=num;
        this.step=step;
        current=min;
        this.max=min+num*step;
    }

    @Override
    public boolean hasNext() {
        boolean b=false;
        if(step<0){
            b=current>max;
        }else{
            b=current<max;
        }
        if(!b)current=min;
        return b;
    }

    @Override
    public Double next() {
        double ret=current;
        current+=step;
        return ret;
    }

    @Override
    public int compareTo(Double o) {
        int r=0;
        r=(o<min)?-1:r;
        r=(o>max)?1:r;
        return r;
    }

    @Override
    public Iterator<Double> iterator() {
        return this;
    }
    
    public boolean equals(Double i) {
        return this.compareTo(i)==0;
    }
    
    public double random(){
        return Math.random()*(max-min+1)+min;
    }
    
    public static double random(double min, int max){
        return Math.random()*(max-min+1)+min;
    }
    
    public static double random(double min, double max, double step){
        return (int)(Math.random()*(max-min+1)/step)*step+min;
    }

}
